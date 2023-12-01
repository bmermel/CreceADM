package com.crece.crece.model;


import com.fasterxml.jackson.annotation.*;
import lombok.*;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    @JsonProperty
    private String password;

    @JoinColumn(name = "tipoUsuario_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private TipoUsuario tipoUsuario;

    @JoinColumn(name = "rolUsuario_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private RolUsuario rolUsuario;

    @JoinColumn(name = "edificio_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Edificio edificio;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((rolUsuario.getRol().name())));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
