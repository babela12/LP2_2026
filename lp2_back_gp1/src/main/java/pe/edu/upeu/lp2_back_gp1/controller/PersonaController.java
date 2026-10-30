package pe.edu.upeu.lp2_back_gp1.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.lp2_back_gp1.dto.PersonaDTO;
import pe.edu.upeu.lp2_back_gp1.exception.ResourceNotFoundException;
import pe.edu.upeu.lp2_back_gp1.services.IPersonaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/personas")
public class PersonaController {

    private final IPersonaService personaService;

    public PersonaController(IPersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public ResponseEntity<List<PersonaDTO>> listar() {
        return ResponseEntity.ok(personaService.listarTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaDTO> obtener(@PathVariable Long id) {
        return personaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Persona no encontrada " + id));
    }

    @PostMapping
    public ResponseEntity<PersonaDTO> crear(
            @Valid @RequestBody PersonaDTO dto) {
        return new ResponseEntity<>(
                personaService.crear(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PersonaDTO dto) {
        return personaService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Persona no actualizada " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (personaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        throw new ResourceNotFoundException(
                "No se pudo eliminar: " + id);
    }
}
