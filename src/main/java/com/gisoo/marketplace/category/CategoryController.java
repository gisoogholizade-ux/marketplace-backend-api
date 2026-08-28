package com.gisoo.marketplace.category;

import com.gisoo.marketplace.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryRepository repository;
    public CategoryController(CategoryRepository repository) { this.repository = repository; }

    @GetMapping
    public ApiResponse<List<Category>> list() {
        return ApiResponse.success("Categories retrieved", repository.findAllByActiveTrueOrderByNameAsc());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Category> create(@Valid @RequestBody CreateCategoryRequest request) {
        String slug = request.slug().trim().toLowerCase();
        if (repository.existsBySlugIgnoreCase(slug)) throw new IllegalArgumentException("Category slug already exists");
        return ApiResponse.success("Category created", repository.save(new Category(request.name().trim(), slug)));
    }
}
