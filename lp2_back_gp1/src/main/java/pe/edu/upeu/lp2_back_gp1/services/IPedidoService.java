package pe.edu.upeu.lp2_back_gp1.services;

import pe.edu.upeu.lp2_back_gp1.dto.PedidoResponseDTO;
import pe.edu.upeu.lp2_back_gp1.dto.VentaDTO;

public interface IPedidoService {
    PedidoResponseDTO realizarPedido(VentaDTO dto);
}
