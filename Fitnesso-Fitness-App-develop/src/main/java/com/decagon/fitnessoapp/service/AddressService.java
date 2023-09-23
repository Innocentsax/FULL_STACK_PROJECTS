package com.decagon.fitnessoapp.service;

import com.decagon.fitnessoapp.dto.AddressRegReq;
import com.decagon.fitnessoapp.dto.AddressRequest;
import com.decagon.fitnessoapp.dto.AddressResponse;
import com.decagon.fitnessoapp.model.user.Address;
import org.springframework.http.ResponseEntity;

public interface AddressService {
    AddressResponse createAddress(AddressRegReq addressRequest);

    AddressRequest updateAddress(AddressRequest request);

    String deleteAddress(Long id);

    Address getAddress(Long id);
}
