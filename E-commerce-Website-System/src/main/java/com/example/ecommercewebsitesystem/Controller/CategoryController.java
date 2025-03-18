package com.example.ecommercewebsitesystem.Controller;

import com.example.ecommercewebsitesystem.Api.ApiResponse;
import com.example.ecommercewebsitesystem.Model.Category;
import com.example.ecommercewebsitesystem.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity getAllCategory() {
        ArrayList<Category> categories = categoryService.getCategories();

        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        boolean isAdded = categoryService.addCategory(category);
        if (isAdded) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Category added successfully"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Category already exists"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable String id, @RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        boolean isUpdated = categoryService.updateCategory(id, category);

        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Category updated successfully"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Category not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable String id) {
        boolean isDeleted = categoryService.deleteCategory(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Category deleted successfully"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Category not found"));
    }
}
