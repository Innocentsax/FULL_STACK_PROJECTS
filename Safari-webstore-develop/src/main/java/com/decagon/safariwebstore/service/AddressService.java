package com.decagon.safariwebstore.service;

import com.decagon.safariwebstore.model.Address;
import com.decagon.safariwebstore.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AddressService {
    Address saveAddress(Address address, User user);
    Address getAUserAddress(User user, String address, String city, String state);
    List<Address> getAllUserAddresses(User user);
    Boolean isAddressExisting(Address address, User user);
    Address getUserDefaultAddress(User loggedInUser);
    Address getAddressByUserAndAddressAndCityAndState(User user, String address,
                                                      String city, String state);
    Boolean userDefaultAddressExists(User user);
    Address editAddress(Long addressId, Address addressRequest);
    ResponseEntity<?> deleteAddress(Long addressId);
}
