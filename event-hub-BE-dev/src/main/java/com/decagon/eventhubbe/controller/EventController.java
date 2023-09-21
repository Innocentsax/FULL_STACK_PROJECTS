package com.decagon.eventhubbe.controller;

import com.decagon.eventhubbe.dto.request.EventRequest;
import com.decagon.eventhubbe.dto.request.EventUpdateRequest;
import com.decagon.eventhubbe.dto.response.APIResponse;
import com.decagon.eventhubbe.dto.response.EventResponse;
import com.decagon.eventhubbe.service.EventService;
import com.decagon.eventhubbe.utils.PageConstant;
import com.decagon.eventhubbe.utils.PageUtils;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping(value = "/create")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<EventResponse>> createEvent(@RequestParam("eventRequest") String request,
                                                                  @RequestParam("file") MultipartFile file) {
        EventRequest eventRequest = new Gson().fromJson(request, EventRequest.class);
        System.out.println(eventRequest.getTickets());
        APIResponse<EventResponse> response = new APIResponse<>(eventService.create(eventRequest, file));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping("/activate-event/{eventId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<EventResponse>> activateEvent(@PathVariable String eventId){
        APIResponse<EventResponse> apiResponse = new APIResponse<>(eventService.activateEvent(eventId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/view-event/")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<PageUtils>> findAllEvents(
            @RequestParam(value = "pageNo", defaultValue = PageConstant.pageNo) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.pageSize) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstant.sortBy) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PageConstant.sortDir) String sortDir) {

        APIResponse<PageUtils> apiResponse = new APIResponse<>(eventService.publishEvent(pageNo, pageSize, sortBy, sortDir));
        return ResponseEntity.ok().body(apiResponse);
    }
    @GetMapping("/view-event-by-user/")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<PageUtils>> findAllEventsByUser(
            @RequestParam(value = "pageNo", defaultValue = PageConstant.pageNo) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.pageSize) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstant.sortBy) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PageConstant.sortDir) String sortDir) {

        APIResponse<PageUtils> apiResponse = new APIResponse<>(eventService.publishEventByUser(pageNo, pageSize, sortBy, sortDir));
        return ResponseEntity.ok().body(apiResponse);
    }
    @GetMapping("/view-event-by-category/")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<PageUtils>> findAllEventsByCategory(
            @RequestParam(value = "pageNo", defaultValue = PageConstant.pageNo) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.pageSize) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstant.sortBy) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PageConstant.sortDir) String sortDir,
            @RequestParam("category") String category) {
        APIResponse<PageUtils> apiResponse = new APIResponse<>(eventService.publishEventByCategory(pageNo, pageSize, sortBy, sortDir,category));
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/search-event")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<PageUtils>> findAllEventsByKeyword(
            @RequestParam(value = "pageNo", defaultValue = PageConstant.pageNo) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.pageSize) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstant.sortBy) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PageConstant.sortDir) String sortDir,
            @RequestParam(value = "location", defaultValue = PageConstant.location) String location,
            @RequestParam("keyword") String keyword) {

        APIResponse<PageUtils> apiResponse = new APIResponse<>(eventService.searchEventsByKeyword(
                pageNo, pageSize, sortBy, sortDir, keyword, location));
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/view-event/{eventId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<EventResponse>> getEventById(
            @PathVariable String eventId){
        APIResponse<EventResponse> apiResponse = new APIResponse<>(eventService.getEventById(eventId));
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{eventId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<EventResponse>> updateEvent(
            @PathVariable String eventId,
            @RequestBody EventUpdateRequest updateEvent,
            MultipartFile file
    ){
        APIResponse<EventResponse> apiResponse = new APIResponse<>(eventService.updateEvent(eventId, updateEvent, file));
        return new ResponseEntity<>(
                apiResponse, HttpStatus.OK
        );
    }

    // Implementing the deletion of Event ----->
    @DeleteMapping("/{eventId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<String>> deleteEvent(
            @PathVariable String eventId) {
        APIResponse<String> apiResponse = new APIResponse<>(eventService.deleteEvent(eventId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
