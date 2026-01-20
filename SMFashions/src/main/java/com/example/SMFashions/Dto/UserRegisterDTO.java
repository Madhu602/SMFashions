package com.example.SMFashions.Dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserRegisterDTO {
    private String name;
    private String mobile;
    
    @Column(unique = true)
    private String email;

    private String password;
    private String address;
}
