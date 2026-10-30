package pe.edu.upeu.lp2_back_gp1.dto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record PersonaDTO(
        @Parameter(hidden = true)
        Long idPersona,

        @NotBlank(message = "Los nombres son obligatorios")
        String nombres,

        @NotBlank(message = "El email es obligatorio")
        String email,

        String telefono,

        String direccion,

        @Parameter(hidden = true)
        LocalDateTime creadoEn
) {
}
