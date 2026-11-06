package com.petsalud.backendpetsalud.service;

import com.petsalud.backendpetsalud.dto.AuthResponse;
import com.petsalud.backendpetsalud.dto.LoginRequest;
import com.petsalud.backendpetsalud.dto.RegisterRequest;
import com.petsalud.backendpetsalud.entity.Rol;
import com.petsalud.backendpetsalud.entity.Usuario;
import com.petsalud.backendpetsalud.repository.UsuarioRepository;
import com.petsalud.backendpetsalud.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(LoginRequest request) {
        // 1. Autenticar credenciales usando el AuthenticationManager de Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        // 2. Si las credenciales son válidas, buscamos al usuario en la BD
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Email o contraseña inválidos"));

        // 3. Generar token JWT
        String jwtToken = jwtService.generateToken(usuario);

        // 4. Retornar respuesta estructurada
        return AuthResponse.builder()
                .token(jwtToken)
                .type("Bearer")
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .rol(usuario.getRol().name())
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        // 1. Validar si el email ya existe
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ya se encuentra registrado");
        }

        // 2. Determinar rol (por defecto CLIENTE si no se especifica)
        Rol rolAsignado = request.getRol() != null ? request.getRol() : Rol.CLIENTE;

        // 3. Crear nuevo usuario encriptando la contraseña con BCrypt
        Usuario nuevoUsuario = Usuario.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .rol(rolAsignado)
                .build();

        usuarioRepository.save(nuevoUsuario);

        // 4. Generar token JWT para que el usuario quede autenticado de inmediato
        String jwtToken = jwtService.generateToken(nuevoUsuario);

        return AuthResponse.builder()
                .token(jwtToken)
                .type("Bearer")
                .email(nuevoUsuario.getEmail())
                .nombre(nuevoUsuario.getNombre())
                .rol(nuevoUsuario.getRol().name())
                .build();
    }
}