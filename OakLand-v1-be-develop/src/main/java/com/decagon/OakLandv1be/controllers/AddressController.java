package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.AddressRequestDto;
import com.decagon.OakLandv1be.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/address")
public class AddressController {

    private final AddressService addressService;


    @PostMapping("/new")
    public ResponseEntity<Object> createAddress(@RequestBody AddressRequestDto request){
        return ResponseEntity.ok(addressService.createAddress(request));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAddress(@RequestParam("id") Long addressId, @RequestBody AddressRequestDto request){
        return ResponseEntity.ok(addressService.updateAddress(addressId, request));
    }

    @GetMapping("/setDefault")
    public ResponseEntity<Object> setAsDefault(@RequestParam("id") Long addressId){
        addressService.setAsDefault(addressId);
        return ResponseEntity.ok("Address set to default");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteAddress(@RequestParam("id") Long addressId){
        addressService.DeleteAddress(addressId);
        return ResponseEntity.ok("Address deleted");
    }

    @GetMapping("/get")
    public ResponseEntity<Object> getAllAddress(){
        return ResponseEntity.ok(addressService.getAllAddress());
    }

    @GetMapping("/view")
    public ResponseEntity<Object> viewAddress(@RequestParam("id") Long addressId){
        return ResponseEntity.ok(addressService.viewAddress(addressId));
    }

}
