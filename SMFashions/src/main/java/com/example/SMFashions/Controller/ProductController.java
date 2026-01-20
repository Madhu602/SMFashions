package com.example.SMFashions.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.SMFashions.Dto.ProductCreateRequestDTO;
import com.example.SMFashions.Dto.ProductResponseDTO;
import com.example.SMFashions.Enum.CategoryType;
import com.example.SMFashions.Repository.ProductRepository;
import com.example.SMFashions.Service.CloudinaryService;
import com.example.SMFashions.Service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private CloudinaryService cloudinaryService;
    
    @Autowired
    private ProductRepository productRepository;



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
    
    @PostMapping(
    	    value = "/admin",
    	    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    	)
    	public ResponseEntity<ProductResponseDTO> addProduct(
    	        @RequestPart("data") String data,
    	        @RequestPart("images") MultipartFile[] images
    	) throws IOException {

    	    ObjectMapper mapper = new ObjectMapper();
    	    ProductCreateRequestDTO dto =
    	            mapper.readValue(data, ProductCreateRequestDTO.class);

    	    List<String> imageUrls = new ArrayList<>();
    	    for (MultipartFile file : images) {
    	        imageUrls.add(cloudinaryService.uploadFile(file));
    	    }

    	    dto.setImageUrls(imageUrls);
    	    return ResponseEntity.ok(productService.addProduct(dto));
    	}


}
