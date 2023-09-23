package com.decagon.fitnessoapp.repository;

import com.decagon.fitnessoapp.model.blog.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {


}
