package com.example.data_warehouses_project_server.domain.olap.entity;

import jakarta.persistence.*;

@Entity(name = "dimPaymentMethod")
@Table(name = "dim_payment_method")
public class DimPaymentMethodEntity {

    @Id
    @Column(name = "payment_method_key")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentMethodKey;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    public DimPaymentMethodEntity() {
    }

    public DimPaymentMethodEntity(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getPaymentMethodKey() {
        return paymentMethodKey;
    }

    public void setPaymentMethodKey(Long paymentMethodKey) {
        this.paymentMethodKey = paymentMethodKey;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
