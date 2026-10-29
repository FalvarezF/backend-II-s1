package com.petsalud.backendpetsalud.dto.cita;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CitaRequest(
        @NotNull @Positive Long mascotaId,
        @NotNull @Positive Long veterinarioId,
        @NotNull @Future LocalDateTime fechaHora,
        @NotBlank @Size(max = 500) String motivo
) {
}
