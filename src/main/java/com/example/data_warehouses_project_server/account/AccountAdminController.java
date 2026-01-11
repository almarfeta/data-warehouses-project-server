package com.example.data_warehouses_project_server.account;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(AccountAdminController.DEFAULT_ENDPOINT_MAPPING)
class AccountAdminController {

    static final String DEFAULT_ENDPOINT_MAPPING = "/api/account";

    private final AccountService accountService;

    AccountAdminController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        return ResponseEntity.ok(this.accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.accountService.getAccountById(id));
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody @Valid CreateAccountForm form) {
        Long accountId = this.accountService.createAccount(form);
        return ResponseEntity.created(URI.create(DEFAULT_ENDPOINT_MAPPING + "/" + accountId)).body("Account created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id) {
        this.accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/address")
    public ResponseEntity<List<AddressResponse>> getAllAddresses() {
        return ResponseEntity.ok(this.accountService.getAllAddresses());
    }

    @GetMapping("/{accountId}/address")
    public ResponseEntity<List<AddressResponse>> getAllAddressesByAccount(@PathVariable("accountId") Long id) {
        return ResponseEntity.ok(this.accountService.getAllAddressesByAccount(id));
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable("addressId") Long id) {
        return ResponseEntity.ok(this.accountService.getAddressById(id));
    }

    @PostMapping("{accountId}/address")
    public ResponseEntity<String> addAddress(
            @PathVariable("accountId") Long id,
            @RequestBody @Valid CreateAddressForm form
    ) {
        Long addressId = this.accountService.createAddress(id, form);
        return ResponseEntity.created(URI.create(DEFAULT_ENDPOINT_MAPPING + "/address/" + addressId))
                .body("Address added");
    }

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable("addressId") Long id) {
        this.accountService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
