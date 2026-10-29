package com.petsalud.backendpetsalud.dto.recompensa;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record RecompensaRequest(
        @NotNull @PositiveOrZero Integer puntos,
        @Size(max = 500) String descripcion
) {
}
