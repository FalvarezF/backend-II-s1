package com.petsalud.backendpetsalud.controller;

import com.petsalud.backendpetsalud.dto.mascota.MascotaRequest;
import com.petsalud.backendpetsalud.dto.mascota.MascotaResponse;
import com.petsalud.backendpetsalud.entity.Mascota;
import com.petsalud.backendpetsalud.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @PostMapping("/propietario/{propietarioId}")
    public ResponseEntity<MascotaResponse> registrarMascota(
            @PathVariable Long propietarioId,
            @Valid @RequestBody MascotaRequest request) {
        Mascota mascota = Mascota.builder()
                .nombre(request.nombre())
                .especie(request.especie())
                .raza(request.raza())
                .fechaNacimiento(request.fechaNacimiento())
                .sexo(request.sexo())
                .build();
        return ResponseEntity.ok(MascotaResponse.from(mascotaService.registrarMascota(mascota, propietarioId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponse> obtenerMascotaPorId(@PathVariable Long id) {
        return mascotaService.obtenerMascotaPorId(id)
                .map(MascotaResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponse> actualizarMascota(
            @PathVariable Long id,
            @Valid @RequestBody MascotaRequest request) {
        Mascota mascota = Mascota.builder()
                .nombre(request.nombre())
                .especie(request.especie())
                .raza(request.raza())
                .fechaNacimiento(request.fechaNacimiento())
                .sexo(request.sexo())
                .build();
        return ResponseEntity.ok(MascotaResponse.from(mascotaService.actualizarMascota(id, mascota)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMascota(@PathVariable Long id) {
        mascotaService.eliminarMascota(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/propietario/{propietarioId}")
    public ResponseEntity<List<MascotaResponse>> listarMascotasPorPropietario(@PathVariable Long propietarioId) {
        List<MascotaResponse> mascotas = mascotaService.listarMascotasPorPropietario(propietarioId).stream()
                .map(MascotaResponse::from)
                .toList();
        return ResponseEntity.ok(mascotas);
    }
}
