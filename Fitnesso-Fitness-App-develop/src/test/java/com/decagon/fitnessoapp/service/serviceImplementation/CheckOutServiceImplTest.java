//package com.decagon.fitnessoapp.service.serviceImplementation;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.atLeast;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.decagon.fitnessoapp.dto.AddressRequest;
//import com.decagon.fitnessoapp.dto.CheckOutRequest;
//import com.decagon.fitnessoapp.dto.DiscountRequest;
//import com.decagon.fitnessoapp.dto.OrderSummaryRequest;
//import com.decagon.fitnessoapp.dto.PaymentRequest;
//import com.decagon.fitnessoapp.model.product.CheckOut;
//import com.decagon.fitnessoapp.model.product.CouponCode;
//import com.decagon.fitnessoapp.model.product.IntangibleProduct;
//import com.decagon.fitnessoapp.model.product.ORDER_STATUS;
//import com.decagon.fitnessoapp.model.product.Order;
//import com.decagon.fitnessoapp.model.product.SHIPPING_METHOD;
//import com.decagon.fitnessoapp.model.product.ShoppingItem;
//import com.decagon.fitnessoapp.model.product.TangibleProduct;
//import com.decagon.fitnessoapp.model.user.Address;
//import com.decagon.fitnessoapp.model.user.PaymentCard;
//import com.decagon.fitnessoapp.model.user.Person;
//import com.decagon.fitnessoapp.model.user.ROLE_DETAIL;
//import com.decagon.fitnessoapp.repository.CheckOutRepository;
//import com.decagon.fitnessoapp.repository.PersonRepository;
//import com.decagon.fitnessoapp.repository.ShoppingCartRepository;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ContextConfiguration(classes = {CheckOutServiceImpl.class})
//@ExtendWith(SpringExtension.class)
//class CheckOutServiceImplTest {
//    @MockBean
//    private CheckOutRepository checkOutRepository;
//
//    @Autowired
//    private CheckOutServiceImpl checkOutServiceImpl;
//
//    @MockBean
//    private ModelMapper modelMapper;
//
//    @MockBean
//    private PersonRepository personRepository;
//
//    @MockBean
//    private ShoppingCartRepository shoppingCartRepository;
//
//    @Test
//    void testSave() {
//        IntangibleProduct intangibleProduct = new IntangibleProduct();
//        intangibleProduct.setCategory("Category");
//        intangibleProduct.setDescription("The characteristics of someone or something");
//        intangibleProduct.setDurationInDays(1);
//        intangibleProduct.setDurationInHoursPerDay(1);
//        intangibleProduct.setId(123L);
//        intangibleProduct.setImage("Image");
//        intangibleProduct.setPrice(BigDecimal.valueOf(42L));
//        intangibleProduct.setProductName("Product Name");
//        intangibleProduct.setStock(1L);
//        intangibleProduct.setStockKeepingUnit("Stock Keeping Unit");
//
//        TangibleProduct tangibleProduct = new TangibleProduct();
//        tangibleProduct.setCategory("Category");
//        tangibleProduct.setDescription("The characteristics of someone or something");
//        tangibleProduct.setId(123L);
//        tangibleProduct.setImage("Image");
//        tangibleProduct.setPrice(BigDecimal.valueOf(42L));
//        tangibleProduct.setProductName("Product Name");
//        tangibleProduct.setQuantity(1);
//        tangibleProduct.setStock(1L);
//        tangibleProduct.setStockKeepingUnit("Stock Keeping Unit");
//
//        ShoppingItem shoppingItem = new ShoppingItem();
//        shoppingItem.setId(123L);
//        shoppingItem.setIntangibleProducts(intangibleProduct);
//        shoppingItem.setQuantity(1);
//        shoppingItem.setTangibleProducts(tangibleProduct);
//        Optional<ShoppingItem> ofResult = Optional.of(shoppingItem);
//        when(this.shoppingCartRepository.findById(any())).thenReturn(ofResult);
//
//        Person person = new Person();
//        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
//        person.setDateOfBirth(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
//        person.setEmail("jane.doe@example.org");
//        person.setFirstName("Jane");
//        person.setGender("Gender");
//        person.setId(123L);
//        person.setImage("Image");
//        person.setLastName("Doe");
//        person.setPassword("iloveyou");
//        person.setPhoneNumber("4105551212");
//        person.setResetPasswordToken("ABC123");
//        person.setRoleDetail(ROLE_DETAIL.ANONYMOUS);
//        person.setUserName("janedoe");
//        person.setVerifyEmail(true);
//        Optional<Person> ofResult1 = Optional.of(person);
//        when(this.personRepository.findByEmail(any())).thenReturn(ofResult1);
//        doNothing().when(this.modelMapper).map(any(), (Object) any());
//
//        Address address = new Address();
//        address.setCity("Oxford");
//        address.setCountry("GB");
//        address.setId(123L);
//        address.setPerson(person);
//        address.setState("MD");
//        address.setStreetDetail("Street Detail");
//        address.setZipCode("21654");
//
//        Order order = new Order();
//        order.setCouponCode(new CouponCode());
//        order.setId(123L);
//        order.setOrderDate(mock(Timestamp.class));
//        order.setOrderStatus(ORDER_STATUS.PENDING);
//        order.setPerson(new Person());
//        order.setShippingAddress(new Address());
//        order.setShippingMethod(SHIPPING_METHOD.FLAT_RATE);
//        order.setShoppingItems(new ArrayList<>());
//        order.setTotalPrice(10.0);
//
//        CouponCode couponCode = new CouponCode();
//        couponCode.setCouponCode("Coupon Code");
//        couponCode.setDiscountPercent(10.0f);
//        couponCode.setId(123L);
//        couponCode.setOrder(order);
//
//        PaymentCard paymentCard = new PaymentCard();
//        paymentCard.setAccountName("Dr Jane Doe");
//        paymentCard.setCardNumber(1L);
//        paymentCard.setCvvNumber(10);
//        paymentCard.setExpiringDate("2020-03-01");
//        paymentCard.setId(123L);
//        paymentCard.setPerson(person);
//
//        CheckOut checkOut = new CheckOut();
//        checkOut.setBillingAddress(address);
//        checkOut.setCouponCode(couponCode);
//        checkOut.setId(123L);
//        checkOut.setOrderDate(mock(Timestamp.class));
//        checkOut.setPaymentCard(paymentCard);
//        checkOut.setPerson(person);
//        checkOut.setShippingAddress(address);
//        checkOut.setShippingMethod(SHIPPING_METHOD.FLAT_RATE);
//        checkOut.setShoppingItems(shoppingItem);
//        checkOut.setTotalPrice(BigDecimal.valueOf(42L));
//        when(this.checkOutRepository.save(any())).thenReturn(checkOut);
//
//        AddressRequest addressRequest = new AddressRequest();
//        addressRequest.setCity("Oxford");
//        addressRequest.setCountry("GB");
//        addressRequest.setState("MD");
//        addressRequest.setStreetDetail("Street Detail");
//        addressRequest.setUserName("janedoe");
//        addressRequest.setZipCode("21654");
//
//        DiscountRequest discountRequest = new DiscountRequest();
//        discountRequest.setCouponCode("Coupon Code");
//        discountRequest.setDiscountPercent(10.0f);
//
//        OrderSummaryRequest orderSummaryRequest = new OrderSummaryRequest();
//        orderSummaryRequest.setFlatRate(BigDecimal.valueOf(42L));
//        orderSummaryRequest.setSubTotal(BigDecimal.valueOf(42L));
//        orderSummaryRequest.setTotal(BigDecimal.valueOf(42L));
//
//        PaymentRequest paymentRequest = new PaymentRequest();
//        paymentRequest.setCardNumber(1L);
//        paymentRequest.setCvvNumber(10);
//        paymentRequest.setExpiringDate("2020-03-01");
//
//
//        CheckOutRequest checkOutRequest = new CheckOutRequest();
//        checkOutRequest.setBillingAddress(addressRequest);
//        checkOutRequest.setDiscountRequest(discountRequest);
//        checkOutRequest.setEmail("jane.doe@example.org");
//        checkOutRequest.setOrderSummary(orderSummaryRequest);
//        checkOutRequest.setPaymentRequest(paymentRequest);
//        checkOutRequest.setShippingAddress(addressRequest);
//        checkOutRequest.setShippingMethod(SHIPPING_METHOD.FLAT_RATE);
//        checkOutRequest.setShoppingCartId(123L);
//        assertEquals("Check-out Successful", this.checkOutServiceImpl.save(checkOutRequest).getMessage());
//        verify(this.shoppingCartRepository).findById(any());
//        verify(this.personRepository).findByEmail(any());
//        verify(this.modelMapper, atLeast(1)).map(any(), (Object) any());
//        verify(this.checkOutRepository).save(any());
//    }
//}
//
