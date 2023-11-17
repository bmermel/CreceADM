package com.crece.crece.controller;

import com.crece.crece.model.dto.EdificioDTO;
import com.crece.crece.model.dto.TipoUsuarioDto;
import com.crece.crece.service.TipoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tipo")
public class TipoUsuarioController {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @PostMapping()
    public ResponseEntity<?> crearTipoUsuario(@RequestBody TipoUsuarioDto tipoUsuarioDto){
        tipoUsuarioService.guardarTipoUsuario(tipoUsuarioDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
