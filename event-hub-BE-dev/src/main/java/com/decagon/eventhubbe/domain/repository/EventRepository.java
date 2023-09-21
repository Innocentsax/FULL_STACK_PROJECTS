package com.decagon.eventhubbe.domain.repository;


import com.decagon.eventhubbe.ENUM.EventCategory;
import com.decagon.eventhubbe.domain.entities.AppUser;
import com.decagon.eventhubbe.domain.entities.Event;
import com.decagon.eventhubbe.dto.response.EventResponse;
import jdk.jfr.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {


    // Delete event by title
    void deleteByTitle(String title);

    Page<Event> findAllByCategory(EventCategory category, PageRequest of);

    Page<Event> findAllByAppUser(PageRequest of, AppUser appUser);

}
