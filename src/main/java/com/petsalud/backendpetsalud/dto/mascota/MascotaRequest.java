package com.petsalud.backendpetsalud.dto.mascota;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MascotaRequest(
        @NotBlank @Size(max = 100) String nombre,
        @NotBlank @Size(max = 50) String especie,
        @Size(max = 100) String raza,
        @PastOrPresent LocalDate fechaNacimiento,
        @Size(max = 30) String sexo
) {
}
