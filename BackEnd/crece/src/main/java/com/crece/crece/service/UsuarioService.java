package com.crece.crece.service;


import com.crece.crece.model.Edificio;
import com.crece.crece.model.RolUsuario;
import com.crece.crece.model.TipoUsuario;
import com.crece.crece.model.Usuario;
import com.crece.crece.model.dto.UsuarioDTO;
import com.crece.crece.repository.IEdificioRepository;
import com.crece.crece.repository.IRolUsuarioRepository;
import com.crece.crece.repository.ITipoUsuarioRepository;
import com.crece.crece.repository.IUsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private IUsuarioRepository usuarioRepository;
    private IEdificioRepository edificioRepository;
    private IRolUsuarioRepository rolUsuarioRepository;
    private ITipoUsuarioRepository tipoUsuarioRepository;
    private ObjectMapper mapper;

    @Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository, IEdificioRepository edificioRepository, IRolUsuarioRepository rolUsuarioRepository, ITipoUsuarioRepository tipoUsuarioRepository, ObjectMapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.edificioRepository = edificioRepository;
        this.rolUsuarioRepository = rolUsuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.mapper = mapper;
    }

    private void guardarUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = mapper.convertValue(usuarioDTO, Usuario.class);
        RolUsuario rol = rolUsuarioRepository.findById(usuarioDTO.getIdRolUsuario()).get();
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(usuarioDTO.getIdTipoUsuario()).get();
        Edificio edificio = edificioRepository.findById(usuarioDTO.getIdEdificio()).get();

        if(rol != null && tipoUsuario != null && edificio != null ){
            usuario.setTipoUsuario(tipoUsuario);
            usuario.setRolUsuario(rol);
            usuario.setEdificio(edificio);
            usuarioRepository.save(usuario);
        }

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

    public Usuario convertirDtoAUsuario(UsuarioDTO usuarioDTO){
        return mapper.convertValue(usuarioDTO, Usuario.class);
    }
}
