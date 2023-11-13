package com.crece.crece.repository;


import com.crece.crece.model.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IArchivosRepository extends JpaRepository<Archivo, Long>{
}
