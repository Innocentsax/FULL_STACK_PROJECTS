package com.example.cedarxpressliveprojectjava010.service.impl;

import com.example.cedarxpressliveprojectjava010.dto.AddressDto;
import com.example.cedarxpressliveprojectjava010.entity.Address;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.enums.AddressType;
import com.example.cedarxpressliveprojectjava010.repository.AddressRepository;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Address> createAddress(AddressDto addressDto) {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByEmail(loggedInEmail);
        AddressType type = getAddressType(addressDto.getAddressType());
        Address address = Address.builder()
                .state(addressDto.getState())
                .city(addressDto.getCity())
                .address(addressDto.getAddress())
                .phoneNumber(addressDto.getPhoneNumber())
                .isDefault(addressDto.getIsDefault())
                .user(user)
                .addressType(type)
                .build();

        return new ResponseEntity<>(addressRepository.save(address), HttpStatus.CREATED);
    }

    private AddressType getAddressType(String addressString){
        String address = addressString.toLowerCase();
        if(address.contains("ship")){
            return AddressType.SHIPPING;
        }else{
            return AddressType.BILLING;
        }
    }
}
