package pe.edu.upeu.lp2_back_gp1.services;

import pe.edu.upeu.lp2_back_gp1.dto.ProductoDTO;
import pe.edu.upeu.lp2_back_gp1.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    List<ProductoDTO> listarTodo();
    Optional<ProductoDTO> buscarPorId(Long id);
    ProductoDTO crear (ProductoDTO p);
    Optional<ProductoDTO> actualizar(Long id, ProductoDTO p);
    boolean eliminar (Long id);
}
