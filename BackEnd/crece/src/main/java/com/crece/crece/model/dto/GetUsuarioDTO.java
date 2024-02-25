package com.crece.crece.model.dto;


import com.crece.crece.model.Edificio;
import com.crece.crece.model.Novedades;
import com.crece.crece.model.RolUsuario;
import com.crece.crece.model.TipoUsuario;
import com.crece.crece.model.enums.Roles;
import com.crece.crece.model.enums.Tipos;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@RequiredArgsConstructor
public class GetUsuarioDTO {
    private Long id;
    private String nombre;
    //private String apellido;
    private String email;
    private String edificio;
    private String tipoUsuario;
    private String rolUsuario;
    private String telefono;
    private String unidadFuncional;
    private Boolean habilitado;



}
