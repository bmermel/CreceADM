package com.crece.crece.auth;

import com.crece.crece.jwt.JwtService;
import com.crece.crece.model.Usuario;
import com.crece.crece.model.dto.UsuarioDTO;
import com.crece.crece.repository.IUsuarioRepository;
import com.crece.crece.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        UserDetails usuario = usuarioRepository.findByEmail(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(usuario);

        return AuthResponse.builder()
                .token(token)
                .build();


    }

    public AuthResponse register(RegisterRequest request) {
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .nombre(request.nombre)
                .apellido(request.apellido)
                .email(request.email)
                .password(encoder.encode(request.password))
                .idEdificio(request.idEdificio)
                .idRolUsuario(request.idRolUsuario)
                .idTipoUsuario(request.idTipoUsuario)
                .build();

        usuarioService.guardarUsuario(usuarioDTO);

        return AuthResponse.builder()
                .token(jwtService.getToken(mapper.convertValue(usuarioDTO, Usuario.class)))
                .build();
    }

}
