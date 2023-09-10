package com.hrsupportcentresq014.repositories;

import com.hrsupportcentresq014.entities.Award;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface AwardRepository extends MongoRepository<Award, String> {
    // Add custom repository methods if needed
    List<Award> findByYear(Year year);
    Optional<Award> findAwardByTitleAndYear(String title, int year);
    Page<Award> findAwardByYear(int year, Pageable pageable);
}