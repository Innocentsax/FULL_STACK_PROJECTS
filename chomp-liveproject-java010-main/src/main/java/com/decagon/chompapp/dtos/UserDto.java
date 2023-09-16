package com.decagon.chompapp.dtos;

import com.decagon.chompapp.enums.Gender;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
  private String firstName;
  private String lastName;
  private String username;
  private String gender;
}
