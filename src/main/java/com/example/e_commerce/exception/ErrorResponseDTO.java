package com.example.e_commerce.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}