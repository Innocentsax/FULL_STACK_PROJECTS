package com.wakacast.repositories;

import com.wakacast.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
class RoleRepositoryTest {
    private final RoleRepository roleRepository;

    private Role role;

    @Autowired
    public RoleRepositoryTest(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setRoleTitle("Voice-over Artiste");
    }

    @Test
    void saveRole() {
        Role savedRole = roleRepository.save(role);
        assertThat(savedRole).isNotNull().isEqualTo(role);
    }

    @Test
    void findRoleByRoleTitle() {
        roleRepository.save(role);
        Role returnedRole = roleRepository.findRoleByRoleTitle("Voice-over Artiste").get();
        assertThat(returnedRole).isNotNull().isEqualTo(role);
    }
}