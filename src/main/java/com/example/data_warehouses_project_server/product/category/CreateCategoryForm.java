package com.example.data_warehouses_project_server.product.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

class CreateCategoryForm {

    @NotNull(message = "Parent category ID should not be null")
    private Long parentCategoryId;

    @NotBlank(message = "Category name should not be blank")
    private String name;

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
