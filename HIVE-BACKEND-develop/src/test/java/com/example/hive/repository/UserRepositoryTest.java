package com.example.hive.repository;

import com.example.hive.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {
    @Mock
    UserRepository userRepository;

    @Test
    void findAllUsers(){
        List<User> result = userRepository.findAll();
        verify(userRepository).findAll();
        assertEquals(0, result.size());
    }

}