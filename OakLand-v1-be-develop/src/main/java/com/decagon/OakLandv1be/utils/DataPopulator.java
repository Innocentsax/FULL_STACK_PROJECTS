//package com.decagon.OakLandv1be.utils;
//
//
//import com.decagon.OakLandv1be.entities.*;
//import com.decagon.OakLandv1be.enums.Gender;
//import com.decagon.OakLandv1be.enums.Role;
//import com.decagon.OakLandv1be.repositries.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import javax.annotation.PostConstruct;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class DataPopulator {
//
//    private final PasswordEncoder passwordEncoder;
//    private final PersonRepository personRepository;
//    private final CustomerRepository customerRepository;
//    private final CartRepository cartRepository;
//    private final ProductRepository productRepository;
//    private final ItemRepository itemRepository;
//    private final OrderRepository orderRepository;
//    private final StateRepository stateRepository;
//    private final PickupRepository pickupRepository;
//
//
//    @PostConstruct
//    public void createData(){
//        Product product = Product.builder()
//                .availableQty(1)
//                .name("Queen Bunette")
//                .imageUrl("https://www.istockphoto.com/photo/great-idea-of-a-marketing-strategy-plan-at-a-creative-office-gm1385970223-444467589")
//                .price(8000D)
//                .build();
//        productRepository.save(product);
//
//        Person person = Person.builder()
//                .firstName("Chiji")
//                .lastName("Boy")
//                .password(passwordEncoder.encode("password123"))
//                .email("chiji@gmail.com")
//                .gender(Gender.OTHER)
//                .date_of_birth("12-09-1993")
//                .phone("78573944843")
//                .verificationStatus(true)
//                .isActive(true)
//                .address("Foolish address")
//                .role(Role.CUSTOMER)
//                .build();
//                Person savedPerson = personRepository.save(person);
//
//        Customer customer = Customer.builder()
//
//                .person(savedPerson)
//                .isActive(true)
//                .cart(new Cart())
//                .build();
//        Customer savedCustomer = customerRepository.save(customer);
//
//        Order order = Order.builder()
//                .customer(savedCustomer)
//                .build();
//        Order savedOrder = orderRepository.save(order);
//
//        Cart cart = Cart.builder()
//                .total(3000D)
//                .customer(savedCustomer)
//                .build();
//        Cart savedCart = cartRepository.save(cart);
//        savedCustomer.setCart(savedCart);
//        customerRepository.save(savedCustomer);
//
//        List<Item> items = List.of(
//                        Item.builder()
//                .imageUrl("https://www.istockphoto.com/photo")
//                .product(product)
//                .order(savedOrder)
//                .cart(savedCart)
//                .orderQty(6)
//                .subTotal(6000D)
//                .unitPrice(1000D)
//                .productName("Rock Couch")
//                .build(),
//                        Item.builder()
//                                .imageUrl("https://www.istockphoto.com/photo")
//                                .subTotal(5000D)
//                                .productName("Brooke Bed")
//                                .orderQty(5)
//                                .unitPrice(1000D)
//                                .order(savedOrder)
//                                .cart(savedCart)
//                                .build());
//        itemRepository.saveAll(items);
//
//
//        State state = State.builder()
//                .name("Lagos")
//                .build();
//        State savedState = stateRepository.save(state);
//
//        List<PickupCenter> pickupCenterList = List.of(PickupCenter.builder()
//                .state(savedState)
//                .phone("07062126666")
//                .email("slot@gmail.com")
//                .address("1 Deck close")
//                .name("Slot Center")
//                .delivery(4500D)
//                .build(),
//
//                PickupCenter.builder()
//                .state(savedState)
//                .phone("09011116666")
//                .email("circle@gmail.com")
//                .address("22 Circle street")
//                .name("Circular Mall")
//                .delivery(5000D)
//                .build());
//
//        pickupRepository.saveAll(pickupCenterList);
//
//    }
//}