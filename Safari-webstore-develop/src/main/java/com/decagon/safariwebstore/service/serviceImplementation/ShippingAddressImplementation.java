package com.decagon.safariwebstore.service.serviceImplementation;

import com.decagon.safariwebstore.exceptions.ResourceNotFoundException;
import com.decagon.safariwebstore.model.ShippingAddress;
import com.decagon.safariwebstore.model.User;
import com.decagon.safariwebstore.repository.ShippingAddressRepository;
import com.decagon.safariwebstore.service.ShippingAddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class ShippingAddressImplementation implements ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;


    @Override
    public List<ShippingAddress> getAllUserShippingAddresses(User user) {
        return shippingAddressRepository
                .findAllByUser(user)
                .orElseThrow(
                        () -> {
                            throw new ResourceNotFoundException("Addresses not found for user!");
                        }
                );
    }

    @Override
    public ShippingAddress getUserDefaultShippingAddress(User user) {
        return getAllUserShippingAddresses(user)
                .stream()
                .filter(ShippingAddress::getIsDefaultShippingAddress)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Default address not found!"));
    }

    @Override
    public ShippingAddress saveShippingAddress(ShippingAddress shippingAddressRequest, User user) {

        List<ShippingAddress> listOfShippingAddresses = getAllUserShippingAddresses(user);

        if (listOfShippingAddresses.isEmpty()) {
            shippingAddressRequest.setIsDefaultShippingAddress(true);
        } else if (shippingAddressRequest.getIsDefaultShippingAddress()){
            Optional<ShippingAddress> currentDefaultShippingAddress = getAllUserShippingAddresses(user).stream().filter(ShippingAddress::getIsDefaultShippingAddress).findFirst();
            currentDefaultShippingAddress.ifPresent(shippingAddress -> shippingAddress.setIsDefaultShippingAddress(false));
            currentDefaultShippingAddress.ifPresent(shippingAddressRepository::save);
        }

        shippingAddressRequest.setUser(user);
        return shippingAddressRepository.save(shippingAddressRequest);
    }

    @Override
    public ShippingAddress editShippingAddress(Long addressId, ShippingAddress shippingAddressRequest, User user) {
        Optional<ShippingAddress> shippingAddress = shippingAddressRepository.findById(addressId);
        if(shippingAddress.isPresent()) {
            shippingAddress.get().setAddress(shippingAddressRequest.getAddress());
            shippingAddress.get().setCity(shippingAddressRequest.getCity());
            shippingAddress.get().setState(shippingAddressRequest.getState());
            shippingAddress.get().setPhone(shippingAddressRequest.getPhone());
            shippingAddress.get().setIsDefaultShippingAddress(shippingAddressRequest.getIsDefaultShippingAddress());
            if (shippingAddress.get().getIsDefaultShippingAddress()){
                Optional<ShippingAddress> currentDefaultShippingAddress = getAllUserShippingAddresses(user).stream().filter(ShippingAddress::getIsDefaultShippingAddress).findFirst();
                if (!shippingAddress.get().getId().equals(currentDefaultShippingAddress.get().getId())) {
                    currentDefaultShippingAddress.ifPresent(address -> address.setIsDefaultShippingAddress(false));
                    currentDefaultShippingAddress.ifPresent(shippingAddressRepository::save);
                }
            } else if (!shippingAddress.get().getIsDefaultShippingAddress()){
                Optional<ShippingAddress> currentDefaultShippingAddress = getAllUserShippingAddresses(user).stream().filter(ShippingAddress::getIsDefaultShippingAddress).findFirst();
                if (currentDefaultShippingAddress.isEmpty()) {
                    List<ShippingAddress> listOfShippingAddresses = getAllUserShippingAddresses(user);
                    Optional<ShippingAddress> shippingAddress1 = listOfShippingAddresses.stream().findFirst();
                    shippingAddress1.ifPresent(address -> address.setIsDefaultShippingAddress(true));
                    shippingAddress1.ifPresent(shippingAddressRepository::save);
                }
            }
            return shippingAddressRepository.save(shippingAddress.get());
        } else {
            throw new ResourceNotFoundException("Shipping Address not found!");
        }
    }

    @Override
    public ResponseEntity<?> deleteShippingAddress(Long addressId, User user) {
        Optional<ShippingAddress> shippingAddress = shippingAddressRepository.findById(addressId);
        if (shippingAddress.isPresent()) {
            shippingAddressRepository.delete(shippingAddress.get());
            List<ShippingAddress> listOfShippingAddresses = getAllUserShippingAddresses(user);
            Optional<ShippingAddress> shippingAddress1 = listOfShippingAddresses.stream().findFirst();
            shippingAddress1.ifPresent(address -> address.setIsDefaultShippingAddress(true));
            shippingAddress1.ifPresent(shippingAddressRepository::save);
            return new ResponseEntity<>("ShippingAddress has been deleted.", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("ShippingAddress not found!!");
        }
    }

}
