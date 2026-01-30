package com.example.data_warehouses_project_server.dwh;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.example.data_warehouses_project_server.domain.olap.entity.DimCustomerEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
class DimCustomerResponse {

    private Long customerKey;
    private Long customerId;
    private String username;
    private String email;
    private String fullName;
    private LocalDate dateOfBirth;
    private OffsetDateTime createdAt;

    private DimCustomerResponse(Long customerKey, Long customerId, String username, String email, String fullName, LocalDate dateOfBirth, OffsetDateTime createdAt) {
        this.customerKey = customerKey;
        this.customerId = customerId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
    }

    public static DimCustomerResponse fromEntity(DimCustomerEntity dimCustomer) {
        return new DimCustomerResponse(
                dimCustomer.getCustomerKey(),
                dimCustomer.getCustomerId(),
                dimCustomer.getUsername(),
                dimCustomer.getEmail(),
                dimCustomer.getFullName(),
                dimCustomer.getDateOfBirth(),
                dimCustomer.getCreatedAt()
        );
    }

    public Long getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(Long customerKey) {
        this.customerKey = customerKey;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
