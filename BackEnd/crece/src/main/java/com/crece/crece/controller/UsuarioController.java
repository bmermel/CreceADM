package com.crece.crece.controller;

import com.crece.crece.model.dto.UsuarioDTO;
import com.crece.crece.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping()
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioDTO usuarioDTO){
        usuarioService.crearUsuario(usuarioDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public UsuarioDTO getUsuario(@PathVariable Long id){
        return usuarioService.leerUsuario(id);
    }

    @PutMapping
    public ResponseEntity<?> modificarUsuario(@RequestBody UsuarioDTO usuarioDTO){
        usuarioService.modificarUsuario(usuarioDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        ResponseEntity<String> response = null;
        usuarioService.eliminarUsuario(id);
        response = ResponseEntity.status(HttpStatus.OK).body("Eliminado");
        return response;
    }
}
