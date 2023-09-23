package com.decagon.fitnessoapp.repository;

import com.decagon.fitnessoapp.model.user.Person;
import com.decagon.fitnessoapp.model.user.ROLE_DETAIL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findPersonByPassword(String password);
    Optional<Person> findPersonByUserName(String username);
    Optional<Person> findPersonByUserNameAndPassword(String username,String password);

    Optional<Person> findByUserName(String userName);
    Optional<Person> findByEmail(String email);
    Optional<Person> findByResetPasswordToken(String token);

    Optional<List<Person>> findAllByRoleDetail(ROLE_DETAIL role);
}
