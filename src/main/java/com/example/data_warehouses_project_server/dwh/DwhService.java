package com.example.data_warehouses_project_server.dwh;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.data_warehouses_project_server.domain.olap.repository.DimCustomerRepository;
import com.example.data_warehouses_project_server.domain.olap.repository.DimLocationRepository;
import com.example.data_warehouses_project_server.domain.olap.repository.DimPaymentMethodRepository;
import com.example.data_warehouses_project_server.domain.olap.repository.DimProductRepository;
import com.example.data_warehouses_project_server.domain.olap.repository.DimTimeRepository;
import com.example.data_warehouses_project_server.domain.olap.repository.FactSaleRepository;

@Service
class DwhService {

    private final DimTimeRepository dimTimeRepository;
    private final DimLocationRepository dimLocationRepository;
    private final DimCustomerRepository dimCustomerRepository;
    private final DimProductRepository dimProductRepository;
    private final DimPaymentMethodRepository dimPaymentMethodRepository;
    private final FactSaleRepository factSaleRepository;

    DwhService(
            DimTimeRepository dimTimeRepository,
            DimLocationRepository dimLocationRepository,
            DimCustomerRepository dimCustomerRepository,
            DimProductRepository dimProductRepository,
            DimPaymentMethodRepository dimPaymentMethodRepository,
            FactSaleRepository factSaleRepository
    ) {
        this.dimTimeRepository = dimTimeRepository;
        this.dimLocationRepository = dimLocationRepository;
        this.dimCustomerRepository = dimCustomerRepository;
        this.dimProductRepository = dimProductRepository;
        this.dimPaymentMethodRepository = dimPaymentMethodRepository;
        this.factSaleRepository = factSaleRepository;
    }

    public List<DimTimeResponse> getAllDimTimes() {
        return this.dimTimeRepository.findAll().stream().map(DimTimeResponse::fromEntity).toList();
    }

    public List<DimLocationResponse> getAllDimLocations() {
        return this.dimLocationRepository.findAll().stream().map(DimLocationResponse::fromEntity).toList();
    }

    public List<DimCustomerResponse> getAllDimCustomers() {
        return this.dimCustomerRepository.findAll().stream().map(DimCustomerResponse::fromEntity).toList();
    }

    public List<DimProductResponse> getAllDimProducts() {
        return this.dimProductRepository.findAll().stream().map(DimProductResponse::fromEntity).toList();
    }

    public List<DimPaymentMethodResponse> getAllDimPaymentMethods() {
        return this.dimPaymentMethodRepository.findAll().stream().map(DimPaymentMethodResponse::fromEntity).toList();
    }

    public List<FactSaleResponse> getAllFactSales() {
        return this.factSaleRepository.findAll().stream().map(FactSaleResponse::fromEntity).toList();
    }
}
