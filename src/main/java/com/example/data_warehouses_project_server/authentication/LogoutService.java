package com.example.data_warehouses_project_server.authentication;

import com.example.data_warehouses_project_server.domain.oltp.entity.TokenEntity;
import com.example.data_warehouses_project_server.domain.oltp.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
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
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        final String jwtToken;

        if (authHeader == null || !authHeader.startsWith(BEARER_TOKEN_PREFIX)) {
            return;
        }

        jwtToken = authHeader.substring(BEARER_TOKEN_PREFIX.length());
        TokenEntity storedToken = this.tokenRepository.findByToken(jwtToken)
                .orElse(null);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            this.tokenRepository.save(storedToken);
        }
    }
}
