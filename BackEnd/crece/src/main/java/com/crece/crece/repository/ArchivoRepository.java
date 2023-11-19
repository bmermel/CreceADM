package com.crece.crece.repository;

import com.crece.crece.model.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
    Optional<Archivo> findByName(String fileName);
}
