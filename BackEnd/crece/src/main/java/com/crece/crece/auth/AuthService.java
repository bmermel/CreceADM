package com.crece.crece.auth;

import com.crece.crece.jwt.JwtService;
import com.crece.crece.model.Edificio;
import com.crece.crece.model.RolUsuario;
import com.crece.crece.model.TipoUsuario;
import com.crece.crece.model.Usuario;
import com.crece.crece.model.dto.EdificioDTO;
import com.crece.crece.model.dto.RolUsuarioDto;
import com.crece.crece.model.dto.TipoUsuarioDto;
import com.crece.crece.model.dto.UsuarioDTO;
import com.crece.crece.repository.IUsuarioRepository;
import com.crece.crece.service.EdificioService;
import com.crece.crece.service.RolUsuarioService;
import com.crece.crece.service.TipoUsuarioService;
import com.crece.crece.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final ObjectMapper mapper;
    @Autowired
    private final IUsuarioRepository usuarioRepository;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UsuarioService usuarioService;
    @Autowired
    private final PasswordEncoder encoder;
    @Autowired
    private final AuthenticationManager authenticationManager;


    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Usuario usuario = (Usuario) usuarioRepository.findByEmail(request.getUsername()).orElseThrow();

        // Establecer el último acceso y formatearlo a dia mes año horario sin segundos
        usuario.setUltimoAcceso(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd,MM,yyyy HH,mm"))));

        usuarioRepository.save(usuario);

        UserDetails userDetails = usuario;
        String token = jwtService.getToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .build();
    }


    public AuthResponse register(RegisterRequest request) {
        String encryptedPassword = encoder.encode(request.password);

        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .nombre(request.nombre)
                .apellido(request.apellido)
                .email(request.email)
                .password(encryptedPassword)
                .idEdificio(request.idEdificio)
                .idRolUsuario(request.idRolUsuario)
                .idTipoUsuario(request.idTipoUsuario)
                .telefono(request.telefono)
                .unidadFuncional(request.unidadFuncional)
                .habilitado(false)
                .build();
        usuarioService.guardarUsuario(usuarioDTO);
        UserDetails user = mapper.convertValue(usuarioDTO, Usuario.class);
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

}
