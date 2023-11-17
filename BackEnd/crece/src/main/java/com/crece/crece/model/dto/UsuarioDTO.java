package com.crece.crece.model.dto;

import com.crece.crece.model.Edificio;
import com.crece.crece.model.RolUsuario;
import com.crece.crece.model.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Long idRolUsuario;
    private Long idEdificio;
    private Long idTipoUsuario;


}
