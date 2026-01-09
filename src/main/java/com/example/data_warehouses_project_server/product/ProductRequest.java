package com.example.data_warehouses_project_server.product;

import com.example.data_warehouses_project_server.marker.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

class ProductRequest {

    @NotBlank(groups = OnCreate.class, message = "Name should not be blank")
    private String name;

    private String description;

    @NotNull(groups = OnCreate.class, message = "Price should not be null")
    private BigDecimal price;

    @NotNull(groups = OnCreate.class, message = "Stock should not be null")
    private Integer stock;

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
}
