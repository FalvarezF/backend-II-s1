package com.petsalud.backendpetsalud.dto.notificacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NotificacionRequest(
        @NotBlank @Size(max = 1000) String mensaje,
        @NotBlank @Size(max = 100) String tipo
) {
}
