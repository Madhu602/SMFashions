package com.example.SMFashions.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponseDTO {
    private String orderNumber;
    private Double totalAmount;
    private String message;
}
