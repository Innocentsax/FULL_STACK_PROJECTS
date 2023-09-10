package com.hrsupportcentresq014.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
@Builder
public class Role {
    @Id
    private String id;
    private String name;
    private List<Permission> permissions;

    public List<Permission> getPermissions(){
        return permissions;
    }
    public void setPermissions(List<Permission> permissions){
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
            List<SimpleGrantedAuthority> authorities = getPermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                    .collect(Collectors.toList());
            authorities.add(new SimpleGrantedAuthority("ROLE_" + this.getName()));
        return authorities;
    }
}
