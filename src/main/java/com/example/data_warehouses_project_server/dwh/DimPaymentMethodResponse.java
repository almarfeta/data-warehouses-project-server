package com.example.data_warehouses_project_server.dwh;

import com.example.data_warehouses_project_server.domain.olap.entity.DimPaymentMethodEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
class DimPaymentMethodResponse {

    private Long paymentMethodKey;
    private String paymentMethod;

    private DimPaymentMethodResponse(Long paymentMethodKey, String paymentMethod) {
        this.paymentMethodKey = paymentMethodKey;
        this.paymentMethod = paymentMethod;
    }

    public static DimPaymentMethodResponse fromEntity(DimPaymentMethodEntity dimPaymentMethod) {
        return new DimPaymentMethodResponse(
                dimPaymentMethod.getPaymentMethodKey(),
                dimPaymentMethod.getPaymentMethod()
        );
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
