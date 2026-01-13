package com.example.data_warehouses_project_server.product.category;

import com.example.data_warehouses_project_server.domain.oltp.entity.CategoryEntity;
import com.example.data_warehouses_project_server.domain.oltp.repository.CategoryRepository;
import com.example.data_warehouses_project_server.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class CategoryService {

    private final CategoryRepository categoryRepository;

    CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    List<CategoryResponse> getAllCategories() {
        return this.categoryRepository.findAll().stream().map(CategoryResponse::fromEntity).toList();
    }

    CategoryResponse getCategoryById(Long id) {
        return this.categoryRepository.findById(id)
                .map(CategoryResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }

    @Transactional
    Long createCategory(CreateCategoryForm form) {
        Optional<CategoryEntity> parentCategory = this.categoryRepository.findById(form.getParentCategoryId());

        if (parentCategory.isEmpty()) {
            throw new NotFoundException("Parent category not found");
        }

        return this.categoryRepository.save(new CategoryEntity(
                form.getName(),
                parentCategory.get()
        )).getId();
    }

    @Transactional
    void deleteCategory(Long id) {
        this.categoryRepository.deleteById(id);
    }
}
