package com.example.data_warehouses_project_server.etl;

import com.example.data_warehouses_project_server.domain.olap.entity.*;
import com.example.data_warehouses_project_server.domain.olap.repository.DimCustomerRepository;
import com.example.data_warehouses_project_server.domain.olap.repository.DimLocationRepository;
import com.example.data_warehouses_project_server.domain.olap.repository.DimProductRepository;
import com.example.data_warehouses_project_server.domain.olap.repository.FactSaleRepository;
import com.example.data_warehouses_project_server.domain.oltp.entity.*;
import com.example.data_warehouses_project_server.domain.oltp.repository.AddressRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.CustomerRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.OrderItemRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.ProductRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
class OlapPipeline {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    private final DimCustomerRepository dimCustomerRepository;
    private final DimLocationRepository dimLocationRepository;
    private final DimProductRepository dimProductRepository;
    private final FactSaleRepository factSaleRepository;

    OlapPipeline(
            CustomerRepository customerRepository,
            AddressRepository addressRepository,
            ProductRepository productRepository,
            OrderItemRepository orderItemRepository,
            DimCustomerRepository dimCustomerRepository,
            DimLocationRepository dimLocationRepository,
            DimProductRepository dimProductRepository,
            FactSaleRepository factSaleRepository
    ) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.dimCustomerRepository = dimCustomerRepository;
        this.dimLocationRepository = dimLocationRepository;
        this.dimProductRepository = dimProductRepository;
        this.factSaleRepository = factSaleRepository;
    }

//    @Transactional("olapTransactionManager") // TODO: This doesn't compile. Should it be like this tho?
    @Scheduled(cron = "0 0 0 7 * *") // TODO: Change this for testing + add a way to trigger this by a POST request
    void runPipeline() {
        this.populateDimCustomer();
        this.populateDimLocation();
        this.populateDimProduct();
        this.populateFactSale();
    }

    private void populateDimCustomer() {
        List<CustomerEntity> oltpCustomers = this.customerRepository.findAll();

        for (CustomerEntity customer : oltpCustomers) {
            DimCustomerEntity dim = this.dimCustomerRepository.findByCustomerId(customer.getId())
                    .orElse(new DimCustomerEntity());

            dim.setCustomerId(customer.getId());
            dim.setUsername(customer.getAccount().getUsername());
            dim.setEmail(customer.getAccount().getEmail());
            dim.setFullName(customer.getFirstName() + " " + customer.getLastName());
            dim.setDateOfBirth(customer.getDateOfBirth());
            dim.setCreatedAt(customer.getCreatedAt());

            this.dimCustomerRepository.save(dim);
        }
    }

    private void populateDimLocation() {
        List<AddressEntity> oltpAddresses = this.addressRepository.findAll();

        for (AddressEntity address : oltpAddresses) {
            DimLocationEntity dim = this.dimLocationRepository.findByAddressId(address.getId())
                    .orElse(new DimLocationEntity());

            dim.setAddressId(address.getId());
            dim.setCity(address.getCity());
            dim.setRegion(address.getRegion());
            dim.setCountry(address.getCountry());

            this.dimLocationRepository.save(dim);
        }
    }

    private void populateDimProduct() {
        List<ProductEntity> oltpProducts = this.productRepository.findAll();

        for (ProductEntity product : oltpProducts) {
            DimProductEntity dim = this.dimProductRepository.findByProductId(product.getId())
                    .orElse(new DimProductEntity());

            dim.setProductId(product.getId());
            dim.setSku(product.getSku());
            dim.setProductName(product.getProductName());
            dim.setBrandName(product.getBrand().getBrandName());
            dim.setCategoryTree(this.buildCategoryTree(product.getCategory())); // TODO: Test this
            dim.setStatus(product.getStatus().toString());

            this.dimProductRepository.save(dim);
        }
    }

    private String buildCategoryTree(CategoryEntity category) {
        List<String> names = new ArrayList<>();

        CategoryEntity current = category;

        while (current != null) {
            names.add(current.getCategoryName());
            current = current.getParentCategory();
        }

        Collections.reverse(names);
        return String.join(" > ", names);
    }

    private void populateFactSale() {
        List<OrderItemEntity> items = this.orderItemRepository.findAll();

        for (OrderItemEntity item : items) {
            FactSaleEntity fact = this.factSaleRepository.findByOrderItemId(item.getId())
                    .orElse(new FactSaleEntity());

            DimTimeEntity time = null; // TODO: Complete this

            DimLocationEntity location = this.dimLocationRepository.findByAddressId(item.getOrder().getDelivery().getAddress().getId())
                    .orElseThrow(); // TODO: Decide what to do in this case

            DimCustomerEntity customer = this.dimCustomerRepository.findByCustomerId(item.getOrder().getCustomer().getId())
                    .orElseThrow(); // TODO: Decide what to do in this case

            DimProductEntity product = this.dimProductRepository.findByProductId(item.getProduct().getId())
                    .orElseThrow(); // TODO: Decide what to do in this case

            DimPaymentMethodEntity paymentMethod = null; // TODO: Complete this

            fact.setOrderId(item.getOrder().getId());
            fact.setOrderItemId(item.getId());
            fact.setUnitPrice(this.standardizePrice(item.getPrice())); // TODO: Test this
            fact.setQuantity(item.getQuantity());
            fact.setStatus(item.getOrder().getStatus().toString());
            fact.setShippedBy(item.getOrder().getDelivery().getShippedBy());

            fact.setTime(time);
            fact.setLocation(location);
            fact.setCustomer(customer);
            fact.setProduct(product);
            fact.setPaymentMethod(paymentMethod);

            this.factSaleRepository.save(fact);
        }
    }

    private BigDecimal standardizePrice(PriceEntity price) {
        return switch (price.getCurrency()) {
            case RON -> price.getValue();
            case EUR -> price.getValue().multiply(BigDecimal.valueOf(5.09));
            case USD -> price.getValue().multiply(BigDecimal.valueOf(4.39)) ;
        };
    }
}
