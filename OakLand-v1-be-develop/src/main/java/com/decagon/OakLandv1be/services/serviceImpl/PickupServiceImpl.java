package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.PickupCenterRequest;
import com.decagon.OakLandv1be.dto.PickupCenterResponse;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.PickupCenter;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.exceptions.*;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.PickupRepository;
import com.decagon.OakLandv1be.repositries.StateRepository;
import com.decagon.OakLandv1be.services.PickupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PickupServiceImpl implements PickupService {
    private final PickupRepository pickupRepository;
    private final PersonRepository personRepository;
    private final StateRepository stateRepository;

    @Override
    public PickupCenterResponse getCenterByEmail(String email) {
        PickupCenter pickup = pickupRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("Center not found"));
        return responseMapper(pickup);
    }

    @Override
    public List<PickupCenterResponse> getCenterByStateName(String name) {
        return pickupRepository.findAll().parallelStream()
                .filter(pickupCenter -> pickupCenter.getState().getName().equalsIgnoreCase(name))
                .map(this::responseMapper)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCenter(Long id) {
        if(!confirmAuthority()) throw new AuthorizationException("You are NOT AUTHORIZED to perform this operation");
        if(pickupRepository.existsById(id)) {pickupRepository.deleteById(id);}
        else throw new ResourceNotFoundException("Can't find the center in our record");
    }

    @Transactional
    @Override
    public String createCenter(PickupCenterRequest pickupCenterRequest) {
        if(!confirmAuthority()) throw new AuthorizationException("You are NOT AUTHORIZED to perform this operation");
        if(pickupRepository.existsByName(pickupCenterRequest.getName().toUpperCase())) {throw new AlreadyExistsException("Pickup center already exists");}

        pickupRepository.save(PickupCenter.builder()
                .name(pickupCenterRequest.getName())
                .state(stateRepository.findByName(pickupCenterRequest.getState().toUpperCase()).orElseThrow(
                        ()-> new ResourceNotFoundException("No state with the name {}",pickupCenterRequest.getState().toUpperCase())
                ))
                .email(pickupCenterRequest.getEmail())
                .address(pickupCenterRequest.getLocation())
                .phone(pickupCenterRequest.getPhone())
                .delivery(pickupCenterRequest.getDelivery())
                .build());
        return "Center created successfully";
    }

    @Override
    public Page<PickupCenterResponse> getAll(int page, int size) {
        page= Math.max(page, 0);
        size= Math.max(size, 10);
        return pickupRepository.findAll(PageRequest.of(page,size)).map(this::responseMapper);
    }

    @Override
    public String updatePickupCenter(Long pickupId, PickupCenterRequest pickupCenterRequest) {
        PickupCenter pickupCenter = pickupRepository.findById(pickupId)
                .orElseThrow(() -> new NotAvailableException("Pickup center does not exist!"));
        String name =  pickupCenterRequest.getState();
        String email = pickupCenterRequest.getEmail();
        String location = pickupCenterRequest.getLocation();
        String state = pickupCenterRequest.getState();
        String phone = pickupCenterRequest.getPhone();
        Double delivery = pickupCenterRequest.getDelivery();

        if(name != null)
            pickupCenter.setName(name);
        if(email != null)
            pickupCenter.setEmail(email);
        if(location != null)
            pickupCenter.setAddress(location);
        if(state != null)
            pickupCenter.setState(stateRepository.findByName(state)
                    .orElseThrow(() -> new NotAvailableException("State is not available")));
        if(phone != null)
            pickupCenter.setPhone(phone);
        if(delivery != null)
            pickupCenter.setDelivery(delivery);

        pickupRepository.save(pickupCenter);
        return "State added";

    }

    protected PickupCenterResponse responseMapper(PickupCenter pickup) {
        return PickupCenterResponse.builder()
                .id(pickup.getId())
                .name(pickup.getName())
                .location(pickup.getAddress())
                .state(pickup.getState().getName())
                .email(pickup.getEmail())
                .phone(pickup.getPhone())
                .delivery(pickup.getDelivery())
                .build();
    }

    private boolean confirmAuthority(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = authentication.getName();
        Person loggedInUser = personRepository.findByEmail(loggedInUserEmail).
                orElseThrow(() -> new UserNotFoundException("No user with this email"));
        return loggedInUser.getRole()== Role.ADMIN;
    }
}
