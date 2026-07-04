package com.producer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record OrderRequest(
        @NotBlank(message = "customerName es obligatorio") String customerName,
        @NotBlank(message = "product es obligatorio") String product,
        @Positive(message = "quantity debe ser mayor a 0") int quantity) {
}
