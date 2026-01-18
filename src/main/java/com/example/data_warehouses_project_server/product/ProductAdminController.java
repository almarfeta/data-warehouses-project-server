package com.example.data_warehouses_project_server.product;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(ProductAdminController.DEFAULT_ENDPOINT_MAPPING)
class ProductAdminController {

    static final String DEFAULT_ENDPOINT_MAPPING = "/api/admin/product";

    private final ProductService productService;

    ProductAdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(this.productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid CreateProductForm form) {
        Long productId = this.productService.createProduct(form);
        return ResponseEntity.created(URI.create(DEFAULT_ENDPOINT_MAPPING + "/" + productId)).body("Product created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        this.productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Void> updateStock(@PathVariable("productId") Long id, @RequestParam Integer stock) {
        this.productService.updateStock(id, stock);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/price")
    public ResponseEntity<List<PriceResponse>> getAllPrices() {
        return ResponseEntity.ok(this.productService.getAllPrices());
    }

    @GetMapping("/{productId}/price")
    public ResponseEntity<List<PriceResponse>> getAllPricesByProduct(@PathVariable("productId") Long id) {
        return ResponseEntity.ok(this.productService.getAllPricesByProduct(id));
    }

    @GetMapping("/price/{priceId}")
    public ResponseEntity<PriceResponse> getPriceById(@PathVariable("priceId") Long id) {
        return ResponseEntity.ok(this.productService.getPriceById(id));
    }

    @PostMapping("{productId}/price")
    public ResponseEntity<String> addPrice(
            @PathVariable("productId") Long id,
            @RequestBody @Valid CreatePriceForm form
    ) {
        Long priceId = this.productService.createPrice(id, form);
        return ResponseEntity.created(URI.create(DEFAULT_ENDPOINT_MAPPING + "/price/" + priceId))
                .body("Price added");
    }

    @DeleteMapping("/price/{priceId}")
    public ResponseEntity<Void> deletePrice(@PathVariable("priceId") Long id) {
        this.productService.deletePrice(id);
        return ResponseEntity.noContent().build();
    }
}
