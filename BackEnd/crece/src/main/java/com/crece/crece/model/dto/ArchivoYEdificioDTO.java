package com.crece.crece.model.dto;

import com.crece.crece.model.Edificio;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
   public class ArchivoYEdificioDTO {
       private Long id;
       private String categoria;
       private String descripcion;
       private String fechaDeIngreso;
       private String destinatario;
       private String filePath;
       private String alias;

       private String edificioNombre;
   }
