package com.example.data_warehouses_project_server.product;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ProductService {

    private final ProductRepository productRepository;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        return null;
    }

    public List<Product> getProducts(String username) {
        return null;
    }

    public Product addProduct(ProductRequest request, String username) {
        return null;
    }

    public void updateProduct(Long id, ProductRequest request, String username) {

    }

    public void deleteProduct(Long id, String username) {

    }
}
