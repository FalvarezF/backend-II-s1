package com.petsalud.backendpetsalud.dto.expediente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExpedienteMedicoRequest(
        @NotBlank @Size(max = 2000) String descripcion,
        @NotBlank @Size(max = 1000) String diagnostico,
        @NotBlank @Size(max = 2000) String tratamiento
) {
}
