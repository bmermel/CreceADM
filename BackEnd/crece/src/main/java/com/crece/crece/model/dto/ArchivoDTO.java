package com.crece.crece.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
 @Setter
    public class ArchivoDTO {
        private Long id;
        private String categoria;
        private String descripcion;
        private LocalDate fechaCarga;
        private String destinatario;
        private String filePath;
        private String alias;

        private String tipoUsuario;
    }
