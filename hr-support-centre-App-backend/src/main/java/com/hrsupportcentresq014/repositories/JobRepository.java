package com.hrsupportcentresq014.repositories;


import com.hrsupportcentresq014.entities.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends MongoRepository<Job, String> {
    @Query("{ 'title' : ?0 }")
    Job findByTitle(String title);
}
