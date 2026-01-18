package com.example.data_warehouses_project_server.product;

import com.example.data_warehouses_project_server.domain.oltp.constant.Currency;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

class CreatePriceForm {

    @NotNull(message = "Price should not be null")
    @DecimalMin(value = "0.00", message = "Price cannot be negative")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @NotNull(message = "Currency should not be null")
    private Currency currency;

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
}
