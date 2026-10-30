package pe.edu.upeu.lp2_back_gp1.dto;

import java.math.BigDecimal;

public record DetalleResponseDTO(
        String producto,
        Integer cantidad,
        BigDecimal preciounitario,
        BigDecimal subtotal
) {
}
