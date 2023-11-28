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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private IEdificioRepository edificioRepository;
    @Autowired
    private IRolUsuarioRepository rolUsuarioRepository;
    @Autowired
    private ITipoUsuarioRepository tipoUsuarioRepository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private PasswordEncoder encoder;


    @Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository, IEdificioRepository edificioRepository, IRolUsuarioRepository rolUsuarioRepository, ITipoUsuarioRepository tipoUsuarioRepository, ObjectMapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.edificioRepository = edificioRepository;
        this.rolUsuarioRepository = rolUsuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.mapper = mapper;
    }

    public void guardarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = mapper.convertValue(usuarioDTO, Usuario.class);
        RolUsuario rol = rolUsuarioRepository.findById(usuarioDTO.getIdRolUsuario()).orElse(null);
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(usuarioDTO.getIdTipoUsuario()).orElse(null);
        Edificio edificio = edificioRepository.findById(usuarioDTO.getIdEdificio()).orElse(null);

        if (rol != null && tipoUsuario != null && edificio != null) {
            usuario.setTipoUsuario(tipoUsuario);
            usuario.setRolUsuario(rol);
            usuario.setEdificio(edificio);

            // Log para verificar el valor del ID antes de guardar
            System.out.println("ID antes de guardar: " + usuario.getId());



            usuarioRepository.save(usuario);

            // Log para verificar el valor del ID después de guardar
            System.out.println("ID después de guardar: " + usuario.getId());
        }
    }

    public UsuarioDTO leerUsuario(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        UsuarioDTO usuarioDTO = null;

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuarioDTO = mapper.convertValue(usuario, UsuarioDTO.class);
        }

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
    public List<Usuario> getUsuarios (){
        return usuarioRepository.findAll();
    }
}
