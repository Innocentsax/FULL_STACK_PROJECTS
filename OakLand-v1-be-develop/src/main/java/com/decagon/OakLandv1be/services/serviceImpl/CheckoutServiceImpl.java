package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.AddressRequestDto;
import com.decagon.OakLandv1be.dto.CheckoutDto;
import com.decagon.OakLandv1be.dto.CheckoutResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.DeliveryStatus;
import com.decagon.OakLandv1be.enums.ModeOfDelivery;
import com.decagon.OakLandv1be.exceptions.*;
import com.decagon.OakLandv1be.repositries.*;
import com.decagon.OakLandv1be.services.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
    private final PersonRepository personRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final WalletRepository walletRepository;
    Double itemDiscount;
    Double discount;
    Double grandTotal;
    BigDecimal balance;

    @Override
    public ResponseEntity<CheckoutResponseDto> cartCheckout(CheckoutDto checkoutDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String email = auth.getName();
            // Get the customer and their cart
            Customer customer = personRepository.findByEmail(email)
                    .map(Person::getCustomer)
                    .orElseThrow(() -> new UnauthorizedUserException("Invalid user"));
            Cart cart = customer.getCart();
            if (!cart.getItems().isEmpty()){
                // Get the selected address
                Address address = addressRepository.findById(checkoutDto.getAddressId())
                        .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
                // Calculate the total and set order details
                Order order = new Order();
                Double total = cart.getItems().stream()
                        .mapToDouble(item -> item.getUnitPrice() * item.getOrderQty())
                        .sum();
                //Get the discount
                 discount = cart.getItems().size() >= 6 ? 4 : 0.5;
                 itemDiscount = total * (discount/100);
                 grandTotal = (total + order.getDeliveryFee()) - itemDiscount;
                //Get and Set Customer Wallet
                double currentBalance = customer.getWallet().getAccountBalance().compareTo(BigDecimal.valueOf(grandTotal));
                if (currentBalance < 0 ) {
                    throw new InsufficientBalanceInWalletException("Insufficient Fund");
                }
                Wallet wallet = new Wallet();
                wallet.setAccountBalance(wallet.getAccountBalance().subtract(BigDecimal.valueOf(grandTotal)));
                walletRepository.save(wallet);
                // Save the order and create the response
                order.setAddress(address);
                order.setCustomer(customer);
                order.setDiscount(itemDiscount);
                order.setDeliveryFee(0.00);
                order.setModeOfDelivery(this.modeOfDelivery(checkoutDto.getModeOfDelivery()));
                order.setDeliveryStatus(DeliveryStatus.TO_ARRIVE);
                //order.setItems(cart.getItems());
                order.setGrandTotal(grandTotal);
                orderRepository.save(order);

                CheckoutResponseDto response = CheckoutResponseDto.builder()
                        .customer(order.getCustomer())
                        //.items(order.getItems())
                        .deliveryFee(order.getDeliveryFee())
                        .modeOfDelivery(order.getModeOfDelivery())
                        .deliveryStatus(order.getDeliveryStatus())
                        .grandTotal(order.getGrandTotal())
                        .discount(order.getDiscount())
                        .address(order.getAddress())
                        .build();
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new EmptyListException("Cart is empty! Please add products to cart");
            }
        } else {
            throw new UnauthorizedUserException("Login to checkout");
        }
    }
    @Override
    public ModeOfDelivery modeOfDelivery(String deliveryMethod) throws InputMismatchException {
        switch (deliveryMethod.toUpperCase()){
            case "DOORSTEP":
                return ModeOfDelivery.DOORSTEP;
            case "PICKUP":
                return ModeOfDelivery.PICKUP;
            default: throw new InputMismatchException("Invalid delivery method");
        }
    }
    @Override
    public ResponseEntity<String> addNewAddress(AddressRequestDto addressRequestDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        if(!(auth instanceof AnonymousAuthenticationToken)){
            Person person = personRepository.findByEmail(email)
                    .orElseThrow(()->new UnauthorizedUserException("Login to add checkout address"));

            Customer customer = person.getCustomer();

            Address address = Address.builder()
                    .emailAddress(addressRequestDto.getEmailAddress())
                    .state(addressRequestDto.getState())
                    .country(addressRequestDto.getCountry())
                    .street(addressRequestDto.getStreet())
                    .fullName(addressRequestDto.getFullName())
                    .customer(customer)
                    .build();
             addressRepository.save(address);

             return new ResponseEntity<>("Address saved successfully", HttpStatus.OK);
        }
        throw new UnauthorizedUserException("Please login to add a new address");
    }

}

