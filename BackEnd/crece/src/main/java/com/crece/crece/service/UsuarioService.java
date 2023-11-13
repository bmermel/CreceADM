package com.crece.crece.service;


import com.crece.crece.model.Usuario;
import com.crece.crece.model.dto.UsuarioDTO;
import com.crece.crece.repository.IUsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    ObjectMapper mapper;

    private void guardarUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = mapper.convertValue(usuarioDTO, Usuario.class);
        usuarioRepository.save(usuario);
    }

    public void crearUsuario(UsuarioDTO usuarioDTO) {
        guardarUsuario(usuarioDTO);
    }

    public UsuarioDTO leerUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        UsuarioDTO usuarioDTO = null;
        if(usuario.isPresent())
            usuarioDTO = mapper.convertValue(usuario, UsuarioDTO.class);
        return usuarioDTO;
    }

    public void modificarUsuario(UsuarioDTO usuarioDTO) {
        guardarUsuario(usuarioDTO);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
}
}
