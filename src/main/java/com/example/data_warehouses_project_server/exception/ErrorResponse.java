package com.example.data_warehouses_project_server.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
class ErrorResponse {

    String error;
    Map<String, String> errors;

    ErrorResponse(String error) {
        this.error = error;
    }

    ErrorResponse(Map<String, String> errors) {
        this.errors = errors;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Map<String, String> getErrors() {
        return this.errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
