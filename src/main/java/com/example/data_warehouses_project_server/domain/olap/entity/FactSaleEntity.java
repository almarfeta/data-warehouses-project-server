package com.example.data_warehouses_project_server.domain.olap.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "factSale")
@Table(name = "fact_sales")
public class FactSaleEntity {

    @Id
    @Column(name = "sales_key")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesKey;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "shipped_by", length = 100)
    private String shippedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_key")
    private DimTimeEntity time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_key")
    private DimLocationEntity location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_key")
    private DimCustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_key")
    private DimProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_key")
    private DimPaymentMethodEntity paymentMethod;

    public FactSaleEntity() {
    }

    public FactSaleEntity(Long orderId, Long orderItemId, BigDecimal unitPrice, Long quantity, String status, String shippedBy,
                          DimTimeEntity time, DimLocationEntity location, DimCustomerEntity customer, DimProductEntity product, DimPaymentMethodEntity paymentMethod) {
        this.orderId = orderId;
        this.orderItemId = orderItemId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.status = status;
        this.shippedBy = shippedBy;
        this.time = time;
        this.location = location;
        this.customer = customer;
        this.product = product;
        this.paymentMethod = paymentMethod;
    }

    public Long getSalesKey() {
        return salesKey;
    }

    public void setSalesKey(Long salesKey) {
        this.salesKey = salesKey;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippedBy() {
        return shippedBy;
    }

    public void setShippedBy(String shippedBy) {
        this.shippedBy = shippedBy;
    }

    public DimTimeEntity getTime() {
        return time;
    }

    public void setTime(DimTimeEntity time) {
        this.time = time;
    }

    public DimLocationEntity getLocation() {
        return location;
    }

    public void setLocation(DimLocationEntity location) {
        this.location = location;
    }

    public DimCustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(DimCustomerEntity customer) {
        this.customer = customer;
    }

    public DimProductEntity getProduct() {
        return product;
    }

    public void setProduct(DimProductEntity product) {
        this.product = product;
    }

    public DimPaymentMethodEntity getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(DimPaymentMethodEntity paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
