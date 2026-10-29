package com.petsalud.backendpetsalud.controller;

import com.petsalud.backendpetsalud.dto.recompensa.RecompensaRequest;
import com.petsalud.backendpetsalud.dto.recompensa.RecompensaResponse;
import com.petsalud.backendpetsalud.entity.Recompensa;
import com.petsalud.backendpetsalud.service.RecompensaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recompensas")
public class RecompensaController {

    @Autowired
    private RecompensaService recompensaService;

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<RecompensaResponse> agregarRecompensa(
            @PathVariable Long usuarioId,
            @Valid @RequestBody RecompensaRequest request) {
        Recompensa recompensa = Recompensa.builder()
                .puntos(request.puntos())
                .descripcion(request.descripcion())
                .build();
        return ResponseEntity.ok(RecompensaResponse.from(
                recompensaService.agregarRecompensa(usuarioId, recompensa)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecompensaResponse> obtenerRecompensaPorId(@PathVariable Long id) {
        return recompensaService.obtenerRecompensaPorId(id)
                .map(RecompensaResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RecompensaResponse>> listarRecompensasPorUsuario(@PathVariable Long usuarioId) {
        List<RecompensaResponse> recompensas = recompensaService.listarRecompensasPorUsuario(usuarioId).stream()
                .map(RecompensaResponse::from)
                .toList();
        return ResponseEntity.ok(recompensas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecompensa(@PathVariable Long id) {
        recompensaService.eliminarRecompensa(id);
        return ResponseEntity.noContent().build();
    }
}
