package com.usermanagement.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {

    MEMBER,
    ADMIN;

    public String getAuthority() {
        return name();
    }

}
