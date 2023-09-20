package com.easyLend.userservice.domain.entity;

import com.easyLend.userservice.domain.constant.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser implements UserDetails  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "registration_status")
    private Boolean registrationStatus;
    @Column(name = "registration_stage")
    private Integer registrationStage;
    @Column(name = "phone_Number")
    private String phoneNumber;
    @Column(name = "user_Type")
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(name = "created_At")
    private LocalDateTime createdAt;
    @Column(name = "updated_At")
    private LocalDateTime updatedAt;
    @Column(name = "profile")
    private String image;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Borrower borrower;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Lender lender;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userType.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }
    public AppUser(String email,String password){
        this.email=email;
        this.password=password;

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
