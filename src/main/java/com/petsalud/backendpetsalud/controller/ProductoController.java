package com.petsalud.backendpetsalud.controller;

import com.petsalud.backendpetsalud.dto.producto.ProductoRequest;
import com.petsalud.backendpetsalud.dto.producto.ProductoResponse;
import com.petsalud.backendpetsalud.entity.Producto;
import com.petsalud.backendpetsalud.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponse> agregarProducto(@Valid @RequestBody ProductoRequest request) {
        Producto producto = toEntity(request);
        return ResponseEntity.ok(ProductoResponse.from(productoService.agregarProducto(producto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id)
                .map(ProductoResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(ProductoResponse.from(
                productoService.actualizarProducto(id, toEntity(request))
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listarProductos() {
        List<ProductoResponse> productos = productoService.listarProductos().stream()
                .map(ProductoResponse::from)
                .toList();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<ProductoResponse>> listarProductosPorCategoria(@RequestParam String categoria) {
        List<ProductoResponse> productos = productoService.listarProductosPorCategoria(categoria).stream()
                .map(ProductoResponse::from)
                .toList();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<ProductoResponse>> listarProductosConStockBajo(@RequestParam Integer cantidad) {
        List<ProductoResponse> productos = productoService.listarProductosConStockBajo(cantidad).stream()
                .map(ProductoResponse::from)
                .toList();
        return ResponseEntity.ok(productos);
    }

    private Producto toEntity(ProductoRequest request) {
        return Producto.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .precio(request.precio())
                .stock(request.stock())
                .categoria(request.categoria())
                .build();
    }
}
