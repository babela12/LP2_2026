package pe.edu.upeu.lp2_back_gp1.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.lp2_back_gp1.dto.ProductoDTO;
import pe.edu.upeu.lp2_back_gp1.exception.ResourceNotFoundException;
import pe.edu.upeu.lp2_back_gp1.services.IFileStorageService;
import pe.edu.upeu.lp2_back_gp1.services.IProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
    private final IProductoService productoService;
    private final IFileStorageService storageService;

    public ProductoController(IProductoService productoService, IFileStorageService storageService) {
        this.productoService = productoService;
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listar(){
        return ResponseEntity.ok(productoService.listarTodo());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtener(@PathVariable Long id){
        return productoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new ResourceNotFoundException("Producto no encontrado "+id));
    }
    /*@PostMapping
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoDTO dto){
        return new ResponseEntity<>(productoService.crear(dto), HttpStatus.CREATED);
    }*/
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ProductoDTO> crear(
            @ModelAttribute @org.springdoc.core.annotations.ParameterObject @Valid ProductoDTO dto,  // Cambiado de @RequestPart a @ModelAttribute
            @RequestPart(value = "file", required = false) MultipartFile file) {

        String nombreImagen = "no-image.png";
        if (file != null && !file.isEmpty()) {
            nombreImagen = storageService.store(file);
        }

        // Como los Records son inmutables, creamos uno nuevo con el nombre de la imagen
        ProductoDTO productoConImagen = new ProductoDTO(
                null,
                dto.nombre(),
                dto.descripcion(),
                dto.precio(),
                dto.stock(),
                nombreImagen
        );

        return new ResponseEntity<>(productoService.crear(productoConImagen), HttpStatus.CREATED);
    }
    /*@PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto){
        return productoService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new ResourceNotFoundException("Producto no actualizado "+id));
    }*/
    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ProductoDTO> actualizar(
            @PathVariable Long id,
            @ModelAttribute @org.springdoc.core.annotations.ParameterObject @Valid ProductoDTO dto,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        // 1. Obtener el producto actual para saber qué imagen tiene
        ProductoDTO productoActual = productoService.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        String nombreImagen = productoActual.imagen(); // Por defecto mantenemos la actual

        // 2. Si el usuario subió un nuevo archivo, lo guardamos
        if (file != null && !file.isEmpty()) {
            nombreImagen = storageService.store(file);
        }

        // 3. Creamos el DTO final con el nombre de la imagen (nueva o vieja)
        ProductoDTO dtoParaActualizar = new ProductoDTO(
                id, dto.nombre(), dto.descripcion(), dto.precio(), dto.stock(), nombreImagen
        );

        return productoService.actualizar(id, dtoParaActualizar)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Error al actualizar"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (productoService.eliminar(id)){
            return ResponseEntity.noContent().build();
        }
        throw new ResourceNotFoundException("No se pudo eliminar: "+ id);
    }

}
