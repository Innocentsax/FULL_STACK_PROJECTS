package com.decagon.fitnessoapp.service.serviceImplementation;

import com.decagon.fitnessoapp.dto.OrderResponse;
import com.decagon.fitnessoapp.model.product.ORDER_STATUS;
import com.decagon.fitnessoapp.model.product.Order;
import com.decagon.fitnessoapp.model.user.Person;
import com.decagon.fitnessoapp.repository.OrderRepository;
import com.decagon.fitnessoapp.repository.PersonRepository;
import com.decagon.fitnessoapp.repository.ShoppingCartRepository;
import com.decagon.fitnessoapp.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, PersonRepository personRepository,
                            ModelMapper modelMapper, ShoppingCartRepository shoppingCartRepository) {
        this.orderRepository = orderRepository;
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
        this.shoppingCartRepository = shoppingCartRepository;
    }


    @Override
    public List<OrderResponse> getAllOrderByPerson(Authentication authentication) {
        Person person = personRepository.findPersonByUserName(authentication.getName())
                .orElseThrow(()-> new UsernameNotFoundException("Check getOrder at OrderServiceImpl: User Name does not Exist"));
        List<OrderResponse> orders = orderRepository.findAllByCheckOut_Person(person)
                .stream().map(x -> {
                    OrderResponse res = new OrderResponse();
                    res.setOrderStatus(x.getOrderStatus());
                    modelMapper.map(x.getCheckOut(), res);
                    return res;
                })
                .peek(y -> {
                    y.setEmail(person.getEmail());
                    y.setFirstName(person.getFirstName());
                    y.setLastName(person.getLastName());
                    y.setPerson(null);
                })
                .collect(Collectors.toList());

                orders.forEach(y -> y.setCartList(shoppingCartRepository
                        .findAllByUniqueCartId(y.getShoppingCartUniqueId())));
        return orders;
    }



    @Override
    public List<OrderResponse> getAllOrders(Integer pageNo) {
        int pageSize = 10;
        String sortBy = "id";
        Pageable orderPage = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        List<OrderResponse> orderList = orderRepository.findAll(orderPage).getContent()
                .stream().map(x -> {
                    OrderResponse res = new OrderResponse();
                    res.setOrderStatus(x.getOrderStatus());
                    modelMapper.map(x.getCheckOut(), res);
                    return res;
                })
                .peek(y -> {
                    y.setEmail(y.getPerson().getEmail());
                    y.setFirstName(y.getPerson().getFirstName());
                    y.setLastName(y.getPerson().getLastName());
                    y.setPerson(null);
                }).collect(Collectors.toList());

            orderList.forEach(y -> y.setCartList(shoppingCartRepository
                            .findAllByUniqueCartId(y.getShoppingCartUniqueId())));
        return orderList;
    }


    @Override
    public Page<OrderResponse> getOrdersByStatus(ORDER_STATUS status, int pageNo) {
        int pageSize = 10;
        int skipCount = (pageNo - 1) * pageSize;
        // TODO: Make getOrder by status return a string for no order status
        // TODO: Use unique custom exceptions for every implementation

        List<OrderResponse> orderList = orderRepository.findAllByOrderStatus(status)
                .stream().map(x -> {
                    OrderResponse res = new OrderResponse();
                    res.setOrderStatus(x.getOrderStatus());
                    modelMapper.map(x.getCheckOut(), res);
                    return res;
                })
                .peek(y -> {
                    y.setEmail(y.getPerson().getEmail());
                    y.setFirstName(y.getPerson().getFirstName());
                    y.setLastName(y.getPerson().getLastName());
                    y.setPerson(null);
                })
                .collect(Collectors.toList())
                .stream()
                .skip(skipCount)
                .limit(pageSize)
                .collect(Collectors.toList());

        Pageable orderPage = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());

        return new PageImpl<>(orderList, orderPage, orderList.size());
    }

    @Override
    public OrderResponse cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order Id"));
        order.setOrderStatus(ORDER_STATUS.CANCELLED);
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponse.class);
    }
    @Override
    public OrderResponse completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order Id"));
        order.setOrderStatus(ORDER_STATUS.COMPLETED);
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponse.class);
    }
}
