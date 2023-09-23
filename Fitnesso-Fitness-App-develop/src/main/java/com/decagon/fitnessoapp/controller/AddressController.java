package com.decagon.fitnessoapp.controller;

import com.decagon.fitnessoapp.dto.AddressRegReq;
import com.decagon.fitnessoapp.dto.AddressRequest;
import com.decagon.fitnessoapp.dto.AddressResponse;
import com.decagon.fitnessoapp.model.user.Address;
import com.decagon.fitnessoapp.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@AllArgsConstructor
@CrossOrigin
public class AddressController {

    public final AddressService addressService;

    @PostMapping("/addAddress")
    public ResponseEntity<AddressResponse> addAddress(@RequestBody AddressRegReq addressRequest){
        return ResponseEntity.ok().body(addressService.createAddress(addressRequest));
    }

    @PutMapping("/update_address/")
    public ResponseEntity<AddressRequest> updateAddress(@RequestBody AddressRequest addressRequest) {
        return ResponseEntity.ok().body(addressService.updateAddress(addressRequest));
    }

    @DeleteMapping("/delete_address/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable("addressId") Long addressId) {
        return ResponseEntity.ok().body(addressService.deleteAddress(addressId));
    }

    @GetMapping("/find_address/{addressId}")
    public ResponseEntity<Address> findAddress(@PathVariable("addressId") Long addressId) {
        return ResponseEntity.ok().body(addressService.getAddress(addressId));
    }
}
