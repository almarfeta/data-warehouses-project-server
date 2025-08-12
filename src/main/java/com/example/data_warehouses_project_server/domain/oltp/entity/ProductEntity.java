package com.example.data_warehouses_project_server.domain.oltp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "product")
@Table(name = "products")
public class ProductEntity {

    @Id
    @SequenceGenerator(name = "productSequence", sequenceName = "products_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSequence")
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private AccountEntity creator;

    public ProductEntity() {
    }

    public ProductEntity(String name, String description, BigDecimal price, Integer stock, AccountEntity creator) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public AccountEntity getCreator() {
        return creator;
    }

    public void setCreator(AccountEntity creator) {
        this.creator = creator;
    }
}
