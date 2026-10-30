package pe.edu.upeu.lp2_back_gp1.dto;

public record UsuarioDTO(
        Long idUsuario,
        String usuario,
        String rol,
        String nombrePersona,
        String email
) {
}
