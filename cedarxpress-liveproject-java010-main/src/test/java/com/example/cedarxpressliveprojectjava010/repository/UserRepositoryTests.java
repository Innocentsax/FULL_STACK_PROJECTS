package com.example.cedarxpressliveprojectjava010.repository;

import com.example.cedarxpressliveprojectjava010.dto.RegistrationDto;
import com.example.cedarxpressliveprojectjava010.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenUserObject_whenCreate_thenCreateUser(){

       RegistrationDto userDto = new RegistrationDto();
       userDto.setFirstName("Sinebi");
       userDto.setLastName("Innazo");
       userDto.setEmail("sinebi.innazo@yahoo.com");
       userDto.setPassword("12345");
       userDto.setConfirmPassword("12345");

       User user = new User();
       user.setFirstName(userDto.getFirstName());
       user.setLastName(userDto.getLastName());
       user.setEmail(userDto.getEmail());
       user.setPassword(userDto.getPassword());

       User savedUser = userRepository.save(user);
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);

    }
}
