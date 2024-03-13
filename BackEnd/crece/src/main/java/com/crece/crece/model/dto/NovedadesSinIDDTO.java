package com.crece.crece.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NovedadesSinIDDTO {
    private String titulo;
    private String texto;
    private LocalDate fecha;
    private Boolean sendEmail;
    private Long edificioId;
}