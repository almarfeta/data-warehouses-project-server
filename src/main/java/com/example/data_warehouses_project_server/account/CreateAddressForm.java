package com.example.data_warehouses_project_server.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

class CreateAddressForm {

    @NotBlank(message = "Street should not be blank")
    private String street;

    @NotBlank(message = "City should not be blank")
    private String city;

    @NotBlank(message = "Region should not be blank")
    private String region;

    @NotBlank(message = "Country should not be blank")
    private String country;

    @NotBlank(message = "Postal code should not be blank")
    @JsonProperty("postal_code")
    private String postalCode;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
