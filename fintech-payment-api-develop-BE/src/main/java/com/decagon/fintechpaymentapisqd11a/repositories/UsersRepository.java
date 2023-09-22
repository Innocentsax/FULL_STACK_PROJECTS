package com.decagon.fintechpaymentapisqd11a.repositories;


import com.decagon.fintechpaymentapisqd11a.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UsersRepository extends JpaRepository<Users, java.lang.Long> {

    Optional<Users> findByEmail(String email);
    Optional<Users> findUsersByEmail (String email);

}
