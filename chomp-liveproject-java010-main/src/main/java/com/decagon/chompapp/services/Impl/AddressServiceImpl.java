package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.AddressDto;
import com.decagon.chompapp.models.Address;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.AddressRepository;
import com.decagon.chompapp.repositories.UserRepository;
import com.decagon.chompapp.services.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ResponseEntity<Address> saveAddress(AddressDto addressDto) {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsernameOrEmail(principal, principal).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        Address addressToBeSaved = modelMapper.map(addressDto, Address.class);
        addressToBeSaved.setUser(user);
        Address savedAddress =  addressRepository.save(addressToBeSaved);
        return ResponseEntity.ok(savedAddress);
    }

    @Override
    public List<Address> getAllUserAddresses() {
        return null;
    }

    @Override
    public Address updateAddress(Long addressId, AddressDto addressDto) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteAddress(long addressId) {
        return null;
    }

    @Override
    public Boolean isAddressExisting(Address address, User user) {
        return null;
    }

    @Override
    public Address getUserDefaultAddress(User loggedInUser) {
        return null;
    }

    @Override
    public Boolean userDefaultAddressExists(User user) {
        return null;
    }
}
