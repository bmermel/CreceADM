package com.crece.crece.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Roles {
    ADMIN(
            Set.of(
                    Permisos.ADMIN_READ, Permisos.ADMIN_UPDATE, Permisos.ADMIN_CREATE, Permisos.ADMIN_DELETE,Permisos.ADMIN_PATCH,Permisos.USER_UPDATE,Permisos.USER_PATCH,Permisos.USER_DELETE,Permisos.USER_CREATE,Permisos.USER_READ
            )
    ),
    USER(            Set.of(
            Permisos.USER_UPDATE,Permisos.USER_PATCH,Permisos.USER_DELETE,Permisos.USER_CREATE,Permisos.USER_READ
    ))
    ;

    @Getter
    private final Set<Permisos> permisosSet;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermisosSet()
                .stream()
                .map(permisos -> new SimpleGrantedAuthority(permisos.getPermiso()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }

}
