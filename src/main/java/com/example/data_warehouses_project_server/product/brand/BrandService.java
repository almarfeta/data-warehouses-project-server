package com.example.data_warehouses_project_server.product.brand;

import com.example.data_warehouses_project_server.domain.oltp.entity.BrandEntity;
import com.example.data_warehouses_project_server.domain.oltp.repository.BrandRepository;
import com.example.data_warehouses_project_server.exception.BadRequestException;
import com.example.data_warehouses_project_server.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BrandService {

    private final BrandRepository brandRepository;

    BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    List<BrandResponse> getAllBrands() {
        return this.brandRepository.findAll().stream().map(BrandResponse::fromEntity).toList();
    }

    BrandResponse getBrandById(Long id) {
        return this.brandRepository.findById(id)
                .map(BrandResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Brand not found"));
    }

    @Transactional
    Long createBrand(CreateBrandForm form) {
        if (this.brandRepository.existsByBrandName(form.getName())) {
            throw new BadRequestException("Brand already exists");
        }

        return this.brandRepository.save(new BrandEntity(form.getName())).getId();
    }

    @Transactional
    void deleteBrand(Long id) { // TODO: This should not be possible if there are products with this brand
        this.brandRepository.deleteById(id);
    }
}
