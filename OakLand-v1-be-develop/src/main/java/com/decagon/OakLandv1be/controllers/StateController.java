package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.CategoryDto;
import com.decagon.OakLandv1be.dto.StateRequest;
import com.decagon.OakLandv1be.dto.StateResponse;
import com.decagon.OakLandv1be.entities.State;
import com.decagon.OakLandv1be.services.StatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/state")
@RequiredArgsConstructor

public class StateController {

    private final StatesService statesService;

    @PostMapping("/admin/create_state")
    public ResponseEntity<String> newState(@RequestBody StateRequest stateRequest){
        return new ResponseEntity<>(statesService.createState(stateRequest),HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/delete_state/{id}")
    @ResponseStatus(HttpStatus.OK)
    public  void deleteState(@PathVariable Long id){
        statesService.deleteState(id);
    }

    @GetMapping("/view_state/{nameOfState}")
    @ResponseStatus(HttpStatus.OK)
    public  void viewState(@PathVariable String nameOfState) { statesService.viewStateByName(nameOfState);
    }

    @GetMapping("/view-all")
    public ResponseEntity<List<StateResponse>> viewAllStates() {
        return new ResponseEntity<>(statesService.viewAllStates(), HttpStatus.OK);
    }

    @GetMapping("/view-states")
    public ResponseEntity<List<State>> getAllStates() {
        return statesService.getAllStates();
    }

}
