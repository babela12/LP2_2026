package pe.edu.upeu.lp2_back_gp1.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.lp2_back_gp1.dto.DetalleResponseDTO;
import pe.edu.upeu.lp2_back_gp1.dto.ItemVentaDTO;
import pe.edu.upeu.lp2_back_gp1.dto.PedidoResponseDTO;
import pe.edu.upeu.lp2_back_gp1.dto.VentaDTO;
import pe.edu.upeu.lp2_back_gp1.entity.DetallePedido;
import pe.edu.upeu.lp2_back_gp1.entity.Pedido;
import pe.edu.upeu.lp2_back_gp1.entity.Persona;
import pe.edu.upeu.lp2_back_gp1.entity.Producto;
import pe.edu.upeu.lp2_back_gp1.exception.InsufficientStockException;
import pe.edu.upeu.lp2_back_gp1.exception.ResourceNotFoundException;
import pe.edu.upeu.lp2_back_gp1.repository.PedidoRepository;
import pe.edu.upeu.lp2_back_gp1.repository.PersonaRepository;
import pe.edu.upeu.lp2_back_gp1.repository.ProductoRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl implements IPedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final PersonaRepository personaRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, ProductoRepository productoRepository, PersonaRepository personaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    @Transactional
    public PedidoResponseDTO realizarPedido(VentaDTO dto) {
        //1. validar cliente
        Persona persona = personaRepository.findById(dto.idPersona())
                .orElseThrow(()-> new ResourceNotFoundException("Cliente no encontrado"));
        //2. crear cabecera del pedido
        Pedido pedido = new Pedido();
        pedido.setPersona(persona);
        pedido.setEstado("pendiente");
        pedido.setFechaPedido(LocalDateTime.now());

        List<DetallePedido> detalles = new ArrayList<>();
        BigDecimal totalPedido = BigDecimal.ZERO;

        //3. Procesamos cada item del carrito
        for (ItemVentaDTO item : dto.items()) {
            Producto producto = productoRepository.findById(item.idProducto())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(("ProductoID: " + item.idProducto() + " no existe ")));
            //validar stock
            if (producto.getStock() < item.cantidad()) {
                throw new InsufficientStockException("Stock insuficiente");
            }
            //actualizar stock en BD
            producto.setStock(producto.getStock() - item.cantidad());
            productoRepository.save(producto);

            //Crear detalle
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(item.cantidad());
            detalle.setPrecioUnitario(producto.getPrecio());

            BigDecimal subtotal = producto.getPrecio().multiply(new BigDecimal((item.cantidad())));
            detalle.setSubtotal(subtotal);

            detalles.add(detalle);
            totalPedido = totalPedido.add(subtotal);
        }
        //guardar el pedido y su detalle
            pedido.setTotal(totalPedido);
            pedido.setDetalles(detalles);
            Pedido pedidoGuardado = pedidoRepository.save(pedido);

            //convertimos los detalles a DTO
            List<DetalleResponseDTO> productosResponse = pedidoGuardado.getDetalles().stream()
                    .map(d-> new DetalleResponseDTO(
                            d.getProducto().getNombre(),
                            d.getCantidad(),
                            d.getPrecioUnitario(),
                            d.getSubtotal()
                    )
                    ).toList();
            return new PedidoResponseDTO(
                    pedidoGuardado.getIdPedido(),
                    persona.getNombres(),
                    pedidoGuardado.getTotal(),
                    pedidoGuardado.getEstado(),
                    pedidoGuardado.getFechaPedido(),
                    productosResponse
            );


    }
}
