package com.example.data_warehouses_project_server.dwh;

import com.example.data_warehouses_project_server.domain.olap.entity.DimProductEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
class DimProductResponse {

    private Long productKey;
    private Long productId;
    private String sku;
    private String productName;
    private String brandName;
    private String categoryTree;
    private String status;

    private DimProductResponse(Long productKey, Long productId, String sku, String productName, String brandName, String categoryTree, String status) {
        this.productKey = productKey;
        this.productId = productId;
        this.sku = sku;
        this.productName = productName;
        this.brandName = brandName;
        this.categoryTree = categoryTree;
        this.status = status;
    }

    public static DimProductResponse fromEntity(DimProductEntity dimProduct) {
        return new DimProductResponse(
                dimProduct.getProductKey(),
                dimProduct.getProductId(),
                dimProduct.getSku(),
                dimProduct.getProductName(),
                dimProduct.getBrandName(),
                dimProduct.getCategoryTree(),
                dimProduct.getStatus()
        );
    }

    public Long getProductKey() {
        return productKey;
    }

    public void setProductKey(Long productKey) {
        this.productKey = productKey;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryTree() {
        return categoryTree;
    }

    public void setCategoryTree(String categoryTree) {
        this.categoryTree = categoryTree;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
