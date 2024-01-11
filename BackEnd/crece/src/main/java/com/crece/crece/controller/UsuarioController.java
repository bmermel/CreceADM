package com.crece.crece.controller;

import com.crece.crece.model.Usuario;
import com.crece.crece.model.dto.ActualizarUsuarioDTO;
import com.crece.crece.model.dto.GetUsuarioDTO;
import com.crece.crece.model.dto.UsuarioDTO;
import com.crece.crece.service.UsuarioService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private  UsuarioService usuarioService;



    @GetMapping("/all")
    public List<GetUsuarioDTO> getUsuarios(){
        return usuarioService.getUsuarios();
    }

    @PutMapping("/{id}")
    public void modificarUsuario(@PathVariable Long id, @RequestBody ActualizarUsuarioDTO actualizarUsuarioDTO){
        UsuarioDTO user = usuarioService.leerUsuario(id);

        if(user != null){
            usuarioService.modificarUsuario(actualizarUsuarioDTO);
        }
    }
    @GetMapping("/emailsPorEdificio/{edificioId}")
    public ResponseEntity<List<String>> obtenerEmailsPorEdificio(@PathVariable Long edificioId) {
        List<String> emails = usuarioService.getEmailsPorEdificio(edificioId);
        return ResponseEntity.status(HttpStatus.OK).body(emails);
    }
}
