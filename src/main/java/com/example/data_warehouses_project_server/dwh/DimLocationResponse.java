package com.example.data_warehouses_project_server.dwh;

import com.example.data_warehouses_project_server.domain.olap.entity.DimLocationEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
class DimLocationResponse {

    private Long locationKey;
    private Long addressId;
    private String city;
    private String region;
    private String country;

    private DimLocationResponse(Long locationKey, Long addressId, String city, String region, String country) {
        this.locationKey = locationKey;
        this.addressId = addressId;
        this.city = city;
        this.region = region;
        this.country = country;
    }

    public static DimLocationResponse fromEntity(DimLocationEntity dimLocation) {
        return new DimLocationResponse(
                dimLocation.getLocationKey(),
                dimLocation.getAddressId(),
                dimLocation.getCity(),
                dimLocation.getRegion(),
                dimLocation.getCountry()
        );
    }

    public Long getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(Long locationKey) {
        this.locationKey = locationKey;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
