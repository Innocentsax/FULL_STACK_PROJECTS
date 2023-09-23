package com.decagon.fitnessoapp.repository;

import com.decagon.fitnessoapp.model.user.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
//    Optional<Contact> findContactByFullName(String fullName);
}
