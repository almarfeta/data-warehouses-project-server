package com.example.data_warehouses_project_server.domain.olap.entity;

import jakarta.persistence.*;

@Entity(name = "dimLocation")
@Table(name = "dim_location")
public class DimLocationEntity {

    @Id
    @Column(name = "location_key")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationKey;

    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "region", length = 100)
    private String region;

    @Column(name = "country", length = 100)
    private String country;

    public DimLocationEntity() {
    }

    public DimLocationEntity(Long addressId, String city, String region, String country) {
        this.addressId = addressId;
        this.city = city;
        this.region = region;
        this.country = country;
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
