package com.decagon.chompapp.services;

import com.decagon.chompapp.dtos.AddressDto;
import com.decagon.chompapp.models.Address;
import com.decagon.chompapp.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AddressService {

    ResponseEntity<Address> saveAddress(AddressDto addressDto);
    List<Address> getAllUserAddresses();
    Address updateAddress(Long addressId,AddressDto addressDto );
    ResponseEntity<String> deleteAddress(long addressId);
    Boolean isAddressExisting(Address address, User user);
    Address getUserDefaultAddress(User loggedInUser);
    Boolean userDefaultAddressExists(User user);
}
