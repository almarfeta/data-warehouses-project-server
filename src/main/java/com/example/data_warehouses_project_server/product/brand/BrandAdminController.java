package com.example.data_warehouses_project_server.product.brand;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(BrandAdminController.DEFAULT_ENDPOINT_MAPPING)
class BrandAdminController {

    static final String DEFAULT_ENDPOINT_MAPPING = "/api/admin/brand";

    private final BrandService brandService;

    BrandAdminController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAllBrands() {
        return ResponseEntity.ok(this.brandService.getAllBrands());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.brandService.getBrandById(id));
    }

    @PostMapping
    public ResponseEntity<String> createBrand(@RequestBody @Valid CreateBrandForm form) {
        Long brandId = this.brandService.createBrand(form);
        return ResponseEntity.created(URI.create(DEFAULT_ENDPOINT_MAPPING + "/" + brandId)).body("Brand created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable("id") Long id) {
        this.brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
