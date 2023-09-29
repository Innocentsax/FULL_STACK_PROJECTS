package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.PickupCenterResponse;
import com.decagon.OakLandv1be.entities.PickupCenter;
import com.decagon.OakLandv1be.entities.State;
import com.decagon.OakLandv1be.repositries.PickupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PickupServiceImplTest {

    @Mock
    PickupRepository pickupRepository;

    @InjectMocks
    private PickupServiceImpl pickupCenterService;

    List<PickupCenter> pickupCenters;
    PickupCenter pickupCenter1;
    PickupCenter pickupCenter2;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        pickupCenters = new ArrayList<>();

        pickupCenter1 = new PickupCenter();
        pickupCenter1.setName("Roban Stores");
        pickupCenter1.setEmail("roban@mail.com");
        pickupCenter1.setAddress("12 Roban St");
        pickupCenter1.setState(State.builder().name("LAGOS").build());

        pickupCenter2 = new PickupCenter();
        pickupCenter2.setName("Zain Pharmacy");
        pickupCenter2.setEmail("zain@mail.com");
        pickupCenter2.setAddress("3 Zain St");
        pickupCenter1.setState(State.builder().name("EDO").build());

        pickupCenters.add(pickupCenter1);
        pickupCenters.add(pickupCenter2);



    }

    @Test
    public void testGetCenterByStateName() {

        when(pickupRepository.findAll()).thenReturn(pickupCenters);

        List<PickupCenterResponse> result = pickupCenterService.getCenterByStateName("lagos");

        assertEquals(1, result.size());
        assertEquals("LAGOS", result.get(0).getState());
    }

    @Test
    public void testGetCenterByEmail() {
        when(pickupRepository.findByEmail("roban@mail.com")).thenReturn(Optional.of(pickupCenter1));

        PickupCenterResponse response = pickupCenterService.getCenterByEmail("roban@mail.com");

        assertEquals("Roban Stores", response.getName());
        assertEquals("12 Roban St", response.getLocation());
    }
}
