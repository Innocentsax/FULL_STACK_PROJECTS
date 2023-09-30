package com.decagon.dev.paybuddy;

import javax.transaction.Transactional;

import com.decagon.dev.paybuddy.enums.Authorities;
import com.decagon.dev.paybuddy.enums.Roles;
import com.decagon.dev.paybuddy.models.Authority;
import com.decagon.dev.paybuddy.models.Role;
import com.decagon.dev.paybuddy.models.User;
import com.decagon.dev.paybuddy.repositories.AuthorityRepository;
import com.decagon.dev.paybuddy.repositories.RoleRepository;
import com.decagon.dev.paybuddy.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Component
@AllArgsConstructor
public class InitialUserSetUp {
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;


    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From Application ready event....");
        Authority readAuthority = createAuthority(Authorities.READ_AUTHORITY.name());
        Authority writeAuthority = createAuthority(Authorities.WRITE_AUTHORITY.name());
        Authority deleteAuthority = createAuthority(Authorities.DELETE_AUTHORITY.name());
        createRole(Roles.ROLE_USER.name(), Collections.singletonList(readAuthority));
        createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority));
        Role roleSuperAdmin = createRole(Roles.ROLE_SUPER_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
        if (roleSuperAdmin == null) return;

        User superAdminUser = new User();
        superAdminUser.setFirstName("Super");
        superAdminUser.setLastName("Super");
        superAdminUser.setEmail("super@admin.com");
        superAdminUser.setPassword(bCryptPasswordEncoder.encode("123456789"));
        superAdminUser.setRoles(List.of(roleSuperAdmin));

        User storedSuperUser = userRepository.findByEmail("super@admin.com").orElse(null);
        if(storedSuperUser == null) {
            userRepository.save(superAdminUser);
        }
    }
    @Transactional
    Authority createAuthority(String authorityName) {
        Authority authority = authorityRepository.findByName(authorityName);
        if (authority == null) {
            authority = new Authority(authorityName);
            authorityRepository.save(authority);
        }
        return authority;
    }
    @Transactional
    Role createRole(String roleName, List<Authority> authorities) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role(roleName);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }
}
