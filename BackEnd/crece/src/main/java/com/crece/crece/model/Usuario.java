package com.crece.crece.model;

import com.crece.crece.model.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Columns;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
    private String apellido;
    private String email;

    //cambiar a TipoDeUsuario
    private String rol;
    @JoinColumn(name = "edificios_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Edificio edificio;

}
