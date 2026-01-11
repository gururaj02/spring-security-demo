package com.example.spring_security_demo.entity;

import java.util.Set;

public enum Role {
    ADMIN(Set.of(Permissions.DEMO_READ, Permissions.DEMO_WRITE, Permissions.DEMO_DELETE)),
    USER(Set.of(Permissions.DEMO_READ));

    private final Set<Permissions> permissions;

    Role(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }
}
