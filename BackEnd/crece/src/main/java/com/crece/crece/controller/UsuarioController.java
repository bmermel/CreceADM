package com.crece.crece.controller;

import com.crece.crece.model.dto.GetUsuarioDTO;
import com.crece.crece.model.dto.UsuarioDTO;
import com.crece.crece.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    private  UsuarioService usuarioService;

    @GetMapping("/all")
    public List<GetUsuarioDTO> getUsuarios(){
        return usuarioService.getUsuarios();
    }
}
