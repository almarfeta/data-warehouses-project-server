package com.example.data_warehouses_project_server.domain.olap.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity(name = "dimCustomer")
@Table(name = "dim_customer")
public class DimCustomerEntity {

    @Id
    @Column(name = "customer_key")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerKey;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "full_name", length = 200)
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    public DimCustomerEntity() {
    }

    public DimCustomerEntity(Long customerId, String username, String email, String fullName, LocalDate dateOfBirth, OffsetDateTime createdAt) {
        this.customerId = customerId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
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
