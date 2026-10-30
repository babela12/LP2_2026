package pe.edu.upeu.lp2_back_gp1.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "persona")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;

    @Column(nullable = false, length = 100)
    private String nombres;
    @Column(unique = true, nullable = false)
    private String email;
    private String telefono;
    private String direccion;
    @Column(name = "creado_en", insertable = false,updatable = false)
    private LocalDateTime creadoEn;

    public Persona() {
    }

    public Persona(Long idPersona, String nombres, String email, String telefono, String direccion, LocalDateTime creadoEn) {
        this.idPersona = idPersona;
        this.nombres = nombres;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.creadoEn = creadoEn;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }
}
