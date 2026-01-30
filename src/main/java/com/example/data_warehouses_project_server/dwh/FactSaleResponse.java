package com.example.data_warehouses_project_server.dwh;

import java.math.BigDecimal;

import com.example.data_warehouses_project_server.domain.olap.entity.FactSaleEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
class FactSaleResponse {

    private Long salesKey;
    private Long timeKey;
    private Long locationKey;
    private Long customerKey;
    private Long productKey;
    private Long paymentMethodKey;
    private Long orderId;
    private Long orderItemId;
    private BigDecimal unitPrice;
    private Integer quantity;
    private String status;
    private String shippedBy;

    private FactSaleResponse(Long salesKey, Long timeKey, Long locationKey, Long customerKey, Long productKey, Long paymentMethodKey,
            Long orderId, Long orderItemId, BigDecimal unitPrice, Integer quantity, String status, String shippedBy) {
        this.salesKey = salesKey;
        this.timeKey = timeKey;
        this.locationKey = locationKey;
        this.customerKey = customerKey;
        this.productKey = productKey;
        this.paymentMethodKey = paymentMethodKey;
        this.orderId = orderId;
        this.orderItemId = orderItemId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.status = status;
        this.shippedBy = shippedBy;
    }

    public static FactSaleResponse fromEntity(FactSaleEntity factSale) {
        return new FactSaleResponse(
                factSale.getSalesKey(),
                factSale.getTime().getTimeKey(),
                factSale.getLocation().getLocationKey(),
                factSale.getCustomer().getCustomerKey(),
                factSale.getProduct().getProductKey(),
                factSale.getPaymentMethod().getPaymentMethodKey(),
                factSale.getOrderId(),
                factSale.getOrderItemId(),
                factSale.getUnitPrice(),
                factSale.getQuantity(),
                factSale.getStatus(),
                factSale.getShippedBy()
        );
    }

    public Long getSalesKey() {
        return salesKey;
    }

    public void setSalesKey(Long salesKey) {
        this.salesKey = salesKey;
    }

    public Long getTimeKey() {
        return timeKey;
    }

    public void setTimeKey(Long timeKey) {
        this.timeKey = timeKey;
    }

    public Long getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(Long locationKey) {
        this.locationKey = locationKey;
    }

    public Long getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(Long customerKey) {
        this.customerKey = customerKey;
    }

    public Long getProductKey() {
        return productKey;
    }

    public void setProductKey(Long productKey) {
        this.productKey = productKey;
    }

    public Long getPaymentMethodKey() {
        return paymentMethodKey;
    }

    public void setPaymentMethodKey(Long paymentMethodKey) {
        this.paymentMethodKey = paymentMethodKey;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
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
}
