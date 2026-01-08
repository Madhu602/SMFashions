package com.example.SMFashions.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SMFashions.Dto.ProductCreateRequestDTO;
import com.example.SMFashions.Dto.ProductResponseDTO;
import com.example.SMFashions.Enum.CategoryType;
import com.example.SMFashions.Service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // =============================
    // ADMIN: Add Product
    // =============================
    @PostMapping("/admin")
    public ResponseEntity<ProductResponseDTO> addProduct(
            @Valid @RequestBody ProductCreateRequestDTO dto
    ) {
        return ResponseEntity.ok(productService.addProduct(dto));
    }

    // =============================
    // USER: Get Products by Type
    // =============================
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByType(
            @PathVariable CategoryType type
    ) {
        return ResponseEntity.ok(
                productService.getProductsByType(type)
        );
    }

    // =============================
    // USER: Get Products by Category
    // =============================
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(
            @PathVariable Long categoryId
    ) {
        return ResponseEntity.ok(
                productService.getProductsByCategory(categoryId)
        );
    }
}
