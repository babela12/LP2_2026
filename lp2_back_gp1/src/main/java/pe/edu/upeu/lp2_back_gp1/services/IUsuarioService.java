package pe.edu.upeu.lp2_back_gp1.services;

import pe.edu.upeu.lp2_back_gp1.dto.RegistroDTO;
import pe.edu.upeu.lp2_back_gp1.dto.UsuarioDTO;
import pe.edu.upeu.lp2_back_gp1.entity.Persona;
import pe.edu.upeu.lp2_back_gp1.entity.Usuario;

import java.util.Optional;

public interface IUsuarioService {

    Optional<UsuarioDTO> registrarCliente(RegistroDTO dto);


}
