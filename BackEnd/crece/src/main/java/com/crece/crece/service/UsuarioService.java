package com.crece.crece.service;


import com.crece.crece.model.Edificio;
import com.crece.crece.model.RolUsuario;
import com.crece.crece.model.TipoUsuario;
import com.crece.crece.model.Usuario;
import com.crece.crece.model.dto.ActualizarUsuarioDTO;
import com.crece.crece.model.dto.GetUsuarioDTO;
import com.crece.crece.model.dto.UsuarioDTO;
import com.crece.crece.repository.IEdificioRepository;
import com.crece.crece.repository.IRolUsuarioRepository;
import com.crece.crece.repository.ITipoUsuarioRepository;
import com.crece.crece.repository.IUsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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



            usuarioRepository.save(usuario);
            System.out.println(usuario);

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


    public void modificarUsuario(ActualizarUsuarioDTO actualizarUsuarioDTO) {
        guardarUsuario(mapper.convertValue(actualizarUsuarioDTO, UsuarioDTO.class));
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
}

    public List<GetUsuarioDTO> getUsuarios() {

        List<Usuario> usuarioList = usuarioRepository.findAll();
        List<GetUsuarioDTO> usuarioDTOList = new ArrayList<>();

        for (Usuario usuario : usuarioList) {
            GetUsuarioDTO user = mapper.convertValue(usuario, GetUsuarioDTO.class);

            user.setRolUsuario(usuario.getRolUsuario());
            user.setTipoUsuario(usuario.getTipoUsuario());
            user.setEdificio(usuario.getEdificio());
            usuarioDTOList.add(user);
        }

        return usuarioDTOList;
}

}
