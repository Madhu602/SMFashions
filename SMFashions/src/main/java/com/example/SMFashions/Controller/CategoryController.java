package com.example.SMFashions.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SMFashions.Entity.Category;
import com.example.SMFashions.Enum.CategoryType;
import com.example.SMFashions.Service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // ADMIN: Add category
    @PostMapping("/admin")
    public ResponseEntity<Category> addCategory(
            @RequestParam String name,
            @RequestParam CategoryType type
    ) {
        return ResponseEntity.ok(
                categoryService.addCategory(name, type)
        );
    }

    // UI: Get categories by type
    @GetMapping("/{type}")
    public ResponseEntity<List<Category>> getCategories(
            @PathVariable CategoryType type
    ) {
        return ResponseEntity.ok(
                categoryService.getCategoriesByType(type)
        );
    }
}
