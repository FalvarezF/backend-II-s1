package com.petsalud.backendpetsalud.dto.notificacion;

import com.petsalud.backendpetsalud.entity.Notificacion;

import java.time.LocalDateTime;

public record NotificacionResponse(
        Long id,
        String mensaje,
        String tipo,
        LocalDateTime fecha,
        Boolean leida,
        Long usuarioId
) {
    public static NotificacionResponse from(Notificacion notificacion) {
        return new NotificacionResponse(
                notificacion.getId(),
                notificacion.getMensaje(),
                notificacion.getTipo(),
                notificacion.getFecha(),
                notificacion.getLeida(),
                notificacion.getUsuario() == null ? null : notificacion.getUsuario().getId()
        );
    }
}
