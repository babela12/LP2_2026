package pe.edu.upeu.lp2_back_gp1.services;

import pe.edu.upeu.lp2_back_gp1.dto.PersonaDTO;
import pe.edu.upeu.lp2_back_gp1.entity.Persona;

import java.util.List;
import java.util.Optional;

public interface IPersonaService {
    List<PersonaDTO> listarTodo();
    Optional<PersonaDTO> buscarPorId(Long id);
    PersonaDTO crear(PersonaDTO p);
    Optional<PersonaDTO> actualizar(Long id, PersonaDTO p);
    boolean eliminar(Long id);
}
