package pe.edu.upeu.lp2_back_gp1.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.lp2_back_gp1.dto.RegistroDTO;
import pe.edu.upeu.lp2_back_gp1.dto.UsuarioDTO;
import pe.edu.upeu.lp2_back_gp1.entity.Persona;
import pe.edu.upeu.lp2_back_gp1.entity.Rol;
import pe.edu.upeu.lp2_back_gp1.entity.Usuario;
import pe.edu.upeu.lp2_back_gp1.exception.DuplicateResourceException;
import pe.edu.upeu.lp2_back_gp1.repository.PersonaRepository;
import pe.edu.upeu.lp2_back_gp1.repository.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(PersonaRepository personaRepository, UsuarioRepository usuarioRepository) {
        this.personaRepository = personaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public Optional<UsuarioDTO> registrarCliente(RegistroDTO dto) {
        //1. validar email unico
        if (personaRepository.existsByEmail(dto.email())){
            throw new DuplicateResourceException("El email '"+dto.email()+"' ya esta registrado");
        }

        //validar que el usuario sea unico
        if (usuarioRepository.existsByUsuario(dto.usuario())){
            throw new DuplicateResourceException("El nombre de usuario '"+dto.usuario()+"' ya existe:(");
        }

        //procedemos al registro del cliente
        Persona p = new Persona();
        p.setNombres(dto.nombre());
        p.setEmail(dto.email());
        p.setTelefono(dto.telefono());
        p.setDireccion(dto.direccion());
        Persona personaGuardada = personaRepository.save(p);
        Usuario u = new Usuario();
        u.setUsuario(dto.usuario());
        u.setPassword(dto.password());
        u.setPersona(personaGuardada);
        u.setRol(Rol.CLIENTE);
        Usuario usuarioGuardado = usuarioRepository.save(u);
        return Optional.of(
                new UsuarioDTO(
                        usuarioGuardado.getIdUsuario(),
                        usuarioGuardado.getUsuario(),
                        usuarioGuardado.getRol().name(),
                        usuarioGuardado.getPersona().getNombres(),
                        usuarioGuardado.getPersona().getEmail()
                )
        );

    }
}
