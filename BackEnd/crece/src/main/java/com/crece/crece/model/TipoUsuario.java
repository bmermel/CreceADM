package com.crece.crece.model;


import com.crece.crece.model.enums.Tipos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tipoUsuario")
public class TipoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipos tipo;

    @OneToMany(mappedBy = "tipoUsuario")
    private List<Usuario> usuarios;
}
