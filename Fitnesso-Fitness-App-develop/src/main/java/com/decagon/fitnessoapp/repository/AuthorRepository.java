package com.decagon.fitnessoapp.repository;

import com.decagon.fitnessoapp.model.user.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
