package com.example.SMFashions.Dto;

import java.util.List;

import com.example.SMFashions.Enum.CategoryType;

import lombok.Data;

@Data
public class ProductResponseDTO {

    private Long id;
    private String name;
    private Double price;
    private Integer stock;
    private String size;
    private String color;

    private Long categoryId;
    private String categoryName;
    private CategoryType categoryType;

    private List<String> imageUrls;
}
