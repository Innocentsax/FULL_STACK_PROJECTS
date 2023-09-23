package com.decagon.fitnessoapp.model.user;

import com.google.common.collect.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

import static com.decagon.fitnessoapp.model.user.Permission.*;

public enum ROLE_DETAIL {

    ANONYMOUS(Lists.newArrayList()),
    PREMIUM(Lists.newArrayList(BLOG_READ, TRAINING_READ, USER_READ, PRODUCT_READ)),
    ADMIN(Lists.newArrayList(USER_WRITE, USER_READ, AUTHOR_READ, AUTHOR_WRITE, PRODUCT_READ, PRODUCT_WRITE,
            BLOG_READ, BLOG_WRITE, TRAINING_WRITE, TRAINING_READ, USERS_READ, USERS_WRITE)),
    TRAINER(Lists.newArrayList(TRAINING_WRITE, TRAINING_READ));

    private final List<Permission> permissions;

    ROLE_DETAIL(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getGrantedAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority((permission.getPermission())))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
