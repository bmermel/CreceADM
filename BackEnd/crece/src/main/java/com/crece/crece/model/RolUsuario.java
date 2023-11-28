package com.crece.crece.model;

import com.crece.crece.model.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rolUsuario")
public class RolUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rol", nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles rol;

    @OneToMany(mappedBy = "rolUsuario")
    private List<Usuario> usuarios;
}
