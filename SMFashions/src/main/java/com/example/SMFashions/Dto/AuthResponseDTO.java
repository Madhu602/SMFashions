package com.example.SMFashions.Dto;

import com.example.SMFashions.Entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private Boolean isExists;
    private String message;
    private Account account;
}
