package com.decagon.eventhubbe.service;

import com.decagon.eventhubbe.dto.request.EventRequest;
import com.decagon.eventhubbe.dto.request.EventUpdateRequest;
import com.decagon.eventhubbe.dto.response.EventResponse;
import com.decagon.eventhubbe.utils.PageUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface EventService {

    EventResponse create(EventRequest request,MultipartFile file);

    EventResponse activateEvent(String id);

    //deletion of event ------>
    String deleteEvent(String id);

    PageUtils searchEventsByKeyword(Integer pageNo, Integer pageSize, String sortBy, String sortDir, String keyword, String location);

    EventResponse getEventById(String id);

    PageUtils publishEvent(Integer pageNo, Integer pageSize, String sortBy, String sortDir);

//    @Cacheable(cacheNames = "events",key = "{#pageNo,#pageSize,#sortBy,#sortDir}")
    PageUtils publishEventByUser(Integer pageNo, Integer pageSize, String sortBy, String sortDir);

    PageUtils publishEventByCategory(Integer pageNo, Integer pageSize, String sortBy, String sortDir, String category);

    EventResponse updateEvent(String id, EventUpdateRequest updateEvent, MultipartFile file);
}
