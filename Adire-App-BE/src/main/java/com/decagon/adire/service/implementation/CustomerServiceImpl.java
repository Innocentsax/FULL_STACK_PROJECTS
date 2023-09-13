package com.decagon.adire.service.implementation;

import com.decagon.adire.dto.request.CustomerDTO;
import com.decagon.adire.dto.response.CustomerResponseDTO;
import com.decagon.adire.dto.response.DesignerResponseDTO;
import com.decagon.adire.dto.response.PaginationResponse;
import com.decagon.adire.entity.Customer;
import com.decagon.adire.entity.Designer;
import com.decagon.adire.entity.Order;
import com.decagon.adire.exception.BadRequestException;
import com.decagon.adire.repository.CustomerRepository;
import com.decagon.adire.exception.NotFoundException;
import com.decagon.adire.repository.DesignerRepository;
import com.decagon.adire.security.jwt.TokenProvider;
import com.decagon.adire.service.CustomerService;
import com.decagon.adire.utils.SecurityConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final DesignerRepository designerRepository;

    private final CustomerRepository customerRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public CustomerResponseDTO createCustomer(CustomerDTO requestDTO, HttpServletRequest request) {
        validateEmail(requestDTO);
        log.info("service:: about setting");
        Customer newCustomer= new Customer();
        newCustomer.setDesigner(getDesignerProfile(request));
        newCustomer.setFirstName(requestDTO.getFirstName());
        newCustomer.setLastName(requestDTO.getLastName());
        newCustomer.setEmail(requestDTO.getEmail());
        newCustomer.setAddress(requestDTO.getAddress());
        newCustomer.setPhoneNumber(requestDTO.getPhoneNumber());
        Customer savedCustomer= customerRepository.save(newCustomer);
        //Map saved customer to customer response DTO
        CustomerResponseDTO customerResponseDto = mapToResponse(savedCustomer);
        return customerResponseDto;
    }

    @Override
    public CustomerResponseDTO editCustomerById(String Id, CustomerDTO customerDto) {
        Customer customer1 = customerRepository.findById(Id)
                .orElseThrow(() -> new NotFoundException("Customer with Id " + Id + " not found"));

        customer1.setFirstName(customerDto.getFirstName());
        customer1.setLastName(customerDto.getLastName());
        customer1.setEmail(customerDto.getEmail());
        customer1.setPhoneNumber(customerDto.getPhoneNumber());
        customer1.setAddress(customerDto.getAddress());

        Customer updatedCustomer = customerRepository.save(customer1);
        return mapToResponse(updatedCustomer);
    }

    @Override
    public CustomerResponseDTO getCustomerById(String Id) {
        Customer foundCustomer = customerRepository.findById(Id)
                .orElseThrow(() -> new NotFoundException("Customer with Id " + Id + " not found"));
        return mapToResponse(foundCustomer);

    }

    @Override
    public CustomerResponseDTO getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email).orElse(null);
        if(customer == null){
            throw new NotFoundException("Customer with email " + email + " not found");
        }
        return mapToResponse(customer);
    }


    @Override
    public void deleteCustomer(String Id) {
        var customer = customerRepository.findById(Id);
        if(customer.isEmpty()){
            throw new NotFoundException("Customer not found");
        }
        customerRepository.deleteById(Id);
    }

     @Override
    public int countCustomers(HttpServletRequest request) {
            Designer designer = getDesignerProfile(request);
         List<Customer> customers = customerRepository.findAll().stream()
                 .filter(order -> order.getDesigner().equals(designer))
                 .collect(Collectors.toList());;
         log.info("found customers");
//         if (customers.isEmpty()) {
//             return 0;
//         }
         return customers.size();
    }


    @Override
    public PaginationResponse<CustomerResponseDTO> getAllCustomers(HttpServletRequest request, String searchTerm, int pageNo, int pageSize, String sortBy, String sortDir) {
        Designer designer = getDesignerProfile(request);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Customer> customers;

        if (searchTerm != null) {
            customers = customerRepository.findByEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchTerm, searchTerm, searchTerm, pageable);
        } else {
            customers = customerRepository.findAll(pageable);
        }

//        Page<Customer> customers = customerRepository.findAll(pageable);

        // get content for page object
        List<Customer> listOfCustomers = customers.getContent().stream()
                .filter(customer -> customer.getDesigner().equals(designer))
                .collect(Collectors.toList());
        List<CustomerResponseDTO> content= listOfCustomers.stream().map(this::mapToResponse).collect(Collectors.toList());

        PaginationResponse customerResponse = new PaginationResponse();
        customerResponse.setContent(content);
        customerResponse.setPageNo(customers.getNumber());
        customerResponse.setPageSize(customers.getSize());
        customerResponse.setTotalElements(customers.getTotalElements());
        customerResponse.setTotalPages(customers.getTotalPages());
        customerResponse.setLast(customers.isLast());
        return customerResponse;
    }




//    ------------------HELPER METHODS ------------------
    private CustomerResponseDTO mapToResponse(Customer customer){
    return modelMapper.map(customer, CustomerResponseDTO.class);
    }
    private CustomerDTO mapToDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }
    private void validateEmail(CustomerDTO requestDTO) {
        log.info("Check if user already exist");
        Customer existing= customerRepository.findByEmail(requestDTO.getEmail()).orElse(null);
        if (existing!=null){
            throw new BadRequestException("Email already exist");
        }
    }

    @Override
    public Designer getDesignerProfile(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION).substring(SecurityConstants.TOKEN_PREFIX.length());
        String userName = TokenProvider.getUsernameFromToken(token);
        log.info("User name: {}", userName);
        Designer user = designerRepository.findByEmail(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user;
    }
}
