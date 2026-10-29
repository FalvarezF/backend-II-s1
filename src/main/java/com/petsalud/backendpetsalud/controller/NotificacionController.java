package com.petsalud.backendpetsalud.controller;

import com.petsalud.backendpetsalud.dto.notificacion.NotificacionRequest;
import com.petsalud.backendpetsalud.dto.notificacion.NotificacionResponse;
import com.petsalud.backendpetsalud.entity.Notificacion;
import com.petsalud.backendpetsalud.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<NotificacionResponse> enviarNotificacion(
            @PathVariable Long usuarioId,
            @Valid @RequestBody NotificacionRequest request) {
        Notificacion notificacion = Notificacion.builder()
                .mensaje(request.mensaje())
                .tipo(request.tipo())
                .build();
        return ResponseEntity.ok(NotificacionResponse.from(
                notificacionService.enviarNotificacion(usuarioId, notificacion)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponse> obtenerNotificacionPorId(@PathVariable Long id) {
        return notificacionService.obtenerNotificacionPorId(id)
                .map(NotificacionResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionResponse>> listarNotificacionesPorUsuario(@PathVariable Long usuarioId) {
        List<NotificacionResponse> notificaciones = notificacionService.listarNotificacionesPorUsuario(usuarioId).stream()
                .map(NotificacionResponse::from)
                .toList();
        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<List<NotificacionResponse>> listarNotificacionesNoLeidas(@PathVariable Long usuarioId) {
        List<NotificacionResponse> notificaciones = notificacionService.listarNotificacionesNoLeidas(usuarioId).stream()
                .map(NotificacionResponse::from)
                .toList();
        return ResponseEntity.ok(notificaciones);
    }

    @PutMapping("/{id}/leida")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable Long id) {
        notificacionService.marcarComoLeida(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id) {
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}
