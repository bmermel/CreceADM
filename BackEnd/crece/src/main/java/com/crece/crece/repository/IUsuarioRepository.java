package com.crece.crece.repository;

import com.crece.crece.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface IUsuarioRepository extends JpaRepository<Usuario,Long> {

}
