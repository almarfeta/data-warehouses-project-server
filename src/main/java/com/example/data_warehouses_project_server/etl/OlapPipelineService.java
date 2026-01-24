package com.example.data_warehouses_project_server.etl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.data_warehouses_project_server.domain.olap.entity.DimCustomerEntity;
import com.example.data_warehouses_project_server.domain.olap.entity.DimLocationEntity;
import com.example.data_warehouses_project_server.domain.olap.entity.DimPaymentMethodEntity;
import com.example.data_warehouses_project_server.domain.olap.entity.DimProductEntity;
import com.example.data_warehouses_project_server.domain.olap.entity.DimTimeEntity;
import com.example.data_warehouses_project_server.domain.olap.entity.FactSaleEntity;
import com.example.data_warehouses_project_server.domain.olap.repository.DimCustomerRepository;
import com.example.data_warehouses_project_server.domain.olap.repository.DimLocationRepository;
import com.example.data_warehouses_project_server.domain.olap.repository.DimPaymentMethodRepository;
import com.example.data_warehouses_project_server.domain.olap.repository.DimProductRepository;
import com.example.data_warehouses_project_server.domain.olap.repository.DimTimeRepository;
import com.example.data_warehouses_project_server.domain.olap.repository.FactSaleRepository;
import com.example.data_warehouses_project_server.domain.oltp.constant.PaymentMethod;
import com.example.data_warehouses_project_server.domain.oltp.constant.Role;
import com.example.data_warehouses_project_server.domain.oltp.entity.AccountEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.AddressEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.CategoryEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.CustomerEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.OrderItemEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.PriceEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.ProductEntity;
import com.example.data_warehouses_project_server.domain.oltp.repository.AccountRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.AddressRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.CategoryRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.OrderItemRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.ProductRepository;

@Service
class OlapPipelineService {

    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    private final DimTimeRepository dimTimeRepository;
    private final DimCustomerRepository dimCustomerRepository;
    private final DimLocationRepository dimLocationRepository;
    private final DimProductRepository dimProductRepository;
    private final DimPaymentMethodRepository dimPaymentMethodRepository;
    private final FactSaleRepository factSaleRepository;

    OlapPipelineService(
            AccountRepository accountRepository,
            AddressRepository addressRepository,
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            OrderItemRepository orderItemRepository,
            DimTimeRepository dimTimeRepository,
            DimCustomerRepository dimCustomerRepository,
            DimLocationRepository dimLocationRepository,
            DimProductRepository dimProductRepository,
            DimPaymentMethodRepository dimPaymentMethodRepository,
            FactSaleRepository factSaleRepository
    ) {
        this.accountRepository = accountRepository;
        this.addressRepository = addressRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.dimTimeRepository = dimTimeRepository;
        this.dimCustomerRepository = dimCustomerRepository;
        this.dimLocationRepository = dimLocationRepository;
        this.dimProductRepository = dimProductRepository;
        this.dimPaymentMethodRepository = dimPaymentMethodRepository;
        this.factSaleRepository = factSaleRepository;
    }

    @Async // TODO: Add logging also
    void runPipeline(boolean populateDimTime, boolean populateDimPaymentMethod, LocalDate startDate, LocalDate endDate) {
        if (populateDimTime) {
            this.populateDimTime(startDate, endDate);
        }

        if (populateDimPaymentMethod) {
            this.populateDimPaymentMethod();
        }

        this.populateDimCustomer();
        this.populateDimLocation();
        this.populateDimProduct();
        this.populateFactSale();
    }

    private void populateDimTime(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate and endDate must not be null");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be < endDate");
        }

        List<DimTimeEntity> dates = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (this.dimTimeRepository.existsByDateValue(date)) {
                continue;
            }

            dates.add(new DimTimeEntity(
                    date,
                    date.getDayOfMonth(),
                    date.getMonthValue(),
                    date.getYear(),
                    date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                    date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)
            ));
        }

        if (!dates.isEmpty()) {
            this.dimTimeRepository.saveAll(dates);
        }
    }

    private void populateDimPaymentMethod() {
        Arrays.stream(PaymentMethod.values())
                .map(Enum::toString)
                .filter(paymentMethod -> !this.dimPaymentMethodRepository.existsByPaymentMethod(paymentMethod))
                .forEach(paymentMethod -> this.dimPaymentMethodRepository.save(new DimPaymentMethodEntity(paymentMethod)));
    }

    private void populateDimCustomer() {
        List<AccountEntity> oltpAccounts = this.accountRepository.findAllWithCustomer().stream()
                .filter(account -> account.getRole() == Role.USER)
                .toList();

        for (AccountEntity oltpAccount : oltpAccounts) {
            DimCustomerEntity dim = this.dimCustomerRepository.findByCustomerId(oltpAccount.getCustomer().getId())
                    .orElse(new DimCustomerEntity());

            dim.setCustomerId(oltpAccount.getCustomer().getId());
            dim.setUsername(oltpAccount.getUsername());
            dim.setEmail(oltpAccount.getEmail());
            dim.setFullName(this.joinFullName(oltpAccount.getCustomer()));
            dim.setDateOfBirth(oltpAccount.getCustomer().getDateOfBirth());
            dim.setCreatedAt(oltpAccount.getCustomer().getCreatedAt());

            this.dimCustomerRepository.save(dim);
        }
    }

    private void populateDimLocation() {
        List<AddressEntity> oltpAddresses = this.addressRepository.findAll();

        for (AddressEntity oltpAddress : oltpAddresses) {
            DimLocationEntity dim = this.dimLocationRepository.findByAddressId(oltpAddress.getId())
                    .orElse(new DimLocationEntity());

            dim.setAddressId(oltpAddress.getId());
            dim.setCity(oltpAddress.getCity());
            dim.setRegion(oltpAddress.getRegion());
            dim.setCountry(oltpAddress.getCountry());

            this.dimLocationRepository.save(dim);
        }
    }

    private void populateDimProduct() {
        Map<Long, CategoryEntity> categoryMap = this.categoryRepository.findAll().stream()
                .collect(Collectors.toMap(CategoryEntity::getId, Function.identity()));

        List<ProductEntity> oltpProducts = this.productRepository.findAllWithBrandAndCategoryAndInventory();

        for (ProductEntity oltpProduct : oltpProducts) {
            DimProductEntity dim = this.dimProductRepository.findByProductId(oltpProduct.getId())
                    .orElse(new DimProductEntity());

            dim.setProductId(oltpProduct.getId());
            dim.setSku(oltpProduct.getSku());
            dim.setProductName(oltpProduct.getProductName());
            dim.setBrandName(oltpProduct.getBrand().getBrandName());
            dim.setCategoryTree(this.buildCategoryTree(oltpProduct.getCategory(), categoryMap));
            dim.setStatus(oltpProduct.getStatus().toString());

            this.dimProductRepository.save(dim);
        }
    }

    private void populateFactSale() {
        List<OrderItemEntity> items = this.orderItemRepository.findAllWithPriceAndOrderAndDelivery();

        for (OrderItemEntity item : items) {
            FactSaleEntity fact = this.factSaleRepository.findByOrderItemId(item.getId()).orElse(new FactSaleEntity());

            DimTimeEntity time = this.dimTimeRepository.findByDateValue(item.getOrder().getCreatedAt().toLocalDate()).orElse(null);
            DimLocationEntity location = this.dimLocationRepository.findByAddressId(item.getOrder().getDelivery().getAddress().getId()).orElse(null);
            DimCustomerEntity customer = this.dimCustomerRepository.findByCustomerId(item.getOrder().getCustomer().getId()).orElse(null);
            DimProductEntity product = this.dimProductRepository.findByProductId(item.getProduct().getId()).orElse(null);
            DimPaymentMethodEntity paymentMethod = this.dimPaymentMethodRepository.findByPaymentMethod(item.getOrder().getPaymentMethod().toString()).orElse(null);

            fact.setTime(time);
            fact.setLocation(location);
            fact.setCustomer(customer);
            fact.setProduct(product);
            fact.setPaymentMethod(paymentMethod);

            fact.setOrderId(item.getOrder().getId());
            fact.setOrderItemId(item.getId());
            fact.setUnitPrice(this.standardizePrice(item.getPrice()));
            fact.setQuantity(item.getQuantity());
            fact.setStatus(item.getOrder().getStatus().toString());
            fact.setShippedBy(item.getOrder().getDelivery().getShippedBy());

            this.factSaleRepository.save(fact);
        }
    }

    private String joinFullName(CustomerEntity customer) {
        return customer.getFirstName() + " " + customer.getLastName();
    }

    private String buildCategoryTree(CategoryEntity category, Map<Long, CategoryEntity> categoryMap) {
        List<String> names = new ArrayList<>();

        CategoryEntity current = categoryMap.get(category.getId());

        while (current != null) {
            names.add(current.getCategoryName());
            Long parentId = current.getParentCategory() != null ? current.getParentCategory().getId() : null;
            current = parentId != null ? categoryMap.get(parentId) : null;
        }

        Collections.reverse(names);
        return String.join(" > ", names);
    }

    private BigDecimal standardizePrice(PriceEntity price) {
        return switch (price.getCurrency()) {
            case RON -> price.getValue();
            case EUR -> price.getValue().multiply(BigDecimal.valueOf(5.09));
            case USD -> price.getValue().multiply(BigDecimal.valueOf(4.39)) ;
        };
    }
}
