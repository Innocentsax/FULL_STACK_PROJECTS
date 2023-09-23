package com.example.hive.dto.request;

import com.example.hive.entity.Address;
import com.example.hive.entity.Task;
import com.example.hive.enums.Role;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String fullName;
    @Email
    private String email;
    private String phoneNumber;
    private String validId;
    private Address address;
    private String password;
    private Boolean isVerified;
    private Role role;
    private List<Task> tasks;
}
