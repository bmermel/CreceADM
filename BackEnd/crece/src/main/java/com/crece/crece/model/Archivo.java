package com.crece.crece.model;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "archivos")
@NoArgsConstructor
@Builder
public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String filePath;

    @JoinColumn(name = "edificio_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Edificio edificio;

}

