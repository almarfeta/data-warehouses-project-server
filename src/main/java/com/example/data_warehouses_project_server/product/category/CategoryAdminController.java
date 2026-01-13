package com.example.data_warehouses_project_server.product.category;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(CategoryAdminController.DEFAULT_ENDPOINT_MAPPING)
class CategoryAdminController {

    static final String DEFAULT_ENDPOINT_MAPPING = "/api/category";

    private final CategoryService categoryService;

    CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(this.categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody @Valid CreateCategoryForm form) {
        Long categoryId = this.categoryService.createCategory(form);
        return ResponseEntity.created(URI.create(DEFAULT_ENDPOINT_MAPPING + "/" + categoryId)).body("Category created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        this.categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
