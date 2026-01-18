package com.example.data_warehouses_project_server.product;

import com.example.data_warehouses_project_server.domain.oltp.constant.PriceStatus;
import com.example.data_warehouses_project_server.domain.oltp.entity.*;
import com.example.data_warehouses_project_server.domain.oltp.repository.*;
import com.example.data_warehouses_project_server.exception.BadRequestException;
import com.example.data_warehouses_project_server.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
class ProductService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final PriceRepository priceRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    ProductService(
            ProductRepository productRepository,
            InventoryRepository inventoryRepository,
            PriceRepository priceRepository,
            BrandRepository brandRepository,
            CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.priceRepository = priceRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    List<ProductResponse> getAllProducts() {
        return this.productRepository.findAllWithBrandAndCategoryAndInventory().stream()
                .map(ProductResponse::fromEntity).toList();
    }

    ProductResponse getProductById(Long id) {
        return this.productRepository.findById(id)
                .map(ProductResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Transactional
    Long createProduct(CreateProductForm form) {
        BrandEntity brand = this.brandRepository.findById(form.getBrandId())
                .orElseThrow(() -> new NotFoundException("Brand not found"));

        CategoryEntity category = this.categoryRepository.findById(form.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        ProductEntity product = this.productRepository.save(new ProductEntity(
                form.getSku(),
                form.getName(),
                form.getDescription(),
                form.getStatus(),
                brand,
                category
        ));

        this.inventoryRepository.save(new InventoryEntity(
                Objects.requireNonNullElse(form.getStock(), 0),
                OffsetDateTime.now(),
                product
        ));

        this.priceRepository.save(new PriceEntity(
                form.getDefaultPrice(),
                form.getPriceCurrency(),
                PriceStatus.DEFAULT,
                OffsetDateTime.now(),
                product
        ));

        return product.getId();
    }

    @Transactional
    void deleteProduct(Long id) { // TODO: Should not be do-able if this product is in some OrderItems
        this.priceRepository.deleteAllByProductId(id);
        this.inventoryRepository.deleteAllByProductId(id);
        this.productRepository.deleteById(id);
    }

    @Transactional
    void updateStock(Long id, Integer stock) {
        ProductEntity product = this.productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        InventoryEntity inventory = product.getInventory();
        inventory.setStockAvailable(stock);
    }

    List<PriceResponse> getAllPrices() {
        return this.priceRepository.findAll().stream().map(PriceResponse::fromEntity).toList();
    }

    List<PriceResponse> getAllPricesByProduct(Long id) {
        return this.priceRepository.findAllByProductId(id).stream().map(PriceResponse::fromEntity).toList();
    }

    PriceResponse getPriceById(Long id) {
        return this.priceRepository.findById(id)
                .map(PriceResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Price not found"));
    }

    @Transactional
    Long createPrice(Long id, CreatePriceForm form) {
        ProductEntity product = this.productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.getPrices().stream()
                .filter(price -> price.getStatus() == PriceStatus.ACTIVE)
                .forEach(price -> price.setStatus(PriceStatus.INACTIVE));

        return this.priceRepository.save(new PriceEntity(
                form.getPrice(),
                form.getCurrency(),
                PriceStatus.ACTIVE,
                OffsetDateTime.now(),
                product
        )).getId();
    }

    @Transactional
    void deletePrice(Long id) { // TODO: Should not be able to delete prices if it's associated with an OrderItem
        Optional<PriceEntity> price = this.priceRepository.findById(id);

        if(price.isPresent() && price.get().getStatus() == PriceStatus.DEFAULT) {
            throw new BadRequestException("Cannot delete price because price status is DEFAULT");
        }

        this.priceRepository.deleteById(id);
    }
}
