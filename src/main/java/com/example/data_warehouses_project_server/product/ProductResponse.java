package com.example.data_warehouses_project_server.product;

import com.example.data_warehouses_project_server.domain.oltp.constant.ProductStatus;
import com.example.data_warehouses_project_server.domain.oltp.entity.ProductEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
class ProductResponse {

    private Long productId;
    private Long inventoryId;
    private String sku;
    private String productName;
    private String description;
    private ProductStatus status;
    private String brand;
    private String category;
    private Integer stock;

    private ProductResponse(Long productId, Long inventoryId, String sku, String productName, String description, ProductStatus status, String brand, String category, Integer stock) {
        this.productId = productId;
        this.inventoryId = inventoryId;
        this.sku = sku;
        this.productName = productName;
        this.description = description;
        this.status = status;
        this.brand = brand;
        this.category = category;
        this.stock = stock;
    }

    public static ProductResponse fromEntity(ProductEntity product) {
        return new ProductResponse(
                product.getId(),
                product.getInventory().getId(),
                product.getSku(),
                product.getProductName(),
                product.getDescription(),
                product.getStatus(),
                product.getBrand().getBrandName(),
                product.getCategory().getCategoryName(),
                product.getInventory().getStockAvailable()
        );
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
