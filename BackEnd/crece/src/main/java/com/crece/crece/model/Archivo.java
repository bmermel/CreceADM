package com.crece.crece.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@Table(name = "archivos")
public class Archivo {

    @Id
    @GeneratedValue
    private Long id;
    private String categoria;
    private String descripcion;
    private String fechaDeIngreso;
    private String destinatario;
}

