//package com.decagon.fitnessoapp.service.serviceImplementation;
//
//import com.decagon.fitnessoapp.dto.OrderResponse;
//import com.decagon.fitnessoapp.model.product.CouponCode;
//import com.decagon.fitnessoapp.model.product.ORDER_STATUS;
//import com.decagon.fitnessoapp.model.product.Order;
//import com.decagon.fitnessoapp.model.product.SHIPPING_METHOD;
//import com.decagon.fitnessoapp.model.user.Address;
//import com.decagon.fitnessoapp.model.user.Person;
//import com.decagon.fitnessoapp.model.user.ROLE_DETAIL;
//import com.decagon.fitnessoapp.repository.OrderRepository;
//import com.decagon.fitnessoapp.repository.PersonRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.TestingAuthenticationToken;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
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
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ContextConfiguration(classes = {OrderServiceImpl.class})
//@ExtendWith(SpringExtension.class)
//class OrderServiceImplTest {
//    @MockBean
//    private ModelMapper modelMapper;
//
//    @MockBean
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private OrderServiceImpl orderServiceImpl;
//
//    @MockBean
//    private PersonRepository personRepository;
//
//    Person person = new Person();
//    CouponCode couponCode = new CouponCode();
//    Order order = new Order();
//    Address address = new Address();
//
//    @BeforeEach
//    void test(){
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
//
//
//        couponCode.setCouponCode("Coupon Code");
//        couponCode.setDiscountPercent(10.0f);
//        couponCode.setId(123L);
//        couponCode.setOrder(new Order());
//
//
//        address.setCity("Oxford");
//        address.setCountry("GB");
//        address.setId(123L);
//        address.setPerson(new Person());
//        address.setState("MD");
//        address.setStreetDetail("Street Detail");
//        address.setZipCode("21654");
//
//
//        order.setCouponCode(couponCode);
//        order.setId(123L);
//        order.setOrderDate(mock(Timestamp.class));
//        order.setOrderStatus(ORDER_STATUS.PENDING);
//        order.setPerson(person);
//        order.setShippingAddress(address);
//        order.setShippingMethod(SHIPPING_METHOD.FLAT_RATE);
//        //order.setCartsr(new ArrayList<>());
//        order.setTotalPrice(BigDecimal.valueOf(10.0));
//
//    }
//
//    @Test
//    void testGetOrder() {
//
//        Optional<Person> ofResult = Optional.of(person);
//        when(this.personRepository.findPersonByUserName((String) any())).thenReturn(ofResult);
//
//        Optional<Order> ofResult1 = Optional.of(order);
//        when(this.orderRepository.findOrderByPerson_Id((Long) any())).thenReturn(ofResult1);
//        doNothing().when(this.modelMapper).map((Object) any(), (Object) any());
//        ResponseEntity<OrderResponse> actualOrder = this.orderServiceImpl
//                .getOrder(new TestingAuthenticationToken("Principal", "Credentials"));
//        assertEquals("<200 OK OK,OrderResponse(Id=null, shoppingItems=null, totalPrice=null, shippingAddress=null,"
//                + " orderStatus=null, shippingMethod=null),[]>", actualOrder.toString());
//        assertTrue(actualOrder.getHeaders().isEmpty());
//        assertTrue(actualOrder.hasBody());
//        assertEquals(HttpStatus.OK, actualOrder.getStatusCode());
//        verify(this.personRepository).findPersonByUserName((String) any());
//        verify(this.orderRepository).findOrderByPerson_Id((Long) any());
//        verify(this.modelMapper).map((Object) any(), (Object) any());
//    }
//
//    @Test
//    void testGetOrder2() {
//        when(this.personRepository.findPersonByUserName((String) any())).thenReturn(Optional.empty());
//
//        Optional<Order> ofResult = Optional.of(order);
//        when(this.orderRepository.findOrderByPerson_Id((Long) any())).thenReturn(ofResult);
//        doNothing().when(this.modelMapper).map((Object) any(), (Object) any());
//        assertThrows(UsernameNotFoundException.class,
//                () -> this.orderServiceImpl.getOrder(new TestingAuthenticationToken("Principal", "Credentials")));
//        verify(this.personRepository).findPersonByUserName((String) any());
//    }
//}
//
