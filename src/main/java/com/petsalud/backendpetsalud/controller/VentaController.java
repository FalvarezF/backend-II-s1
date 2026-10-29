package com.petsalud.backendpetsalud.controller;

import com.petsalud.backendpetsalud.dto.venta.VentaRequest;
import com.petsalud.backendpetsalud.dto.venta.VentaResponse;
import com.petsalud.backendpetsalud.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaResponse> realizarVenta(@Valid @RequestBody VentaRequest request) {
        return ResponseEntity.ok(VentaResponse.from(
                ventaService.realizarVenta(request.usuarioId(), request.productosIds())
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> obtenerVentaPorId(@PathVariable Long id) {
        return ventaService.obtenerVentaPorId(id)
                .map(VentaResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<VentaResponse>> listarVentasPorUsuario(@PathVariable Long usuarioId) {
        List<VentaResponse> ventas = ventaService.listarVentasPorUsuario(usuarioId).stream()
                .map(VentaResponse::from)
                .toList();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping
    public ResponseEntity<List<VentaResponse>> listarTodasLasVentas() {
        List<VentaResponse> ventas = ventaService.listarTodasLasVentas().stream()
                .map(VentaResponse::from)
                .toList();
        return ResponseEntity.ok(ventas);
    }
}
