package pe.edu.upeu.lp2_back_gp1.services;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.lp2_back_gp1.dto.ProductoDTO;
import pe.edu.upeu.lp2_back_gp1.entity.Producto;
import pe.edu.upeu.lp2_back_gp1.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements IProductoService {

    private final ProductoRepository productoRepository;
    private final IFileStorageService fileStorageService;

    public ProductoServiceImpl(ProductoRepository productoRepository, IFileStorageService fileStorageService) {
        this.productoRepository = productoRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> listarTodo() {
        return productoRepository.findAll().stream().map(this::converTODTO).toList();
    }

    @Override
    @Transactional
    public Optional<ProductoDTO> buscarPorId(Long id) {
        return productoRepository.findById(id).map(this::converTODTO);
    }

    @Override
    @Transactional
    public ProductoDTO crear(ProductoDTO dto) {
       Producto pro = new Producto();
        pro.setNombre(dto.nombre());
        pro.setDescripcion(dto.descripcion());
        pro.setPrecio(dto.precio());
        pro.setStock(dto.stock());
        pro.setImagen(dto.imagen());
        return converTODTO(productoRepository.save(pro));
    }

    @Override
    @Transactional
    public Optional<ProductoDTO> actualizar(Long id, ProductoDTO dto) {
        /*return productoRepository.findById(id).map(pro ->{
            pro.setNombre(dto.nombre());
            pro.setDescripcion(dto.descripcion());
            pro.setPrecio(dto.precio());
            pro.setStock(dto.stock());
            pro.setImagen(dto.imagen());
            return converTODTO(productoRepository.save(pro));
        });*/
        return productoRepository.findById(id).map(p -> {
            // Lógica de limpieza de archivos:
            // Si el nombre de imagen que viene en el DTO es distinto al de la BD,
            // borramos el archivo físico anterior para no dejar basura.
            if (p.getImagen() != null && !p.getImagen().equals(dto.imagen())) {
                fileStorageService.delete(p.getImagen());
            }

            p.setNombre(dto.nombre());
            p.setDescripcion(dto.descripcion());
            p.setPrecio(dto.precio());
            p.setStock(dto.stock());
            p.setImagen(dto.imagen()); // Seteamos el nuevo nombre que viene del controlador

            return converTODTO (productoRepository.save(p));
        });
    }

    @Override
    public boolean eliminar(Long id) {
        return productoRepository.findById(id).map(p -> {
            // Borrar archivo físico
            if (p.getImagen() != null) {
                fileStorageService.delete(p.getImagen());
            }
            productoRepository.delete(p);
            return true;
        }).orElse(false);
    }

    //metodo de auto ayuda de conversion
    private ProductoDTO converTODTO(Producto p){
        String relativePath = "/lp2_back_gp1/images/" + p.getImagen();
        return new ProductoDTO(
                p.getIdProducto(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getStock(),
                relativePath
        );
    }
}
