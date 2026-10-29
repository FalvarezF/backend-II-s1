package com.petsalud.backendpetsalud.dto.cita;

import com.petsalud.backendpetsalud.entity.Cita;

import java.time.LocalDateTime;

public record CitaResponse(
        Long id,
        LocalDateTime fechaHora,
        String estado,
        String motivo,
        Long mascotaId,
        Long veterinarioId
) {
    public static CitaResponse from(Cita cita) {
        return new CitaResponse(
                cita.getId(),
                cita.getFechaHora(),
                cita.getEstado(),
                cita.getMotivo(),
                cita.getMascota() == null ? null : cita.getMascota().getId(),
                cita.getVeterinario() == null ? null : cita.getVeterinario().getId()
        );
    }
}
