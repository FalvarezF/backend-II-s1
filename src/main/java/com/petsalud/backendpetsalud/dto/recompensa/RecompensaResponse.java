package com.petsalud.backendpetsalud.dto.recompensa;

import com.petsalud.backendpetsalud.entity.Recompensa;

import java.time.LocalDateTime;

public record RecompensaResponse(
        Long id,
        Integer puntos,
        String descripcion,
        LocalDateTime fecha,
        Long usuarioId
) {
    public static RecompensaResponse from(Recompensa recompensa) {
        return new RecompensaResponse(
                recompensa.getId(),
                recompensa.getPuntos(),
                recompensa.getDescripcion(),
                recompensa.getFecha(),
                recompensa.getUsuario() == null ? null : recompensa.getUsuario().getId()
        );
    }
}
