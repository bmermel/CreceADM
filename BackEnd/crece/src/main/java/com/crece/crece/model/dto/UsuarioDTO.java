package com.crece.crece.model.dto;

import com.crece.crece.model.Edificio;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String rol;
    private Edificio edificio;


}
