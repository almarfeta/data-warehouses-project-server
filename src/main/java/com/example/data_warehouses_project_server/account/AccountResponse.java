package com.example.data_warehouses_project_server.account;

import com.example.data_warehouses_project_server.domain.oltp.constant.Role;
import com.example.data_warehouses_project_server.domain.oltp.entity.AccountEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
class AccountResponse {

    private Long accountId;
    private Long customerId;
    private String username;
    private String email;
    private Role role;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;

    private AccountResponse(Long accountId, Long customerId, String username, String email, Role role,
                           String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public static AccountResponse fromEntity(AccountEntity account) {
        CustomerEntity customer = account.getCustomer();

        return new AccountResponse(
                account.getId(),
                customer != null ? customer.getId() : null,
                account.getUsername(),
                account.getEmail(),
                account.getRole(),
                customer != null ? customer.getFirstName() : null,
                customer != null ? customer.getLastName() : null,
                customer != null ? customer.getDateOfBirth() : null,
                customer != null ? customer.getPhoneNumber() : null
        );
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
