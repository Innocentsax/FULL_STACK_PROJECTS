package com.decagon.eventhubbe.controller;

import com.decagon.eventhubbe.dto.request.SaveTicketRequest;
import com.decagon.eventhubbe.dto.response.APIResponse;
import com.decagon.eventhubbe.dto.response.SaveTicketResponse;
import com.decagon.eventhubbe.dto.response.TicketsSalesResponse;
import com.decagon.eventhubbe.service.EventTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/ticket")
public class EventTicketController {
    private final EventTicketService eventTicketService;

    @GetMapping("/view-event-sales/{eventId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<List<TicketsSalesResponse>>> trackTicket
            (@PathVariable String eventId){
        APIResponse<List<TicketsSalesResponse>> apiResponse = new APIResponse<>
                (eventTicketService.trackTicketSales(eventId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/save-tickets")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<String>> saveTickets(@RequestBody SaveTicketRequest request){
        APIResponse<String> apiResponse = new APIResponse<>(eventTicketService.saveTicket(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get-saved-tickets")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<List<SaveTicketResponse>>> getBookedTicket(){
        APIResponse<List<SaveTicketResponse>> apiResponse = new APIResponse<>(eventTicketService.getSavedTickets());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
