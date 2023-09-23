package com.decagon.fitnessoapp.service.serviceImplementation;

import com.decagon.fitnessoapp.dto.CheckOutRequest;
import com.decagon.fitnessoapp.dto.CheckOutResponse;
import com.decagon.fitnessoapp.dto.OrderResponse;
import com.decagon.fitnessoapp.dto.transactionDto.TransactionResponseDTO;
import com.decagon.fitnessoapp.model.product.*;
import com.decagon.fitnessoapp.model.user.Address;
import com.decagon.fitnessoapp.model.user.PaymentCard;
import com.decagon.fitnessoapp.model.user.Person;
import com.decagon.fitnessoapp.repository.*;
import com.decagon.fitnessoapp.service.CheckOutService;
import com.decagon.fitnessoapp.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CheckOutServiceImpl implements CheckOutService {

    private final CheckOutRepository checkOutRepository;
    private final PersonRepository personRepository;
    private final PaymentCardRepository paymentCardRepository;
    private final AddressRepository addressRepository;
    private final CouponCodeRepository couponCodeRepository;
    private final ModelMapper modelMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final TransactionService transactionService;


    @Override
    public CheckOutResponse save(CheckOutRequest checkOutRequest) {
        CheckOut checkOut = new CheckOut();
        Address billingAddress = new Address();
        Address shippingAddress = new Address();
        PaymentCard paymentCard = new PaymentCard();
        Optional<CouponCode> couponCode = couponCodeRepository.
                findByCouponCode(checkOutRequest.getDiscountRequest().getCouponCode());
        Optional<Person> personExists = personRepository.findByEmail(checkOutRequest.getEmail());
        List<Cart> shoppingItemExist = shoppingCartRepository.findAllByUniqueCartId(checkOutRequest.getShoppingCartUniqueId());
        if(personExists.isPresent()){
            if(!shoppingItemExist.isEmpty()){
                if(shoppingItemExist.get(0).getCartReference() == null) {
                    checkOut.setPerson(personExists.get());
                    billingAddress.setPerson(personExists.get());
                    shippingAddress.setPerson(personExists.get());
                    paymentCard.setPerson(personExists.get());
//                    paymentCard.setAccountName(personExists.get().getFirstName());
                    modelMapper.map(checkOutRequest.getPaymentRequest(), paymentCard);
                    modelMapper.map(checkOutRequest.getBillingAddress(), billingAddress);
                    modelMapper.map(checkOutRequest.getShippingAddress(), shippingAddress);
                    paymentCardRepository.save(paymentCard);
                    addressRepository.save(billingAddress);
                    addressRepository.save(shippingAddress);
                    final String randomString = RandomString.make(10);
                    shoppingItemExist.forEach((cart) -> cart.setCartReference(randomString));
                    shoppingCartRepository.saveAll(shoppingItemExist);
                    checkOut.setTotalPrice(checkOutRequest.getOrderSummary().getTotal());
                    checkOut.setBillingAddress(billingAddress);
                    checkOut.setShippingAddress(shippingAddress);
                    checkOut.setShoppingCartUniqueId(shoppingItemExist.get(0).getUniqueCartId());
                    checkOut.setShippingMethod(checkOutRequest.getShippingMethod());
                    if (couponCode.isPresent() && couponCode.get().getExpiresAt().isBefore(LocalDateTime.now())) {
                        final BigDecimal total_cost = checkOutRequest.getOrderSummary().getTotal();
                        checkOut.setTotalPrice(BigDecimal.
                                valueOf(total_cost.floatValue() * couponCode.get().getDiscountPercent()));
                        checkOut.setCouponCode(checkOutRequest.getDiscountRequest().getCouponCode());
                    }
                    checkOut.setPaymentCard(paymentCard);
                    final String referenceNumber = "fitnesso-app-" + RandomString.make(16);
                    checkOut.setTransactionStatus(TRANSACTION_STATUS.PENDING);
                    checkOut.setReferenceNumber(referenceNumber);
                    checkOutRepository.save(checkOut);
                    System.out.println(checkOut.getTotalPrice());
                    TransactionResponseDTO response = transactionService.initializeTransaction(
                            personExists.get().getEmail(),
                            checkOut.getTotalPrice(), referenceNumber, checkOutRequest.getPaymentRequest().getCardNumber());
                    return CheckOutResponse.builder()
                            .message("Complete your Payment").link(response.getData().getAuthorization_url())
                            .timestamp(LocalDateTime.now()).build();
                }


                final String referenceNumber = "fitnesso-app-" + RandomString.make(16);
                Optional<CheckOut> checkOutResend = checkOutRepository.
                        findCheckOutByShoppingCartUniqueId(checkOutRequest.getShoppingCartUniqueId());
                if(checkOutResend.isPresent() && checkOutResend.get().getTransactionStatus().equals(TRANSACTION_STATUS.PENDING)){
                    checkOutResend.get().setReferenceNumber(referenceNumber);
                    updateCheckOut(checkOutResend.get());
                    TransactionResponseDTO response = transactionService.initializeTransaction(
                            personExists.get().getEmail(),
                            checkOutResend.get().getTotalPrice(),
                            referenceNumber,
                            checkOutResend.get().getPaymentCard().getCardNumber());

                    return CheckOutResponse.builder()
                            .message("Complete your Payment").link(response.getData().getAuthorization_url())
                            .timestamp(LocalDateTime.now()).build();
                }
                else if (checkOutResend.isPresent() && checkOutResend.get().getTransactionStatus().equals(TRANSACTION_STATUS.COMPLETED)){
                    return CheckOutResponse.builder()
                            .message("Shopping Items have been fully paid").timestamp(LocalDateTime.now()).build();
                }
                return CheckOutResponse.builder()
                        .message("Error! finding the Cart").timestamp(LocalDateTime.now()).build();
            }

            return CheckOutResponse.builder().message("Shopping cart is empty").timestamp(LocalDateTime.now()).build();

        }
        return CheckOutResponse.builder().message("User not Registered").timestamp(LocalDateTime.now()).build();
    }



    public CheckOut findByReferenceNumber(String referenceNumber){
        Optional<CheckOut> checkOut = checkOutRepository.findByReferenceNumber(referenceNumber);
        return checkOut.orElse(null);
    }


    public CheckOut updateCheckOut(CheckOut checkOut){
        return checkOutRepository.save(checkOut);
    }

}
