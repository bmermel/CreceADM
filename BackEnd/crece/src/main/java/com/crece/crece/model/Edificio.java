package com.crece.crece.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@Table(name = "edificios")

public class Edificio {

    @Id
    @GeneratedValue

    private Long id;
    private String nombre;
    private String direccion;
    @Column(name = "razonSocial")
    private String razonSocial;
    private int cuit;
    @OneToMany (mappedBy = "edificio")
    private List<Usuario> usuarios;
}

