package pe.edu.upeu.lp2_back_gp1.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record VentaDTO(
        @NotNull
        Long idPersona,
        @NotEmpty
        List<ItemVentaDTO> items
) {
}
