package com.example.SMFashions.Dto;

import java.util.List;

import com.example.SMFashions.Enum.CategoryType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class ProductCreateRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private Integer stock;

    private String size;
    private String color;

    @NotNull
    private Long categoryId;

    @NotNull
    private CategoryType type;

    // IMAGE URLs (uploaded separately)
    private List<String> imageUrls;
}
