package com.crece.crece.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "edificio")
public class Edificio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "direccion", nullable = false)
    private String direccion;


    @Column(name = "razonSocial", nullable = false)
    private String razonSocial;

    @Column(name = "cuit", nullable = false)
    private int cuit;


    @OneToMany (mappedBy = "edificio")
    private List<Usuario> usuarios;
}

