package com.example.data_warehouses_project_server.authentication;

import com.example.data_warehouses_project_server.domain.oltp.constant.Role;
import com.example.data_warehouses_project_server.domain.oltp.entity.AccountEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.CustomerEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.TokenEntity;
import com.example.data_warehouses_project_server.domain.oltp.repository.AccountRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.CustomerRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.TokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
class AuthenticationService {

    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtPublicService jwtService;
    private final AuthenticationManager authenticationManager;

    AuthenticationService(AccountRepository accountRepository, TokenRepository tokenRepository,
                          CustomerRepository customerRepository, PasswordEncoder passwordEncoder,
                          JwtPublicService jwtService, AuthenticationManager authenticationManager) {
        this.accountRepository = accountRepository;
        this.tokenRepository = tokenRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    private void saveUserToken(String jwt, AccountEntity user) {
        this.tokenRepository.save(new TokenEntity(jwt, false, false, user));
    }

    private void revokeAllUserTokens(AccountEntity user) {
        List<TokenEntity> validUserTokens = this.tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty()) {
            return;
        }

        for (TokenEntity token : validUserTokens) {
            token.setExpired(true);
            token.setRevoked(true);
        }

        this.tokenRepository.saveAll(validUserTokens);
    }

    private String authenticate(AccountEntity user, String originalPassword) {
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

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        if (this.accountRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameNotFoundException("Username taken");
        }

        if (this.accountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UsernameNotFoundException("Email taken");
        }

        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            throw new UsernameNotFoundException("Passwords don't match");
        }

        CustomerEntity customer = this.customerRepository.save(new CustomerEntity(
                request.getFirstName(),
                request.getLastName(),
                null,
                null,
                OffsetDateTime.now()
        ));

        AccountEntity user = this.accountRepository.save(new AccountEntity(
                request.getUsername(),
                request.getEmail(),
                this.passwordEncoder.encode(request.getPassword()),
                Role.USER,
                customer
        ));

        String jwt = authenticate(user, request.getPassword());

        return new AuthenticationResponse("User registered successfully.", user.getId(), jwt);
    }

    @Transactional
    public AuthenticationResponse login(LoginRequest request) {
        AccountEntity user = this.accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username or password wrong"));

        String jwt = authenticate(user, request.getPassword());

        return new AuthenticationResponse("Login successful.", jwt);
    }
}
