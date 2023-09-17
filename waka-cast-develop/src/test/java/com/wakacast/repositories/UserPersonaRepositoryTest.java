package com.wakacast.repositories;

import com.wakacast.models.UserPersona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class UserPersonaRepositoryTest {
    private final UserPersonaRepository userPersonaRepository;
    private UserPersona userPersona;

    @Autowired
    public UserPersonaRepositoryTest(UserPersonaRepository userPersonaRepository) {
        this.userPersonaRepository = userPersonaRepository;
    }

    @BeforeEach
    void setUp() {
        userPersona = new UserPersona();
        userPersona.setPersona("TALENT");
    }

    @Test
    void saveUserPersona() {
        UserPersona savedPersona = userPersonaRepository.save(userPersona);
        assertNotNull(savedPersona);
        assertEquals(savedPersona.getPersona(), userPersona.getPersona());
        assertEquals(savedPersona, userPersona);
    }

    @Test
    void findUserPersonaByPersona() {
        userPersonaRepository.save(userPersona);
        UserPersona returnedPersona = userPersonaRepository.findUserPersonaByPersona("TALENT").get();
        assertNotNull(returnedPersona);
        assertEquals(returnedPersona.getPersona(), userPersona.getPersona());
        assertEquals(returnedPersona, userPersona);
    }
}