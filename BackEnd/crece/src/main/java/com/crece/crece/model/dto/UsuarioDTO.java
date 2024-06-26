package com.crece.crece.model.dto;

import com.crece.crece.model.Edificio;
import com.crece.crece.model.RolUsuario;
import com.crece.crece.model.TipoUsuario;
import com.crece.crece.model.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String unidadFuncional;

    @JsonProperty
    private String password;
    private Long idRolUsuario;
    private Long idEdificio;
    private Long idTipoUsuario;
    private Boolean habilitado;

    private LocalDateTime ultimoAcceso;


}
