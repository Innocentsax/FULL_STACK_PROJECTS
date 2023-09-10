package com.hrsupportcentresq014.repositories;

import com.hrsupportcentresq014.entities.Applicant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicantRepository   extends MongoRepository<Applicant, String> {
}
