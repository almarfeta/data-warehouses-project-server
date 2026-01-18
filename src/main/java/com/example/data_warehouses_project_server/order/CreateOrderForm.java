package com.example.data_warehouses_project_server.order;

import com.example.data_warehouses_project_server.domain.oltp.constant.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

class CreateOrderForm {

    @NotNull(message = "Customer ID should not be null")
    private Long customerId;

    @NotNull(message = "Address ID should not be null")
    private Long addressId;

    @NotNull(message = "Payment method should not be null")
    private PaymentMethod paymentMethod;

    @Valid
    @NotNull(message = "Order items should not be null")
    @NotEmpty(message = "Order items should not be empty")
    private List<FormOrderItem> orderItems;

    static class FormOrderItem {

        @NotNull(message = "Product ID should not be null")
        private Long productId;

        @NotNull(message = "Price ID should not be null")
        private Long priceId;

        @NotNull(message = "Quantity should not be null")
        @Positive(message = "Quantity should be greater than 0")
        private Integer quantity;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Long getPriceId() {
            return priceId;
        }

        public void setPriceId(Long priceId) {
            this.priceId = priceId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<FormOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<FormOrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
