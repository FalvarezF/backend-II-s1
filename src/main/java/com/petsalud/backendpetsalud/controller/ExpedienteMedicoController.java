package com.petsalud.backendpetsalud.controller;

import com.petsalud.backendpetsalud.dto.expediente.ExpedienteMedicoRequest;
import com.petsalud.backendpetsalud.dto.expediente.ExpedienteMedicoResponse;
import com.petsalud.backendpetsalud.entity.ExpedienteMedico;
import com.petsalud.backendpetsalud.service.ExpedienteMedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/expedientes")
public class ExpedienteMedicoController {

    @Autowired
    private ExpedienteMedicoService expedienteMedicoService;

    @PostMapping
    public ResponseEntity<ExpedienteMedicoResponse> agregarRegistro(
            @RequestParam Long mascotaId,
            @RequestParam Long veterinarioId,
            @Valid @RequestBody ExpedienteMedicoRequest request) {
        ExpedienteMedico registro = ExpedienteMedico.builder()
                .descripcion(request.descripcion())
                .diagnostico(request.diagnostico())
                .tratamiento(request.tratamiento())
                .build();
        ExpedienteMedico nuevoRegistro = expedienteMedicoService.agregarRegistro(mascotaId, veterinarioId, registro);
        return ResponseEntity.ok(ExpedienteMedicoResponse.from(nuevoRegistro));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpedienteMedicoResponse> obtenerRegistroPorId(@PathVariable Long id) {
        return expedienteMedicoService.obtenerRegistroPorId(id)
                .map(ExpedienteMedicoResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<ExpedienteMedicoResponse>> listarRegistrosPorMascota(@PathVariable Long mascotaId) {
        List<ExpedienteMedicoResponse> registros = expedienteMedicoService.listarRegistrosPorMascota(mascotaId).stream()
                .map(ExpedienteMedicoResponse::from)
                .toList();
        return ResponseEntity.ok(registros);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegistro(@PathVariable Long id) {
        expedienteMedicoService.eliminarRegistro(id);
        return ResponseEntity.noContent().build();
    }
}
