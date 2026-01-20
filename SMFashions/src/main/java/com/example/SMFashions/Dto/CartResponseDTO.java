package com.example.SMFashions.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartResponseDTO {

    private Long cartId;
    private List<CartItemResponseDTO> items;
    private Double grandTotal;
}
