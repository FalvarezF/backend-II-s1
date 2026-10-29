package com.petsalud.backendpetsalud.dto.mascota;

import com.petsalud.backendpetsalud.entity.Mascota;

import java.time.LocalDate;

public record MascotaResponse(
        Long id,
        String nombre,
        String especie,
        String raza,
        LocalDate fechaNacimiento,
        String sexo,
        Long propietarioId
) {
    public static MascotaResponse from(Mascota mascota) {
        return new MascotaResponse(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                mascota.getFechaNacimiento(),
                mascota.getSexo(),
                mascota.getPropietario() == null ? null : mascota.getPropietario().getId()
        );
    }
}
