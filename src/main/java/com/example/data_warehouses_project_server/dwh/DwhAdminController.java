package com.example.data_warehouses_project_server.dwh;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(DwhAdminController.DEFAULT_ENDPOINT_MAPPING)
class DwhAdminController {

    static final String DEFAULT_ENDPOINT_MAPPING = "/api/admin/dwh";

    private final DwhService dwhService;

    DwhAdminController(DwhService dwhService) {
        this.dwhService = dwhService;
    }

    @GetMapping("/dim-time")
    public ResponseEntity<List<DimTimeResponse>> getAllDimTimes() {
        return ResponseEntity.ok(this.dwhService.getAllDimTimes());
    }

    @GetMapping("/dim-location")
    public ResponseEntity<List<DimLocationResponse>> getAllDimLocations() {
        return ResponseEntity.ok(this.dwhService.getAllDimLocations());
    }

    @GetMapping("/dim-customer")
    public ResponseEntity<List<DimCustomerResponse>> getAllDimCustomers() {
        return ResponseEntity.ok(this.dwhService.getAllDimCustomers());
    }

    @GetMapping("/dim-product")
    public ResponseEntity<List<DimProductResponse>> getAllDimProducts() {
        return ResponseEntity.ok(this.dwhService.getAllDimProducts());
    }

    @GetMapping("/dim-payment-method")
    public ResponseEntity<List<DimPaymentMethodResponse>> getAllDimPaymentMethods() {
        return ResponseEntity.ok(this.dwhService.getAllDimPaymentMethods());
    }

    @GetMapping("/fact-sale")
    public ResponseEntity<List<FactSaleResponse>> getAllFactSales() {
        return ResponseEntity.ok(this.dwhService.getAllFactSales());
    }
}
