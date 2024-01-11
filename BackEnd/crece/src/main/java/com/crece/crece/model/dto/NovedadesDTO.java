package com.crece.crece.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NovedadesDTO {
    private Long id;
    private String titulo;
    private String texto;
    private LocalDate fecha;
    private Boolean sendEmail;
    private Long edificioId; // Este campo representa el id del edificio en lugar de la entidad completa
}