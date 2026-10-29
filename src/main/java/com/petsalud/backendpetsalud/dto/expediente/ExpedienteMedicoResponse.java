package com.petsalud.backendpetsalud.dto.expediente;

import com.petsalud.backendpetsalud.entity.ExpedienteMedico;

import java.time.LocalDateTime;

public record ExpedienteMedicoResponse(
        Long id,
        LocalDateTime fecha,
        String descripcion,
        String diagnostico,
        String tratamiento,
        Long mascotaId,
        Long veterinarioId
) {
    public static ExpedienteMedicoResponse from(ExpedienteMedico expediente) {
        return new ExpedienteMedicoResponse(
                expediente.getId(),
                expediente.getFecha(),
                expediente.getDescripcion(),
                expediente.getDiagnostico(),
                expediente.getTratamiento(),
                expediente.getMascota() == null ? null : expediente.getMascota().getId(),
                expediente.getVeterinario() == null ? null : expediente.getVeterinario().getId()
        );
    }
}
