package pe.edu.upeu.lp2_back_gp1.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.lp2_back_gp1.dto.PersonaDTO;
import pe.edu.upeu.lp2_back_gp1.entity.Persona;
import pe.edu.upeu.lp2_back_gp1.repository.PersonaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements IPersonaService {

    private final PersonaRepository personaRepository;

    public PersonaServiceImpl(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaDTO> listarTodo() {
        return personaRepository.findAll().stream()
                .map(this::converTODTO)
                .toList();
    }

    @Override
    @Transactional
    public Optional<PersonaDTO> buscarPorId(Long id) {
        return personaRepository.findById(id)
                .map(this::converTODTO);
    }

    @Override
    @Transactional
    public PersonaDTO crear(PersonaDTO dto) {
        Persona per = new Persona();

        per.setNombres(dto.nombres());
        per.setEmail(dto.email());
        per.setTelefono(dto.telefono());
        per.setDireccion(dto.direccion());

        return converTODTO(personaRepository.save(per));
    }

    @Override
    @Transactional
    public Optional<PersonaDTO> actualizar(Long id, PersonaDTO dto) {
        return personaRepository.findById(id).map(per -> {

            per.setNombres(dto.nombres());
            per.setEmail(dto.email());
            per.setTelefono(dto.telefono());
            per.setDireccion(dto.direccion());

            return converTODTO(personaRepository.save(per));
        });
    }

    @Override
    public boolean eliminar(Long id) {
        if (personaRepository.existsById(id)) {
            personaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Método de autoayuda de conversión
    private PersonaDTO converTODTO(Persona p) {
        return new PersonaDTO(
                p.getIdPersona(),
                p.getNombres(),
                p.getEmail(),
                p.getTelefono(),
                p.getDireccion(),
                p.getCreadoEn()
        );
    }
}