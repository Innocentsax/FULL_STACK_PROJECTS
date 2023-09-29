package com.decagon.OakLandv1be.services;


import com.decagon.OakLandv1be.dto.StateRequest;
import com.decagon.OakLandv1be.dto.StateResponse;
import com.decagon.OakLandv1be.entities.State;
import org.springframework.http.ResponseEntity;

import java.util.List;

import java.util.List;


public interface StatesService {
    String createState(StateRequest stateRequest);

    void deleteState(Long id);

    StateResponse viewStateByName(String name);

    List<StateResponse> viewAllStates();

    ResponseEntity<List<State>> getAllStates();
}

