package com.crece.crece.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetEdificioListDtoCompleto {
    private Long id;
    private String nombre;
    private String direccion;
    private String razonSocial;
    private int cuit;
}
