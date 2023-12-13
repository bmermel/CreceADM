package com.crece.crece.controller;

import com.crece.crece.model.Usuario;
import com.crece.crece.model.dto.ActualizarUsuarioDTO;
import com.crece.crece.model.dto.GetUsuarioDTO;
import com.crece.crece.model.dto.UsuarioDTO;
import com.crece.crece.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
