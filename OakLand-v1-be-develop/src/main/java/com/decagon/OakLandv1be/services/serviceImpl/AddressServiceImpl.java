package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.AddressRequestDto;
import com.decagon.OakLandv1be.dto.AddressResponseDto;
import com.decagon.OakLandv1be.entities.Address;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.repositries.AddressRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.decagon.OakLandv1be.utils.UserUtil.extractEmailFromPrincipal;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;


    @Override
    public String createAddress(AddressRequestDto request){
        String email = extractEmailFromPrincipal().get();

        Person person = personRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Customer customer = person.getCustomer();

        Address address = Address.builder()
                .customer(customer)
                .fullName(request.getFullName())
                .street(request.getStreet())
                .state(request.getState())
                .country(request.getCountry())
                .isDefault(false)
                .phone(request.getPhone())
                .emailAddress(request.getEmailAddress())
                .build();
        addressRepository.save(address);
        return "Address saved succesfully";
    }



    @Override
    public String updateAddress(Long address_id, AddressRequestDto request){
        Address address = getAddress(address_id);

        address.setEmailAddress(request.getEmailAddress());
        address.setFullName(request.getFullName());
        address.setStreet(request.getStreet());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPhone(request.getPhone());
        addressRepository.save(address);

        return "Address updated successfully";
    }


    @Override
    public void setAsDefault(Long address_id){
        Address address = getAddress(address_id);
        address.setIsDefault(true);
        addressRepository.save(address);

        List<Address> addressList = addressRepository.findAll();
        addressList.stream()
                .filter(address1 -> address1 != address)
                .forEach(address1 -> address1.setIsDefault(false));
        addressRepository.saveAll(addressList);
    }

    @Override
    public void DeleteAddress(Long addressId){
        Address address = getAddress(addressId);
        addressRepository.delete(address);
    }

    @Override
    public Set<AddressResponseDto> getAllAddress(){
        String email = extractEmailFromPrincipal().get();
        Person person = personRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(("User not found")));
        Customer customer = person.getCustomer();
        Set<Address> addressList = customer.getAddressBook();
        Set<AddressResponseDto> addressResponse = new HashSet<>();
        addressList
                .forEach(address -> {
                    AddressResponseDto response = AddressResponseDto.builder()
                            .id(address.getId())
                            .fullName(address.getFullName())
                            .street(address.getStreet())
                            .state(address.getState())
                            .country(address.getCountry())
                            .phone(address.getPhone())
                            .isDefault(address.getIsDefault())
                            .build();
                    addressResponse.add(response);
                });
        return addressResponse;
    }

    @Override
    public AddressResponseDto viewAddress(Long addressId){
        Address address = getAddress(addressId);
        return AddressResponseDto.builder()
                .id(address.getId())
                .fullName(address.getFullName())
                .phone(address.getPhone())
                .emailAddress(address.getEmailAddress())
                .street(address.getStreet())
                .state(address.getState())
                .country(address.getCountry())
                .isDefault(address.getIsDefault())
                .build();
    }


    public Address getAddress(Long addressId){
        return addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }


}
