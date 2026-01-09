package com.example.data_warehouses_project_server.product;

import com.example.data_warehouses_project_server.authentication.JwtPublicService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ProductController.DEFAULT_ENDPOINT_MAPPING)
class ProductController {

    static final String DEFAULT_ENDPOINT_MAPPING = "/api/products";

    private final ProductService productService;
    private final JwtPublicService jwtService;

    ProductController(ProductService productService, JwtPublicService jwtService) {
        this.productService = productService;
        this.jwtService = jwtService;
    }

    // TODO: Will be changed

//    @GetMapping("/{id}")
//    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
//        ProductEntity product = this.productService.getProductById(id);
//
//        return ResponseEntity.ok(new ProductResponse(
//                product.getId(),
//                product.getName(),
//                product.getDescription(),
//                product.getPrice(),
//                product.getStock(),
//                product.getCreator().getUsername()
//        ));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ProductResponse>> getProducts(
//            @RequestHeader(value = "Authorization", required = false) String bearerToken
//    ) {
//        String username = bearerToken == null ? null :
//                this.jwtService.extractUsername(this.jwtService.extractJwt(bearerToken));
//
//        List<ProductEntity> products = this.productService.getProducts(username);
//
//        return ResponseEntity.ok(products.stream()
//                .map(product -> new ProductResponse(
//                        product.getId(),
//                        product.getName(),
//                        product.getDescription(),
//                        product.getPrice(),
//                        product.getStock(),
//                        product.getCreator().getUsername()
//                ))
//                .toList()
//        );
//    }
//
//    @PostMapping
//    public ResponseEntity<ProductResponse> addProduct(@RequestHeader("Authorization") String bearerToken,
//            @RequestBody @Validated(OnCreate.class) ProductRequest request) {
//        String username = this.jwtService.extractUsername(this.jwtService.extractJwt(bearerToken));
//
//        ProductEntity product = this.productService.addProduct(request, username);
//
//        URI uri = URI.create(DEFAULT_ENDPOINT_MAPPING + "/" + product.getId());
//
//        return ResponseEntity.created(uri).body(new ProductResponse("Product created successfully"));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ProductResponse> updateProduct(@RequestHeader("Authorization") String bearerToken,
//            @PathVariable Long id,
//            @RequestBody @Validated(OnUpdate.class) ProductRequest request) {
//        String username = this.jwtService.extractUsername(this.jwtService.extractJwt(bearerToken));
//
//        this.productService.updateProduct(id, request, username);
//
//        return ResponseEntity.ok(new ProductResponse("Product updated successfully"));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ProductResponse> deleteProduct(@RequestHeader("Authorization") String bearerToken,
//            @PathVariable Long id) {
//        String username = this.jwtService.extractUsername(this.jwtService.extractJwt(bearerToken));
//
//        this.productService.deleteProduct(id, username);
//
//        return ResponseEntity.ok(new ProductResponse("Product deleted successfully"));
//    }
}
