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
import java.time.ZoneId;
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

        // Obtener la zona horaria deseada
        ZoneId zoneId = ZoneId.of("GMT-3");

        // Obtener la fecha y hora actual en la zona horaria deseada
        LocalDateTime currentDateTime = LocalDateTime.now(zoneId);

        // Formatear la fecha y hora actual con el patrón personalizado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd,MM,uuuu HH,mm");
        String formattedDateTime = currentDateTime.format(formatter);

        // Analizar la fecha y hora formateada en un LocalDateTime
        LocalDateTime parsedDateTime = LocalDateTime.parse(formattedDateTime, formatter);

        // Establecer el último acceso
        usuario.setUltimoAcceso(parsedDateTime);
        System.out.println(parsedDateTime);
        // Guardar el usuario actualizado en el repositorio
        usuarioRepository.save(usuario);

        // Generar el token JWT
        UserDetails userDetails = usuario;
        String token = jwtService.getToken(userDetails);

        // Construir y devolver la respuesta de autenticación
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
