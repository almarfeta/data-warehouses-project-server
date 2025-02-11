package com.example.data_warehouses_project_server.product;

import com.example.data_warehouses_project_server.authentication.Account;
import com.example.data_warehouses_project_server.authentication.AccountRepository;
import com.example.data_warehouses_project_server.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ProductService {

    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;

    ProductService(ProductRepository productRepository, AccountRepository accountRepository) {
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
    }

    public Product getProductById(Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    public List<Product> getProducts(String username) {
        if (username == null) {
            return this.productRepository.findAll();
        }

        return this.productRepository.findAllByCreator_Username(username);
    }

    @Transactional
    public Product addProduct(ProductRequest request, String username) {
        Account creator = this.accountRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials"));

        Product newProduct = new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock(),
                creator
        );

        return this.productRepository.save(newProduct);
    }

    @Transactional
    public void updateProduct(Long id, ProductRequest request, String username) {
        Account creator = this.accountRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials"));

        Product editableProduct = this.productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (!isOwnProduct(creator, editableProduct)) {
            throw new BadCredentialsException("Bad credentials");
        }

        if (request.getName() != null && !request.getName().isBlank()) {
            editableProduct.setName(request.getName());
        }

        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            editableProduct.setDescription(request.getDescription());
        }

        if (request.getPrice() != null) {
            editableProduct.setPrice(request.getPrice());
        }

        if (request.getStock() != null) {
            editableProduct.setStock(request.getStock());
        }

        this.productRepository.save(editableProduct);
    }

    @Transactional
    public void deleteProduct(Long id, String username) {
        Account creator = this.accountRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials"));

        Product deleteableProduct = this.productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (!isOwnProduct(creator, deleteableProduct)) {
            throw new BadCredentialsException("Bad credentials");
        }

        this.productRepository.delete(deleteableProduct);
    }

    private boolean isOwnProduct(Account account, Product product) {
        return account.getId().equals(product.getCreator().getId());
    }
}
