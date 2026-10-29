package com.petsalud.backendpetsalud.dto.venta;

import com.petsalud.backendpetsalud.entity.Venta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VentaResponse(
        Long id,
        LocalDateTime fecha,
        BigDecimal total,
        Long usuarioId,
        List<Long> productosIds
) {
    public static VentaResponse from(Venta venta) {
        return new VentaResponse(
                venta.getId(),
                venta.getFecha(),
                venta.getTotal(),
                venta.getUsuario() == null ? null : venta.getUsuario().getId(),
                venta.getProductos() == null
                        ? List.of()
                        : venta.getProductos().stream().map(producto -> producto.getId()).toList()
        );
    }
}
