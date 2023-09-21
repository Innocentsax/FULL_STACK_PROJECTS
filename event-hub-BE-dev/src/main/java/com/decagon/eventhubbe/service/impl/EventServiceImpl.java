package com.decagon.eventhubbe.service.impl;

import com.decagon.eventhubbe.ENUM.EventCategory;
import com.decagon.eventhubbe.config.CloudinaryConfig;
import com.decagon.eventhubbe.domain.entities.AppUser;
import com.decagon.eventhubbe.domain.entities.Event;
import com.decagon.eventhubbe.domain.entities.EventTicket;
import com.decagon.eventhubbe.domain.entities.geoLocation.GeoLocation;
import com.decagon.eventhubbe.domain.entities.geoLocation.GeoResponse;
import com.decagon.eventhubbe.domain.repository.AccountRepository;
import com.decagon.eventhubbe.domain.repository.EventRepository;
import com.decagon.eventhubbe.domain.repository.EventTicketRepository;
import com.decagon.eventhubbe.dto.request.EventRequest;
import com.decagon.eventhubbe.dto.request.EventTicketRequest;
import com.decagon.eventhubbe.dto.request.EventUpdateRequest;
import com.decagon.eventhubbe.dto.response.EventResponse;
import com.decagon.eventhubbe.exception.EventNotFoundException;
import com.decagon.eventhubbe.exception.UnauthorizedException;
import com.decagon.eventhubbe.service.EventService;
import com.decagon.eventhubbe.utils.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.decagon.eventhubbe.utils.LocationUtils.*;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventTicketRepository eventTicketRepository;
    private final AppUserServiceImpl appUserService;
    private final MongoTemplate mongoTemplate;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    public EventResponse create(EventRequest request,MultipartFile file) {
        AppUser user = appUserService.getUserByEmail(UserUtils.getUserEmailFromContext());

        GeoResponse geoDetails = getGeoDetails(request);
        String actualLocation = extractActualLocation(geoDetails);
        GeoLocation coordinates = extractGeoLocation(geoDetails);

        if(!accountRepository.existsByAppUser(user)){
            throw new RuntimeException("USER NEED TO UPDATE HIS PROFILE");
        }
        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .caption(request.getCaption())
                .appUser(user)
                .category(EventCategory.fromDisplayName(request.getCategory()))
                .location(actualLocation)
                .coordinates(coordinates)
                .organizer(request.getOrganizer())
                .isDeleted(false)
                .isExpired(false)
                .isActive(false)
                .startDate(request.getStartDate())
                .startTime(request.getStartTime())
                .endDate(request.getEndDate())
                .endTime(request.getEndTime())
                .createdAt(DateUtils.saveDate(LocalDateTime.now()))
                .build();
        Event savedEvent = eventRepository.save(event);
        CloudinaryConfig cloudinaryConfig = new CloudinaryConfig();
        savedEvent.setBannerUrl(cloudinaryConfig.imageLink(file,savedEvent.getId()));
        List<EventTicketRequest> ticketRequest = request.getTickets();
        ticketRequest.forEach(ticket -> eventTicketRepository.save(
                EventTicket.builder()
                        .event(savedEvent)
                        .description(ticket.getDescription())
                        .quantity(ticket.getQuantity())
                        .ticketClass(ticket.getTicketClass())
                        .ticketPrice(ticket.getTicketPrice())
                        .build()
        ));
        savedEvent.setEventTickets(eventTicketRepository.findAllByEvent(savedEvent));
        return modelMapper.map(eventRepository.save(savedEvent), EventResponse.class);
    }
    @Override
    public EventResponse activateEvent(String id){
        Event eventToActivate = eventRepository
                .findById(id)
                .orElseThrow(() ->
                        new EventNotFoundException(id));
        System.out.println(eventToActivate.getAppUser().getEmail());
        AppUser appUser = appUserService.getUserByEmail(UserUtils.getUserEmailFromContext());
        System.out.println(appUser.getEmail());
        if(!eventToActivate.getAppUser().getEmail().equals(appUser.getEmail())){
            throw new UnauthorizedException("Unauthorized! Event created by another user");
        }
        eventToActivate.setActive(true);
        return modelMapper.map(eventRepository.save(eventToActivate), EventResponse.class);
    }

    // Implementing the deletion of Event ----->
    @CacheEvict(cacheNames = "events",allEntries = true)
    @Override
    public String deleteEvent(String id) {

        Event eventToDelete = eventRepository
                .findById(id)
                .orElseThrow(() ->
                        new EventNotFoundException(id));
        AppUser appUser = appUserService.getUserByEmail(UserUtils.getUserEmailFromContext());
        if(!eventToDelete.getAppUser().equals(appUser)){
            throw new UnauthorizedException("Unauthorized! Event created by another user");
        }
        eventToDelete.setDeleted(true);
        eventRepository.save(eventToDelete);
        return "Event with title : "+eventToDelete.getTitle()+" deleted successfully";
    }
//    @Cacheable(cacheNames = "events",key = "{#pageNo,#pageSize,#sortBy,#sortDir,#keyword,#location}")
    @Override
    public PageUtils searchEventsByKeyword(Integer pageNo, Integer pageSize, String sortBy, String sortDir, String keyword, String location) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize,sort);

        Criteria locationCriteria = Criteria.where("location").regex("(?i).*" + location + ".*");
        Criteria textSearchCriteria = new Criteria().orOperator(
                Criteria.where("title").regex("(?i).*" + keyword + ".*"),
                Criteria.where("description").regex("(?i).*" + keyword + ".*"),
                Criteria.where("caption").regex("(?i).*" + keyword + ".*")
        );
        Criteria combinedCriteria = new Criteria().andOperator(locationCriteria, textSearchCriteria);
        Query query = new Query().addCriteria(combinedCriteria).with(sort);
        Pair<List<Event>, Long> result = executeQuery(query, pageable);
        Page<Event> eventPage = PageableExecutionUtils.getPage(result.getFirst(), pageable, result::getSecond);
        List<Event> events = eventPage.getContent();
        if(events.isEmpty()){
            throw  new EventNotFoundException(" search word is not found");
        }
        List<EventResponse> eventResponseList = events.stream()
                .filter(event -> {
                    return event.isActive() && EventUtils.eventValidation(event);
                })
                .map(event -> modelMapper.map(event, EventResponse.class))
                .toList();
        return PageUtils.builder()
                .content(eventResponseList)
                .pageNo(eventPage.getNumber())
                .pageSize(eventPage.getSize())
                .totalElements(eventPage.getTotalElements())
                .totalPage(eventPage.getTotalPages())
                .isLast(eventPage.isLast())
                .build();
    }
    private Pair<List<Event>, Long> executeQuery(Query query, Pageable pageable) {
        Assert.notNull(query, "TextQuery must not be null");
        Assert.notNull(pageable, "Pageable must not be null");

        query.with(pageable);

        List<Event> results = mongoTemplate.find(query, Event.class);
        long count = calculateCount(query);

        return Pair.of(results, count);
    }

    private long calculateCount(Query query) {
        return mongoTemplate.count(query, Event.class);
    }

//    @Cacheable(cacheNames = "events", key = "#id")
    @Override
    public EventResponse getEventById(String id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return modelMapper.map(event, EventResponse.class);

    }

//    @Cacheable(cacheNames = "events",key = "{#pageNo,#pageSize,#sortBy,#sortDir}")
    @Override
    public PageUtils publishEvent(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Event> eventPage = eventRepository.findAll(PageRequest.of(pageNo, pageSize, sort));
        List<Event> events = new ArrayList<>();

        eventPage.getContent().forEach(event -> {
            if (EventUtils.eventValidation(event) && event.isActive()) {
                events.add(event);
            }
        });
        List<EventResponse> eventResponses = events.stream().map(event -> modelMapper.map(event, EventResponse.class))
                .collect(Collectors.toList());
        return PageUtils.builder()
                        .content(eventResponses)
                        .pageNo(eventPage.getNumber())
                        .pageSize(eventPage.getSize())
                .totalElements(eventPage.getTotalElements())
                .totalPage(eventPage.getTotalPages())
                .isLast(eventPage.isLast())
                .build();

    }
//    @Cacheable(cacheNames = "events",key = "{#pageNo,#pageSize,#sortBy,#sortDir}")
    @Override
    public PageUtils publishEventByUser(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        AppUser appUser = appUserService.getUserByEmail(UserUtils.getUserEmailFromContext());
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Event> eventPage = eventRepository.findAllByAppUser(PageRequest.of(pageNo, pageSize, sort), appUser);
        List<Event> events = new ArrayList<>();

        eventPage.getContent().forEach(event -> {
            if (!EventUtils.eventValidation(event)) {
                event.setExpired(true);
            }
            if(event.getAppUser().getEmail().equals(appUser.getEmail())){
                events.add(event);
            }


        });
        List<EventResponse> eventResponses = events.stream().map(event -> modelMapper.map(event, EventResponse.class))
                .collect(Collectors.toList());
        return PageUtils.builder()
                .content(eventResponses)
                .pageNo(eventPage.getNumber())
                .pageSize(eventPage.getSize())
                .totalElements(eventPage.getTotalElements())
                .totalPage(eventPage.getTotalPages())
                .isLast(eventPage.isLast())
                .build();

    }

//    @Cacheable(cacheNames = "events",key = "{#pageNo,#pageSize,#sortBy,#sortDir,#category}")
    @Override
    public PageUtils publishEventByCategory(Integer pageNo, Integer pageSize, String sortBy, String sortDir, String category) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Event> eventPage = eventRepository.findAllByCategory(EventCategory.fromDisplayName(category),PageRequest.of(pageNo, pageSize, sort));
        List<Event> events = new ArrayList<>();
        eventPage.getContent().forEach(event -> {
            if (EventUtils.eventValidation(event) && event.isActive()) {
                events.add(event);
            }
        });
        List<EventResponse> eventResponses = events.stream().map(event -> modelMapper.map(event, EventResponse.class))
                .collect(Collectors.toList());
        return PageUtils.builder()
                .content(eventResponses)
                .pageNo(eventPage.getNumber())
                .pageSize(eventPage.getSize())
                .totalElements(eventPage.getTotalElements())
                .totalPage(eventPage.getTotalPages())
                .isLast(eventPage.isLast())
                .build();

    }
    @CachePut(cacheNames = "events", key = "#id")
    @Override
    public EventResponse updateEvent(String id, EventUpdateRequest updateEvent, MultipartFile file) {
        AppUser appUser = appUserService.getUserByEmail(UserUtils.getUserEmailFromContext());
        GeoResponse geoDetails = getGeoDetails(updateEvent);
        String actualLocation = extractActualLocation(geoDetails);
        GeoLocation coordinates = extractGeoLocation(geoDetails);

        Event eventToUpdate = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        if(!eventToUpdate.getAppUser().equals(appUser)){
            throw new UnauthorizedException("Event Created By Another User");
        }
        evictEventCache();
        eventToUpdate.setTitle(updateEvent.getTitle());
        eventToUpdate.setCaption(updateEvent.getCaption());
        eventToUpdate.setDescription(updateEvent.getDescription());
        eventToUpdate.setOrganizer(updateEvent.getOrganizer());
        eventToUpdate.setCategory(EventCategory.valueOf(updateEvent.getCategory()));
        eventToUpdate.setLocation(actualLocation);
        eventToUpdate.setCoordinates(coordinates);
        eventToUpdate.setStartDate(updateEvent.getStartDate());
        eventToUpdate.setEndDate(updateEvent.getEndDate());
        eventToUpdate.setStartTime(updateEvent.getStartTime());
        eventToUpdate.setEndTime(updateEvent.getEndTime());
        if(!file.isEmpty()){
            CloudinaryConfig cloudinaryConfig = new CloudinaryConfig();
            eventToUpdate.setBannerUrl(cloudinaryConfig.imageLink(file,eventToUpdate.getId()));
        }
        return modelMapper.map(eventRepository.save(eventToUpdate), EventResponse.class);
    }

    @CacheEvict(cacheNames = "events",allEntries = true)
    public void evictEventCache(){
    }
}


