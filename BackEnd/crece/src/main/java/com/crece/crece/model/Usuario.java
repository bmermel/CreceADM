package com.crece.crece.model;


import lombok.*;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @JoinColumn(name = "tipoUsuario_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private TipoUsuario tipoUsuario;

    @JoinColumn(name = "rolUsuario_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private RolUsuario rolUsuario;

    @JoinColumn(name = "edificio_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Edificio edificio;

}
