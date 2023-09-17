package com.wakacast.repositories;

import com.wakacast.dto.pages_criteria.TalentPage;
import com.wakacast.enums.Gender;
import com.wakacast.models.Role;
import com.wakacast.models.User;
import com.wakacast.models.UserPersona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("og@gmail.com");
        user.setGender(Gender.MALE);
        user.setPassword("12345678");
        user.setLoginToken("123456");
    }

    @Test
    void findUserByEmail() {
        userRepository.save(user);
        User returnedUser = userRepository.findUserByEmail("og@gmail.com").get();
        assertThat(returnedUser).isNotNull();
        assertThat(returnedUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void saveUser() {
        User savedUser = userRepository.save(user);
        assertThat(savedUser).isNotNull().isEqualTo(user);
    }

    @Test
    void findUserByLoginToken() {
        userRepository.save(user);
        User returnedUser = userRepository.findUserByLoginToken("123456").get();
        assertThat(returnedUser).isNotNull();
        assertThat(returnedUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void findTalentAll() {
        User user1 = new User();
        user1.setUserPersonas(new HashSet<>(Set.of(new UserPersona("TALENT"))));
        User user2 = new User();
        user2.setUserPersonas(new HashSet<>(Set.of(new UserPersona("Producer"))));
        User user3 = new User();
        user3.setUserPersonas(new HashSet<>(Set.of(new UserPersona("TALENT"))));
        User user4 = new User();
        user4.setUserPersonas(new HashSet<>(Set.of(new UserPersona("Crew"))));
        List<User> list = Arrays.asList(user1, user2, user3, user4);
        userRepository.saveAll(list);
        TalentPage talentPage = new TalentPage();
        Sort sort = Sort.by(talentPage.getSortDirection(), talentPage.getSortBy());
        Pageable pageable = PageRequest.of(talentPage.getPageNumber(), talentPage.getPageSize(), sort);
        Page<User> talents = userRepository.findTalentAll(pageable);
        assertThat(talents.getTotalElements()).isEqualTo(2);
    }

    @Test
    void findUserById() {
        user.setAccountVerified(true);
        user.setRoles(Set.of(new Role("TALENT")));
        user.setActive(true);
        User savedUser = userRepository.save(user);
        User returnedUser = userRepository.findById(savedUser.getId()).get();
        assertThat(returnedUser).isNotNull();
        assertThat(returnedUser.getEmail()).isEqualTo(user.getEmail());
    }
}
