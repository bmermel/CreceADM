package com.crece.crece.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NuevoArchivoDTO {
    private String categoria;
    private String descripcion;
    private String filePath;
    private String alias;
    private Long edificioId;
}