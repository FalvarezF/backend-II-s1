package com.petsalud.backendpetsalud.config;

import com.petsalud.backendpetsalud.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/*Gran reglamento del sistema de seguridad de la aplicacion, 
// aqui se configuran los endpoints publicos y protegidos, .
 el filtro de autenticacion, el encriptador de contraseñas y authenticationmanager
*/

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // instancia de JwtAuthfilter ya que es quien atrapa la peticion y valida el token
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    //injeccion por constructor 
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    //endpoint publicos 
                        .requestMatchers(
                                "/api/usuarios/registro",
                                "/api/auth/registro",
                                "/api/auth/login"
                        ).permitAll()

                    // endpoints protegidos por roles                        
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/veterinario/**").hasAnyRole("VETERINARIO", "ADMIN")
                    
                    //otros endpoints requieren autenticacion
                        .anyRequest().authenticated()
                )

                //session management para que no se cree una sesion y se use el token en cada peticion
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                //deshabilitar autenticacion basica y login por formulario
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable())

                //agregar el filtro de autenticacion antes del filtro de autenticacion por usuario y contraseña
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
    }

    //este bean es para encriptar la contraseña del usuario antes de guardarla en la base de datos y para comparar la contraseña ingresada con la almacenada
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //este bean es para poder inyectar el AuthenticationManager en el controlador de autenticacion y poder autenticar al usuario con su email y contraseña
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
