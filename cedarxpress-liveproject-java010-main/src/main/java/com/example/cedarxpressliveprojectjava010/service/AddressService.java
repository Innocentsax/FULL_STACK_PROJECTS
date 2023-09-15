package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.AddressDto;
import com.example.cedarxpressliveprojectjava010.entity.Address;
import org.springframework.http.ResponseEntity;

public interface AddressService {
    ResponseEntity<Address> createAddress(AddressDto addressDto);
}
