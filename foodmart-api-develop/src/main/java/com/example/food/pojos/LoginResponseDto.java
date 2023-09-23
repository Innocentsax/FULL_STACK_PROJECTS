package com.example.food.pojos;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String token;
    private String firstname;
    private String lastName;
    private String email;
}
