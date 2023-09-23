package com.example.hive.dto.response;

import com.example.hive.entity.Address;
import com.example.hive.enums.Role;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationResponseDto {

    private String fullName;

    private String email;

    private String phoneNumber;

    private Address address;

    private Boolean isVerified;

    private Role role;

}
