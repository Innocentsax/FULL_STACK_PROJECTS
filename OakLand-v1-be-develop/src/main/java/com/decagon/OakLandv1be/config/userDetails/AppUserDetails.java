package com.decagon.OakLandv1be.config.userDetails;

import com.decagon.OakLandv1be.entities.Person;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
@Builder
@Getter
@Setter
public class AppUserDetails implements UserDetails {

    private Person person;

    @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {

        return person.getRole().getGrantedAuthorities();
        }

        @Override
        public String getPassword() {

        return person.getPassword();
        }

        @Override
        public String getUsername() {

        return person.getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {

        return true;
        }

        @Override
        public boolean isAccountNonLocked() {


        return person.isActive();
        }

        @Override
        public boolean isCredentialsNonExpired() {

        return true;
        }

        @Override
        public boolean isEnabled() {
        return person.getVerificationStatus();
        }
    }
