package com.petsalud.backendpetsalud.controller;

import com.petsalud.backendpetsalud.dto.security.UsuarioRegistroRequest;
import com.petsalud.backendpetsalud.dto.usuario.UsuarioResponse;
import com.petsalud.backendpetsalud.dto.usuario.UsuarioUpdateRequest;
import com.petsalud.backendpetsalud.entity.Rol;
import com.petsalud.backendpetsalud.entity.Usuario;
import com.petsalud.backendpetsalud.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registrarUsuario(@Valid @RequestBody UsuarioRegistroRequest request) {
        Usuario usuario = Usuario.builder()
                .email(request.email())
                .password(request.password())
                .nombre(request.nombre())
                .apellido(request.apellido())
                .telefono(request.telefono())
                .direccion(request.direccion())
                .rol(Rol.CLIENTE)
                .build();
        return ResponseEntity.ok(UsuarioResponse.from(usuarioService.registrarUsuario(usuario)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id)
                .map(UsuarioResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioUpdateRequest request) {
        Usuario usuario = Usuario.builder()
                .nombre(request.nombre())
                .apellido(request.apellido())
                .telefono(request.telefono())
                .direccion(request.direccion())
                .build();
        return ResponseEntity.ok(UsuarioResponse.from(usuarioService.actualizarUsuario(id, usuario)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        List<UsuarioResponse> usuarios = usuarioService.listarUsuarios().stream()
                .map(UsuarioResponse::from)
                .toList();
        return ResponseEntity.ok(usuarios);
    }
}
