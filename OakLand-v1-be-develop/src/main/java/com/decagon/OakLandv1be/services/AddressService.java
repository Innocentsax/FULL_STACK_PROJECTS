package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.AddressRequestDto;
import com.decagon.OakLandv1be.dto.AddressResponseDto;
import com.decagon.OakLandv1be.entities.Address;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AddressService {
    String createAddress(AddressRequestDto request);

    String updateAddress(Long address_id, AddressRequestDto request);

    void setAsDefault(Long address_id);

    void DeleteAddress(Long addressId);

    Set<AddressResponseDto> getAllAddress();

    AddressResponseDto viewAddress(Long addressId);

}
