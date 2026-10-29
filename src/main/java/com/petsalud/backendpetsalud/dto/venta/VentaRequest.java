package com.petsalud.backendpetsalud.dto.venta;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record VentaRequest(
        @NotNull @Positive Long usuarioId,
        @NotEmpty List<@NotNull @Positive Long> productosIds
) {
}
