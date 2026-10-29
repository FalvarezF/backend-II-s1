package com.petsalud.backendpetsalud.dto.producto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductoRequest(
        @NotBlank @Size(max = 150) String nombre,
        @Size(max = 1000) String descripcion,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal precio,
        @NotNull @PositiveOrZero Integer stock,
        @NotBlank @Size(max = 100) String categoria
) {
}
