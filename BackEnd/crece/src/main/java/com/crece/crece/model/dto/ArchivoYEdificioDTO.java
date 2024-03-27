package com.crece.crece.model.dto;

import com.crece.crece.model.Edificio;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
   public class ArchivoYEdificioDTO {
       private Long id;
       private String categoria;
       private String descripcion;
       private LocalDate fechaCarga;
       private String filePath;
       private String alias;
       private String edificioNombre;
        private String tipoUsuario;

}
