package com.crece.crece.repository;

import com.crece.crece.model.Novedades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository


public interface NovedadesRepository extends JpaRepository<Novedades,Long> {
}
