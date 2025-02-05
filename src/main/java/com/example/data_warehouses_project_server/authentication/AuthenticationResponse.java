package com.example.data_warehouses_project_server.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
class AuthenticationResponse {

    private String message;
    private Long userId;
    private String token;

    AuthenticationResponse(String message, Long userId, String token) {
        this.message = message;
        this.userId = userId;
        this.token = token;
    }

    AuthenticationResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
