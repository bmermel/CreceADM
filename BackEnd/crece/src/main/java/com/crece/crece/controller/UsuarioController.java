package com.crece.crece.controller;

import com.crece.crece.model.Edificio;
import com.crece.crece.model.RolUsuario;
import com.crece.crece.model.TipoUsuario;
import com.crece.crece.model.Usuario;
import com.crece.crece.model.dto.EdificioDTO;
import com.crece.crece.model.dto.RolUsuarioDto;
import com.crece.crece.model.dto.TipoUsuarioDto;
import com.crece.crece.model.dto.UsuarioDTO;
import com.crece.crece.service.EdificioService;
import com.crece.crece.service.RolUsuarioService;
import com.crece.crece.service.TipoUsuarioService;
import com.crece.crece.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolUsuarioService rolUsuarioService;

    @Autowired
    private EdificioService edificioService;

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @PostMapping()
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioDTO usuarioDTO){
        usuarioService.crearUsuario(usuarioDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
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
