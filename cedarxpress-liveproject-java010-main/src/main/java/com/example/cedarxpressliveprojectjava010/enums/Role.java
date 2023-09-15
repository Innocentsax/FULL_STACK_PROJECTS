package com.example.cedarxpressliveprojectjava010.enums;

public enum Role {
    ANONYMOUS("Anonymous"),
    ROLE_CUSTOMER("ROLE_CUSTOMER"),
    DISPATCH_RIDER("Dispatch Rider"),
    ADMIN("Administrator");

    private final String role;

    Role(String role) {
        this.role = role;
    }
}
