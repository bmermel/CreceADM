package com.crece.crece.controller;

import com.crece.crece.model.dto.RolUsuarioDto;
import com.crece.crece.model.dto.TipoUsuarioDto;
import com.crece.crece.service.RolUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rol")
public class RolUsuarioController {
    @Autowired
    private RolUsuarioService rolUsuarioService;

    @PostMapping()
    public ResponseEntity<?> crearRolUsuario(@RequestBody RolUsuarioDto rolUsuarioDto){
        rolUsuarioService.guardarRolusuario(rolUsuarioDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
