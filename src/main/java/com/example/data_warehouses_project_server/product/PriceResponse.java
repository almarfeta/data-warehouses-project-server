package com.example.data_warehouses_project_server.product;

import com.example.data_warehouses_project_server.domain.oltp.constant.Currency;
import com.example.data_warehouses_project_server.domain.oltp.constant.PriceStatus;
import com.example.data_warehouses_project_server.domain.oltp.entity.PriceEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
class PriceResponse {

    private Long productId;
    private Long priceId;
    private BigDecimal price;
    private Currency currency;
    private PriceStatus status;

    private PriceResponse(Long productId, Long priceId, BigDecimal price, Currency currency, PriceStatus status) {
        this.productId = productId;
        this.priceId = priceId;
        this.price = price;
        this.currency = currency;
        this.status = status;
    }

    public static PriceResponse fromEntity(PriceEntity price) {
        return new PriceResponse(
                price.getProduct().getId(),
                price.getId(),
                price.getValue(),
                price.getCurrency(),
                price.getStatus()
        );
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public PriceStatus getStatus() {
        return status;
    }

    public void setStatus(PriceStatus status) {
        this.status = status;
    }
}
