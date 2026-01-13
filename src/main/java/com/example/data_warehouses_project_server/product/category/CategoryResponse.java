package com.example.data_warehouses_project_server.product.category;

import com.example.data_warehouses_project_server.domain.oltp.entity.CategoryEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
class CategoryResponse {

    private Long categoryId;
    private Long parentCategoryId;
    private String categoryName;

    private CategoryResponse(Long categoryId, Long parentCategoryId, String categoryName) {
        this.categoryId = categoryId;
        this.parentCategoryId = parentCategoryId;
        this.categoryName = categoryName;
    }

    public static CategoryResponse fromEntity(CategoryEntity category) {
        CategoryEntity parentCategory = category.getParentCategory();

        return new CategoryResponse(
                category.getId(),
                parentCategory != null ? parentCategory.getId() : null,
                category.getCategoryName()
        );
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
