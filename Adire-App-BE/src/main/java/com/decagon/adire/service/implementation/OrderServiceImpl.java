package com.decagon.adire.service.implementation;
import com.decagon.adire.dto.request.MeasurementDto;
import com.decagon.adire.dto.response.*;
import com.decagon.adire.entity.Designer;
import com.decagon.adire.dto.request.OrderDTO;
import com.decagon.adire.entity.Customer;
import com.decagon.adire.entity.Measurement;
import com.decagon.adire.entity.Order;
import com.decagon.adire.enums.MaterialType;
import com.decagon.adire.enums.OrderStatus;
import com.decagon.adire.exception.NotFoundException;
import com.decagon.adire.repository.DesignerRepository;
import com.decagon.adire.repository.MeasurementRepository;
import com.decagon.adire.security.jwt.TokenProvider;
import com.decagon.adire.utils.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import com.decagon.adire.repository.CustomerRepository;
import com.decagon.adire.repository.OrderRepository;
import com.decagon.adire.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final DesignerRepository designerRepository;
    private final MeasurementRepository measurementRepository;

    private ModelMapper modelMapper = new ModelMapper();


    @Override
    public List<Report> getMonthlyReport(HttpServletRequest request, Principal principal) {

        // get email from pricipal and use it to find the designer
        String email = principal.getName();
        Designer designer = designerRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Designer not found"));

        // Get the first day of the year
        LocalDate firstDayOfYear = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
        LocalDateTime start = firstDayOfYear.atStartOfDay();

        // Get the last day of the year
        LocalDate lastDayOfYear = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
        LocalDateTime end = lastDayOfYear.atTime(23, 59, 59);

        List<Order> orders = orderRepository.findByCreatedDateBetween(Timestamp.valueOf(start), Timestamp.valueOf(end));

        return getReportForEachMonth(request, orders);
    }

    @Override
    public PaginationResponse<OrderDTO> fetchAllOrders(HttpServletRequest request, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Designer designer = getDesignerProfile(request);
        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Order> orders = orderRepository.findAll(pageable);
        // get content for page object
        List<Order> listOfOrder = orders.getContent().stream()
                .filter(order -> order.getDesigner().equals(designer))
                .collect(Collectors.toList());
        List<OrderResponse> content = listOfOrder.stream().map(this::mapToResponse).collect(Collectors.toList());

        PaginationResponse orderResponse = new PaginationResponse();
        orderResponse.setContent(content);
        orderResponse.setPageNo(orders.getNumber());
        orderResponse.setPageSize(orders.getSize());
        orderResponse.setTotalElements(orders.getTotalElements());
        orderResponse.setTotalPages(orders.getTotalPages());
        orderResponse.setLast(orders.isLast());
        return orderResponse;
    }


    @Override
    public OrderDTO findOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order with the id {" + id + "does not exist"));
        System.out.println(order);
        return mapToDTO(order);
    }


    @Override
    @Transactional
    public OrderResponse createOrder(OrderDTO requestDTO, HttpServletRequest request) {
        Customer customer = customerRepository.findByEmail(requestDTO.getCustomerEmail())
                .orElseThrow(() -> new NotFoundException("Customer Email doesn't exist"));

        List<Measurement> measurementList = requestDTO.getMeasurementList()
                .stream()
                .map(measurement -> {
                    Measurement measurementObj = new Measurement();
                    measurementObj.setMeasurementType(measurement.getType());
                    measurementObj.setValue(measurement.getValue());
                    return measurementObj;
                }).collect(Collectors.toList());

        var savedMeasurementList = measurementRepository.saveAll(measurementList);
        List<MeasurementDto> measurementDtos = new ArrayList<>();
        for (Measurement measurement : measurementList) {
            MeasurementDto measurementDto = mapToMeasurementDto(measurement);
            measurementDtos.add(measurementDto);
        }

        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        newOrder.setDesigner(getDesignerProfile(request));
        newOrder.setMeasurementList(measurementList);
        newOrder.setMaterialType(MaterialType.valueOf(requestDTO.getMaterialType()));
        newOrder.setDesignImageUrl(requestDTO.getDesignImageUrl());
        newOrder.setDuration(requestDTO.getDuration());
        newOrder.setOrderPrice(requestDTO.getOrderPrice());
        newOrder.setDueDate(requestDTO.getDueDate());
        newOrder.setOrderStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(newOrder);

        return OrderResponse.builder()
                .id(savedOrder.getId())
                .customerEmail(customer.getEmail())
                .materialType(String.valueOf(newOrder.getMaterialType()))
                .designImageUrl(savedOrder.getDesignImageUrl())
                .measurementList(measurementDtos)
                .duration(savedOrder.getDuration())
                .orderPrice(savedOrder.getOrderPrice())
                .dueDate(savedOrder.getDueDate())
                .orderStatus(String.valueOf(savedOrder.getOrderStatus()))
                .build();

    }


    @Override
    public RevenueReportResponse getAllRevenueReport(HttpServletRequest request) {
        Designer designer = getDesignerProfile(request);
        List<Order> orderList = orderRepository.findAll().stream()
                .filter(order -> order.getDesigner().equals(designer))
                .collect(Collectors.toList());

        Double amountOfSales = 0.0;

        if (orderList.size() == 0) {
            return RevenueReportResponse.builder()
                    .numberOfOrders(orderList.size())
                    .totalRevenue(amountOfSales)
                    .build();
        }


        for (Order order : orderList) {
            amountOfSales += order.getOrderPrice();
        }

        return RevenueReportResponse.builder()
                .numberOfOrders(orderList.size())
                .totalRevenue(amountOfSales)
                .build();
    }

    private List<Report> getReportForEachMonth(HttpServletRequest request, List<Order> orderList) {

        Designer designer = getDesignerProfile(request);
        List<Order> designerOrders = orderList.stream()
                .filter(order -> order.getDesigner().equals(designer))
                .collect(Collectors.toList());
        HashMap<String, Double> reportMap = new HashMap<>();

        //populate the map with months keys
        for (Month month : Month.values()) {
            String monthName = month.name();
            reportMap.put(monthName, 0.0);
        }

        // iterate through orders and group them by month
        for (Order order : designerOrders) {
            LocalDateTime createdDate = order.getCreatedDate().toLocalDateTime();
            String monthString = createdDate.getMonth().toString();

            // check if report for this month already exists, create new report if not
            if (!reportMap.containsKey(monthString)) {
                // initialize the report value to 0.0 for months without orders
                reportMap.put(monthString, 0.0);
            }

            // add income to the report value for this month
            Double income = reportMap.get(monthString);
            income += order.getOrderPrice();
            reportMap.put(monthString, income);
        }

        // create a list of Report objects for each month
        List<Report> reportList = new ArrayList<>();

        for (String month : reportMap.keySet()) {
            Double income = reportMap.get(month);
            Report report = new Report(month, income);
            reportList.add(report);
        }
        //sort the list by the natural sequence of months
        reportList.sort(Comparator.comparing(m -> Month.valueOf(m.getMonth())));
        return reportList;
    }


    @Override
    public int countOrders(HttpServletRequest request) {
        Designer designer = getDesignerProfile(request);
        List<Order> orders = orderRepository.findAll().stream()
                .filter(order -> order.getDesigner().equals(designer))
                .collect(Collectors.toList());
        log.info("Found Orders");
        if (orders.isEmpty()) {
            return 0;
        }
        log.info("Count orders");
        return orders.size();
    }


    @Override
    public OrderResponse updateOrder(OrderDTO orderDto, String id) {
        log.info("service updateOrder - updating order with id :: [{}] ::", id);
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new NotFoundException("Order with the id {" + id + "does not exist");
        } else {
            Customer customer = customerRepository.findByEmail(orderDto.getCustomerEmail())
                    .orElseThrow(() -> new NotFoundException("Customer Email doesn't exist"));
            List<Measurement> measurementList = orderDto.getMeasurementList().stream().map(this::mapToMeasurement).collect(Collectors.toList());

            var savedMeasurementList = measurementRepository.saveAll(measurementList);
            order.setCustomer(customer);
            order.setMeasurementList(savedMeasurementList);
            order.setMaterialType(MaterialType.valueOf(orderDto.getMaterialType()));
            order.setDesignImageUrl(orderDto.getDesignImageUrl());
            order.setDuration(orderDto.getDuration());
            order.setOrderPrice(orderDto.getOrderPrice());
            order.setDueDate(orderDto.getDueDate());
            order.setOrderStatus(OrderStatus.valueOf(orderDto.getOrderStatus()));
            Order savedOrder = orderRepository.save(order);
            return mapToResponse(savedOrder);
        }
    }

    @Override
    public void deleteOrder(String id) {
        var order = orderRepository.findById(id);
        if(order.isEmpty()){
            throw new NotFoundException("Order not found");
        }
        orderRepository.deleteById(id);
    }

    //    ;;;;;;;----------- HELPER METHODS ---------------;;;;;;;;;;;;;
    private OrderDTO mapToDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
    private Measurement mapToMeasurement(MeasurementDto measurementDto) {
        return modelMapper.map(measurementDto, Measurement.class);
    }
    private OrderResponse mapToResponse(Order order) {
        return modelMapper.map(order, OrderResponse.class);
    }
    private MeasurementDto mapToMeasurementDto(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDto.class);
    }

    private Designer getDesignerProfile(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION).substring(SecurityConstants.TOKEN_PREFIX.length());
        String userName = TokenProvider.getUsernameFromToken(token);
        log.info("User name: {}", userName);
        Designer user = designerRepository.findByEmail(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user;
    }

}



