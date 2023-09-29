package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.PickupCenterRequest;
import com.decagon.OakLandv1be.dto.PickupCenterResponse;
import com.decagon.OakLandv1be.services.PickupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1/pickup")
@RequiredArgsConstructor
public class PickupController {
    private final PickupService pickupService;

    @GetMapping("/all")
    public ResponseEntity<Page<PickupCenterResponse>> getAll(@RequestParam(defaultValue = "0") Integer page ,
                                                             @RequestParam(defaultValue = "16") Integer size){
        log.info("My page accessed {}", page);
        return ResponseEntity.ok(pickupService.getAll(page,size));
    }

//    @GetMapping("/{name}")
//    public ResponseEntity<PickupCenterResponse> getPickupCenterByName(@PathVariable String name){
//        return ResponseEntity.ok(pickupService.getCenterByName(name));
//    }

    @GetMapping("/{email}")
    public ResponseEntity<PickupCenterResponse> getPickupCenterByEmail(@PathVariable String email){
        return ResponseEntity.ok(pickupService.getCenterByEmail(email));
    }

    @GetMapping("/state/{name}")
    public ResponseEntity<List<PickupCenterResponse>> getPickupCenterByStateName(@PathVariable String name){
        return ResponseEntity.ok(pickupService.getCenterByStateName(name));
    }
    @GetMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public  void deleteCenter(@PathVariable Long id){
        pickupService.deleteCenter(id);
    }

    @PostMapping("/new")
    public ResponseEntity<String>  newCenter(@RequestBody PickupCenterRequest pickupCenterRequest){
        return new ResponseEntity<>(pickupService.createCenter(pickupCenterRequest),HttpStatus.CREATED);
    }

    @PutMapping("/update/{pickupId}")
    public ResponseEntity<String> updatePickupCenter(@PathVariable Long pickupId, @RequestBody PickupCenterRequest pickupCenterRequest) {
        return new ResponseEntity<>(pickupService.updatePickupCenter(pickupId, pickupCenterRequest), HttpStatus.OK);
    }

}
