package com.example.data_warehouses_project_server.domain.olap.entity;

import jakarta.persistence.*;

@Entity(name = "dimProduct")
@Table(name = "dim_product")
public class DimProductEntity {

    @Id
    @Column(name = "product_key")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productKey;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "sku", length = 100)
    private String sku;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "category_tree", length = 500)
    private String categoryTree;

    @Column(name = "status", length = 50)
    private String status;

    public DimProductEntity() {
    }

    public DimProductEntity(Long productId, String sku, String productName, String brandName, String categoryTree, String status) {
        this.productId = productId;
        this.sku = sku;
        this.productName = productName;
        this.brandName = brandName;
        this.categoryTree = categoryTree;
        this.status = status;
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
