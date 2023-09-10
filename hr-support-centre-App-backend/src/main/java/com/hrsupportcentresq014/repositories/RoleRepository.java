package com.hrsupportcentresq014.repositories;
import com.hrsupportcentresq014.entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findRoleById(String Id);
}
