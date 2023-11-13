package com.crece.crece.model.dto;

import lombok.Getter;
import lombok.Setter;

    @Getter
    @Setter
    public class EdificioDTO {
        private Long id;
        private String nombre;
        private String direccion;
        private String razonSocial;
        private int cuit;
    }
