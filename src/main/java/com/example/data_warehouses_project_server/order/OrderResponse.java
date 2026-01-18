package com.example.data_warehouses_project_server.order;

import com.example.data_warehouses_project_server.domain.oltp.constant.OrderStatus;
import com.example.data_warehouses_project_server.domain.oltp.constant.PaymentMethod;
import com.example.data_warehouses_project_server.domain.oltp.entity.OrderEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.OrderItemEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
class OrderResponse {

    private Long orderId;
    private Long customerId;
    private Long addressId;
    private OffsetDateTime orderDate;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private String shipper;
    private String awb;
    private List<OrderItemResponse> orderItems;

    private OrderResponse(Long orderId, Long customerId, Long addressId, OffsetDateTime orderDate, OrderStatus orderStatus, PaymentMethod paymentMethod, String shipper, String awb, List<OrderItemResponse> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.addressId = addressId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.shipper = shipper;
        this.awb = awb;
        this.orderItems = orderItems;
    }

    public static OrderResponse fromEntity(OrderEntity order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomer().getId(),
                order.getDelivery().getAddress().getId(),
                order.getCreatedAt(),
                order.getStatus(),
                order.getPaymentMethod(),
                order.getDelivery().getShippedBy(),
                order.getDelivery().getAwb(),
                order.getOrderItems().stream().map(OrderItemResponse::fromEntity).toList()
        );
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public OffsetDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(OffsetDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getAwb() {
        return awb;
    }

    public void setAwb(String awb) {
        this.awb = awb;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemResponse> orderItems) {
        this.orderItems = orderItems;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class OrderItemResponse {

        private Long orderItemId;
        private Long productId;
        private Long priceId;
        private Integer quantity;

        private OrderItemResponse(Long orderItemId, Long productId, Long priceId, Integer quantity) {
            this.orderItemId = orderItemId;
            this.productId = productId;
            this.priceId = priceId;
            this.quantity = quantity;
        }

        public static OrderItemResponse fromEntity(OrderItemEntity orderItem) {
            return new OrderItemResponse(
                    orderItem.getId(),
                    orderItem.getProduct().getId(),
                    orderItem.getPrice().getId(),
                    orderItem.getQuantity()
            );
        }

        public Long getOrderItemId() {
            return orderItemId;
        }

        public void setOrderItemId(Long orderItemId) {
            this.orderItemId = orderItemId;
        }

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
}
