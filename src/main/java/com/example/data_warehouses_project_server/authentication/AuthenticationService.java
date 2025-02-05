package com.example.data_warehouses_project_server.authentication;

import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class AuthenticationService {

    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    AuthenticationService(AccountRepository accountRepository, TokenRepository tokenRepository,
                                 PasswordEncoder passwordEncoder, JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.accountRepository = accountRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    private void saveUserToken(String jwt, Account user) {
        this.tokenRepository.save(new Token(jwt, false, false, user));
    }

    private void revokeAllUserTokens(Account user) {
        List<Token> validUserTokens = this.tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty()) {
            return;
        }

        for (Token token : validUserTokens) {
            token.setExpired(true);
            token.setRevoked(true);
        }

        this.tokenRepository.saveAll(validUserTokens);
    }

    private String authenticate(Account user, String originalPassword) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        originalPassword
                )
        );

        String jwt = this.jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(jwt, user);

        return jwt;
    }

    // TODO: Declare a custom exception instead of IllegalState, that should return 401 - Unauthorized

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        if (this.accountRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalStateException("Username taken");
        }

        if (this.accountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("Email taken");
        }

        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            throw new IllegalStateException("Passwords don't match");
        }

        Account user = this.accountRepository.save(new Account(
                request.getUsername(),
                request.getEmail(),
                this.passwordEncoder.encode(request.getPassword()),
                Role.USER)
        );

        String jwt = authenticate(user, request.getPassword());

        return new AuthenticationResponse("User registered successfully.", user.getId(), jwt);
    }

    @Transactional
    public AuthenticationResponse login(LoginRequest request) {
        Account user = this.accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalStateException("Username or password wrong"));

        String jwt = authenticate(user, request.getPassword());

        return new AuthenticationResponse("Login successful.", jwt);
    }
}
