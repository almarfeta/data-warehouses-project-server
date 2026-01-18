package com.example.data_warehouses_project_server.product.category;

import jakarta.validation.constraints.NotBlank;

class CreateCategoryForm {

    @NotBlank(message = "Category name should not be blank")
    private String name;

    private Long parentCategoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
