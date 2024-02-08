package com.crece.crece.config;

import com.crece.crece.jwt.JwtAuthenticationFilter;
import com.crece.crece.model.enums.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.crece.crece.model.enums.Permisos.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authRequest ->
                        //cambiar los permisos mas adelante
                        authRequest
                                //.requestMatchers("/**").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/novedades/**").hasAnyRole(Roles.ADMIN.name())
                                .requestMatchers(HttpMethod.POST,"/novedades/**").hasAnyAuthority(ADMIN_CREATE.name())
                                .requestMatchers(HttpMethod.GET,"/novedades/**").hasAnyAuthority(ADMIN_READ.name(),USER_READ.name())
                                .requestMatchers(HttpMethod.DELETE,"/novedades/**").hasAnyAuthority(ADMIN_DELETE.name())


                                //.requestMatchers("/file/fileSystem/upload/**").hasRole(Roles.ADMIN.name())




                        //.anyRequest().authenticated()
                )                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider((authProvider))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class )
                .build();

    }
}
