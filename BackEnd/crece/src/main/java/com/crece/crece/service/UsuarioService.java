package com.crece.crece.service;


import com.crece.crece.model.Edificio;
import com.crece.crece.model.RolUsuario;
import com.crece.crece.model.TipoUsuario;
import com.crece.crece.model.Usuario;
import com.crece.crece.model.dto.*;
import com.crece.crece.model.enums.Roles;
import com.crece.crece.model.enums.Tipos;
import com.crece.crece.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    private NovedadesRepository novedadesRepository;
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
            //usuario.setNombre(usuario.getNombre());
            //usuario.setApellido(usuario.getApellido());
            //usuario.setHabilitado(false);

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

    public UsuarioDTO leerUsuarioByMail(String email) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
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
                usuarioDto.setHabilitado(usuario.getHabilitado());
                usuarioDto.setTelefono(usuario.getTelefono());
                usuarioDto.setUnidadFuncional(usuario.getUnidadFuncional());
                usuarioDto.setRolUsuario(usuario.getRolUsuario());
                usuarioDto.setNovedad(novedadesRepository.findByEdificioId(usuario.getEdificio().getId()).orElse(null));




        log.info("en la busqueda para el usuario del dashboard se armo este objeto: " + usuarioDto.toString());

        return usuarioDto;
    }


    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
}


    @Cacheable(value = "usuariosCache")
    public List<GetUsuarioDTO> getUsuarios() {
        List<Usuario> usuarioList = usuarioRepository.findAll();
        List<GetUsuarioDTO> usuarioDTOList = new ArrayList<>();

        for (Usuario usuario : usuarioList) {
            GetUsuarioDTO user = new GetUsuarioDTO();

            user.setId(usuario.getId());
            user.setNombre(usuario.getNombre() + " "+ usuario.getApellido());
            //user.setApellido(usuario.getApellido());
            user.setEmail(usuario.getEmail());
            user.setHabilitado(usuario.getHabilitado());
            user.setUnidadFuncional(usuario.getUnidadFuncional());
            user.setTelefono(usuario.getTelefono());
            user.setUltimoAcceso(usuario.getUltimoAcceso());

            if (usuario.getEdificio() != null) {
                user.setEdificio(usuario.getEdificio().getNombre());
            }

            if (usuario.getRolUsuario() != null) {
                user.setRolUsuario(usuario.getRolUsuario().getRol().toString());
            }

            if (usuario.getTipoUsuario() != null) {
                user.setTipoUsuario(usuario.getTipoUsuario().getTipo().toString());
            }

            usuarioDTOList.add(user);
        }
        return usuarioDTOList;
    }
    public List<String> getEmailsPorEdificio(Long edificioId, String tipoUsuario) {
        tipoUsuario = tipoUsuario.toUpperCase(Locale.ROOT);
        List<Usuario> usuariosPorEdificio = usuarioRepository.findByEdificioId(edificioId);
        String finalTipoUsuario = tipoUsuario;
        List<String> emails = usuariosPorEdificio.stream()
                .filter(usuario -> esInquilino(usuario, finalTipoUsuario))
                .map(Usuario::getEmail)
                .collect(Collectors.toList());
        return emails;
    }
    public List<String> getEmailsPorEdificioSinTipo(Long edificioId) {
        List<Usuario> usuariosPorEdificio = usuarioRepository.findByEdificioId(edificioId);
        List<String> emails = usuariosPorEdificio.stream()
                .map(Usuario::getEmail)
                .collect(Collectors.toList());
        return emails;
    }
    public boolean esInquilino(Usuario usuario, String tipoUsuario) {
        // Verificar si el tipo de usuario coincide con el proporcionado
        return usuario.getTipoUsuario() != null && tipoUsuario.equals(usuario.getTipoUsuario().getTipo().toString());
    }

    public void cambiarEstadoUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Boolean habilitado = usuario.getHabilitado();

        if (habilitado == null) {
            usuario.setHabilitado(true);
        } else {
            usuario.setHabilitado(!habilitado);
        }

        usuarioRepository.save(usuario);
    }

    public void changePassword(String email, String newPassword) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setPassword(encoder.encode(newPassword)); // Usar el encoder para almacenar contraseñas seguras

            // Guardar los cambios en la base de datos
            usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuario no encontrado para el correo electrónico: " + email);
        }
    }

    public Roles obtenerRolPorEmail(String email) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            RolUsuario rolUsuario = usuario.getRolUsuario();

            if (rolUsuario != null) {
                return rolUsuario.getRol();
            } else {
                throw new RuntimeException("El usuario no tiene un rol asignado.");
            }
        } else {
            throw new RuntimeException("Usuario no encontrado para el correo electrónico enviado.");
        }
    }
}
