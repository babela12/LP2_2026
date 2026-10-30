package pe.edu.upeu.lp2_back_gp1.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemVentaDTO(
        @NotNull Long idProducto,
        @Min(1)
        Integer cantidad
) {
}
