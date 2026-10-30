package pe.edu.upeu.lp2_back_gp1.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long idPedido,
        String cliente,
        BigDecimal total,
        String estado,
        LocalDateTime fecha,
        List<DetalleResponseDTO> productos
) {
}
