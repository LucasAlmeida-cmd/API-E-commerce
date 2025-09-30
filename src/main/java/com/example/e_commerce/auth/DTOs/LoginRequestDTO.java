package com.example.e_commerce.auth.DTOs;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String senha;
}
