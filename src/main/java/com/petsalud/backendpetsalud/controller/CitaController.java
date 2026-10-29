package com.petsalud.backendpetsalud.controller;

import com.petsalud.backendpetsalud.dto.cita.CitaRequest;
import com.petsalud.backendpetsalud.dto.cita.CitaResponse;
import com.petsalud.backendpetsalud.entity.Cita;
import com.petsalud.backendpetsalud.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @PostMapping
    public ResponseEntity<CitaResponse> agendarCita(@Valid @RequestBody CitaRequest request) {
        Cita nuevaCita = citaService.agendarCita(
                request.mascotaId(),
                request.veterinarioId(),
                request.fechaHora(),
                request.motivo()
        );
        return ResponseEntity.ok(CitaResponse.from(nuevaCita));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse> obtenerCitaPorId(@PathVariable Long id) {
        return citaService.obtenerCitaPorId(id)
                .map(CitaResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<CitaResponse> actualizarEstadoCita(@PathVariable Long id, @RequestParam String estado) {
        Cita citaActualizada = citaService.actualizarEstadoCita(id, estado);
        return ResponseEntity.ok(CitaResponse.from(citaActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarCita(@PathVariable Long id) {
        citaService.cancelarCita(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<CitaResponse>> listarCitasPorMascota(@PathVariable Long mascotaId) {
        List<CitaResponse> citas = citaService.listarCitasPorMascota(mascotaId).stream()
                .map(CitaResponse::from)
                .toList();
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/veterinario/{veterinarioId}")
    public ResponseEntity<List<CitaResponse>> listarCitasPorVeterinario(@PathVariable Long veterinarioId) {
        List<CitaResponse> citas = citaService.listarCitasPorVeterinario(veterinarioId).stream()
                .map(CitaResponse::from)
                .toList();
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/entre-fechas")
    public ResponseEntity<List<CitaResponse>> listarCitasEntreFechas(
            @RequestParam String inicio,
            @RequestParam String fin) {
        LocalDateTime inicioParsed = LocalDateTime.parse(inicio);
        LocalDateTime finParsed = LocalDateTime.parse(fin);
        List<CitaResponse> citas = citaService.listarCitasEntreFechas(inicioParsed, finParsed).stream()
                .map(CitaResponse::from)
                .toList();
        return ResponseEntity.ok(citas);
    }
}
