package com.crece.crece.service;


import com.crece.crece.model.Edificio;
import com.crece.crece.model.RolUsuario;
import com.crece.crece.model.TipoUsuario;
import com.crece.crece.model.Usuario;
import com.crece.crece.model.dto.*;
import com.crece.crece.repository.IEdificioRepository;
import com.crece.crece.repository.IRolUsuarioRepository;
import com.crece.crece.repository.ITipoUsuarioRepository;
import com.crece.crece.repository.IUsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
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

    private final Logger log = Logger.getLogger(UsuarioService.class);

    public void guardarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = mapper.convertValue(usuarioDTO, Usuario.class);
        RolUsuario rol = rolUsuarioRepository.findById(usuarioDTO.getIdRolUsuario()).orElse(null);
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(usuarioDTO.getIdTipoUsuario()).orElse(null);
        Edificio edificio = edificioRepository.findById(usuarioDTO.getIdEdificio()).orElse(null);

        if (rol != null && tipoUsuario != null && edificio != null) {
            usuario.setTipoUsuario(tipoUsuario);
            usuario.setRolUsuario(rol);
            usuario.setEdificio(edificio);

            log.info("usuario registrado con exito");

            usuarioRepository.save(usuario);


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
    public UsuarioDashboardDTO getUsuarioByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Aquí puedes construir el UsuarioDTO según tus necesidades
        UsuarioDashboardDTO usuarioDto = new UsuarioDashboardDTO();


                usuarioDto.setId(usuario.getId());
                usuarioDto.setNombre(usuario.getNombre());
                usuarioDto.setApellido(usuario.getApellido());
                usuarioDto.setEmail(usuario.getEmail());
                usuarioDto.setEdificio(usuario.getEdificio());


        log.info("en la busqueda para el usuario del dashboard se armo este objeto: " + usuarioDto.toString());

        return usuarioDto;
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
