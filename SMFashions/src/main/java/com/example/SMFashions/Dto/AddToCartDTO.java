package com.example.SMFashions.Dto;

import lombok.Data;

@Data
public class AddToCartDTO {
    private Long accountId;
    private Long productId;
    private Integer quantity;
}
