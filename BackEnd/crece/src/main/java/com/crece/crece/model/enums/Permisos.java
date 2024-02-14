package com.crece.crece.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permisos {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_PATCH("admin:patch"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_PUT("admin:put"),

    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_PATCH("user:patch"),
    USER_DELETE("user:delete");

    @Getter
    private final String permiso;
}
