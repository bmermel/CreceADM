package com.crece.crece.repository;

import com.crece.crece.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface IUsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByEmail(String userEmail);
}
