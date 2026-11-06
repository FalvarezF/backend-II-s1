package com.petsalud.backendpetsalud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record LoginRequest(
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato de email no es válido")
    String email,

    @NotBlank(message = "La contraseña es obligatoria")
    String password
) {}