package com.example.data_warehouses_project_server.product.brand;

import com.example.data_warehouses_project_server.domain.oltp.entity.BrandEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
class BrandResponse {

    private Long brandId;
    private String brandName;

    private BrandResponse(Long brandId, String brandName) {
        this.brandId = brandId;
        this.brandName = brandName;
    }

    public static BrandResponse fromEntity(BrandEntity brand) {
        return new BrandResponse(brand.getId(), brand.getBrandName());
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
