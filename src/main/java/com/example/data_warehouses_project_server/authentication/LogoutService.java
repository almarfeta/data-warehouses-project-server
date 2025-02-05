package com.example.data_warehouses_project_server.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static com.example.data_warehouses_project_server.authentication.AuthenticationConstants.AUTHORIZATION_HEADER;
import static com.example.data_warehouses_project_server.authentication.AuthenticationConstants.BEARER_TOKEN_PREFIX;

@Service
class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    LogoutService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        final String jwtToken;

        if (authHeader == null || !authHeader.startsWith(BEARER_TOKEN_PREFIX)) {
            return;
        }

        jwtToken = authHeader.substring(BEARER_TOKEN_PREFIX.length());
        Token storedToken = this.tokenRepository.findByToken(jwtToken)
                .orElse(null);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            this.tokenRepository.save(storedToken);
        }
    }
}
