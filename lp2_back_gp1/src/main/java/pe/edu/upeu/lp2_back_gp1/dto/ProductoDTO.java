package pe.edu.upeu.lp2_back_gp1.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductoDTO(
        @Parameter(hidden = true)
        Long idProducto,
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        String descripcion,
        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01")
        BigDecimal precio,
        @NotNull(message = "el stock es obligatorio")
        @Min(value = 0)
        Integer stock,
        @Parameter(hidden = true)
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        String imagen
) {
}
