package com.example.data_warehouses_project_server.authentication;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity(name = "token")
@Table(name = "tokens")
class Token {

    @Id
    @SequenceGenerator(name = "tokenSequence", sequenceName = "tokens_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokenSequence")
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private boolean revoked;

    @Column(nullable = false)
    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Account account;

    public Token() {
    }

    Token(String token, boolean revoked, boolean expired, Account account) {
        this.token = token;
        this.revoked = revoked;
        this.expired = expired;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}