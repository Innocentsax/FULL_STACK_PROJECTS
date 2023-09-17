package com.decagon.safariwebstore.controller;

import com.decagon.safariwebstore.model.Address;
import com.decagon.safariwebstore.model.User;
import com.decagon.safariwebstore.service.AddressService;
import com.decagon.safariwebstore.service.UserService;
import com.decagon.safariwebstore.utils.JWTUtil;
import com.decagon.safariwebstore.utils.MethodUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @GetMapping("/all")
    @Secured({"ADMIN","USER"})
    public List<Address> getAllUserAddresses(HttpServletRequest request){
        String jwt = MethodUtils.parseJwt(request);
        String email = jwtUtil.extractUserName(jwt);
        User user = userService.findUserByEmail(email);
        return addressService.getAllUserAddresses(user);
    }

    @GetMapping("/default")
    @Secured({"ADMIN","USER"})
    public Address getUserDefaultAddress(HttpServletRequest request) {
        String jwt = MethodUtils.parseJwt(request);
        String email = jwtUtil.extractUserName(jwt);
        User user = userService.findUserByEmail(email);
        return addressService.getUserDefaultAddress(user);
    }

    @PostMapping("/add")
    @Secured({"ADMIN","USER"})
    public ResponseEntity<Address> addNewAddress(@Valid @RequestBody Address addressRequest,
                                           HttpServletRequest request){
        String jwt = MethodUtils.parseJwt(request);
        String email = jwtUtil.extractUserName(jwt);
        User user = userService.findUserByEmail(email);
        Address address = addressService.saveAddress(addressRequest, user);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PutMapping("/edit/{addressId}")
    @Secured({"ADMIN","USER"})
    public ResponseEntity<Address> editAddress(@Valid @RequestBody Address addressRequest, @PathVariable Long addressId){
        Address address = addressService.editAddress(addressId, addressRequest);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{addressId}")
    @Secured({"ADMIN","USER"})
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
        return addressService.deleteAddress(addressId);
    }
}
