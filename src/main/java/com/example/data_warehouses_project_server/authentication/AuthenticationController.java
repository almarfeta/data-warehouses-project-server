package com.example.data_warehouses_project_server.authentication;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(AuthenticationController.DEFAULT_ENDPOINT_MAPPING)
class AuthenticationController {

    static final String DEFAULT_ENDPOINT_MAPPING = "/api/auth";

    private final AuthenticationService authenticationService;

    AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.created(URI.create(DEFAULT_ENDPOINT_MAPPING + "/login"))
                .body(this.authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(this.authenticationService.login(request));
    }

    // TODO: Make 3 new endpoints: CRD a new account (that can be admin if specified)
    //  Idea behind these 2 endpoints:
    //  - when a normal USER will try to make a new account they will hit "register" then "login"
    //  - when an ADMIN user will try to make a new account, another admin needs to create it for him,
    //   then, he can hit "login"
    //  - this flow is useful, because in the end, on our UI, we might want to create dummy users ourselves,
    //   but we will login through an ADMIN account to do this

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> createAccount() {
        return ResponseEntity.ok("test secured endpoint");
    }
}
