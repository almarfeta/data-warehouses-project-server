package com.example.data_warehouses_project_server.product;

import com.example.data_warehouses_project_server.domain.oltp.constant.Currency;
import com.example.data_warehouses_project_server.domain.oltp.constant.ProductStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

class CreateProductForm {

    @NotNull(message = "Brand ID should not be null")
    private Long brandId;

    @NotNull(message = "Category ID should not be null")
    private Long categoryId;

    @NotBlank(message = "SKU should not be blank")
    private String sku;

    @NotBlank(message = "Product name should not be blank")
    private String name;

    private String description;

    @NotNull(message = "Status should not be null")
    private ProductStatus status;

    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    @NotNull(message = "Default price should not be null")
    @DecimalMin(value = "0.00", message = "Default price cannot be negative")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal defaultPrice;

    @NotNull(message = "Price currency should not be null")
    private Currency priceCurrency;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(BigDecimal defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Currency getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(Currency priceCurrency) {
        this.priceCurrency = priceCurrency;
    }
}
