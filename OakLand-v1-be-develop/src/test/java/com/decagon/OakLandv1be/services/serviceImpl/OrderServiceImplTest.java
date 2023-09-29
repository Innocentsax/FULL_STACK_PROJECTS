//package com.decagon.OakLandv1be.services.serviceImpl;
//
//import com.decagon.OakLandv1be.dto.OrderResponseDto;
//import com.decagon.OakLandv1be.entities.*;
//import com.decagon.OakLandv1be.enums.DeliveryStatus;
//import com.decagon.OakLandv1be.exceptions.EmptyListException;
//import com.decagon.OakLandv1be.repositries.CustomerRepository;
//import com.decagon.OakLandv1be.repositries.OrderRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.domain.Page;
//
//import java.util.*;
//
//import static com.decagon.OakLandv1be.enums.ModeOfDelivery.DOORSTEP;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//class OrderServiceImplTest {
//    @Mock
//    CustomerRepository customerRepository;
//
//    @Mock
//    OrderRepository orderRepository;
//
//    @InjectMocks
//    OrderServiceImpl orderService;
//
//    private Order order;
//    private OrderResponseDto orderResponseDto;
//    private ModeOfPayment modeOfPayment;
//    private DeliveryStatus deliveryStatus;
//    private Address address;
//    private Transaction transaction;
//    private Customer customer;
//    private List<Order> orders;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        customer = new Customer();
//        customer.setId(1L);
//        modeOfPayment = new ModeOfPayment();
//        delivery = new Delivery();
//        address = Address.builder().fullName("Decagon").emailAddress("mag@gmail.com").street("Ohen Street")
//                .country("Edo").state("Nigeria").customer(customer).build();
//        transaction = new Transaction();
//         order = Order.builder()
//                .items(new HashSet<>()).modeOfPayment(modeOfPayment).modeOfDelivery(DOORSTEP).deliveryFee(2000.00)
//                .delivery(delivery).grandTotal(5000.00).discount(500.00).address(address).transaction(transaction).build();
//        order.setId(2L);
//        orderResponseDto = OrderResponseDto.builder()
//                .items(new HashSet<>()).modeOfPayment(modeOfPayment).modeOfDelivery(DOORSTEP).deliveryFee(2000.00)
//                .delivery(delivery).grandTotal(5000.00).discount(500.00).address(address).transaction(transaction).build();
//        orders = Arrays.asList(new Order(), new Order(), new Order());
//        when(orderRepository.findAll()).thenReturn(orders);
//    }
//
//    @Test
//    void viewAParticularOrder() {
//        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));
//        OrderResponseDto actual = orderService.viewAParticularOrder(2L);
//        assertEquals(0, orderResponseDto.getItems().size());
//        assertNotNull(orderResponseDto.getAddress());
//        assertNotNull(orderResponseDto.getTransaction());
//        assertEquals(orderResponseDto, actual);
//    }
//
//    @Test
//    void viewAllOrdersPaginated() {
//        Page<OrderResponseDto> result = orderService.viewAllOrdersPaginated(0, 2, "id", true);
//        assertEquals(2, result.getSize());
//        assertEquals(3, result.getTotalElements());
//        assertEquals(2, result.getNumberOfElements());
//        verify(orderRepository).findAll();
//    }
//
//    @Test
//    public void testViewAllSubCategories_EmptyList() {
//        List<Order> orders = new ArrayList<>();
//        when(orderRepository.findAll()).thenReturn(orders);
//        assertThrows(EmptyListException.class, () -> orderService.viewAllOrdersPaginated(0, 2, "id", true));
//        verify(orderRepository, times(1)).findAll();
//    }
//}