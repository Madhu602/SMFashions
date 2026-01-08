package com.example.SMFashions.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SMFashions.Dto.ProductCreateRequestDTO;
import com.example.SMFashions.Dto.ProductResponseDTO;
import com.example.SMFashions.Entity.Category;
import com.example.SMFashions.Entity.Product;
import com.example.SMFashions.Entity.ProductImage;
import com.example.SMFashions.Enum.CategoryType;
import com.example.SMFashions.Repository.CategoryRepository;
import com.example.SMFashions.Repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // =============================
    // ADD PRODUCT
    // =============================
    public ProductResponseDTO addProduct(ProductCreateRequestDTO dto) {

        Category category = categoryRepository
                .findByIdAndActiveTrue(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Invalid category"));

        if (category.getType() != dto.getType()) {
            throw new RuntimeException("Category type mismatch");
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setSize(dto.getSize());
        product.setColor(dto.getColor());
        product.setCategory(category);

        // Handle images
        if (dto.getImageUrls() != null && !dto.getImageUrls().isEmpty()) {
            for (int i = 0; i < dto.getImageUrls().size(); i++) {
                ProductImage img = new ProductImage();
                img.setImageUrl(dto.getImageUrls().get(i));
                img.setPrimaryImage(i == 0);
                img.setProduct(product);
                product.getImages().add(img);
            }
        }

        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }

    // =============================
    // GET PRODUCTS BY CATEGORY
    // =============================
    public List<ProductResponseDTO> getProductsByCategory(Long categoryId) {

        return productRepository.findByCategory_Id(categoryId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =============================
    // GET PRODUCTS BY TYPE (KIDS / MEN / WOMEN)
    // =============================
    public List<ProductResponseDTO> getProductsByType(CategoryType type) {

        return productRepository.findByCategory_Type(type)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =============================
    // MAPPER
    // =============================
    private ProductResponseDTO mapToResponse(Product product) {

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setSize(product.getSize());
        dto.setColor(product.getColor());

        dto.setCategoryId(product.getCategory().getId());
        dto.setCategoryName(product.getCategory().getName());
        dto.setCategoryType(product.getCategory().getType());

        dto.setImageUrls(
                product.getImages()
                        .stream()
                        .map(ProductImage::getImageUrl)
                        .toList()
        );

        return dto;
    }
}
