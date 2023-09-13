package com.decagon.adire.controller;

import com.decagon.adire.dto.request.CustomerDTO;
import com.decagon.adire.dto.response.AppResponse;
import com.decagon.adire.dto.response.CustomerResponseDTO;
import com.decagon.adire.service.CustomerService;
import com.decagon.adire.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.decagon.adire.dto.response.PaginationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping(path = "/register")
    public ResponseEntity<Object> registerCustomer(@RequestBody @Valid final CustomerDTO customerDTO, HttpServletRequest request){
        log.info("controller register: register user :: [{}] ::", customerDTO.getEmail());
        CustomerResponseDTO response = customerService.createCustomer(customerDTO, request);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/adire/customer/register").toUriString());
        return ResponseEntity.created(uri).body(AppResponse.buildSuccess(response));
    }

    @PutMapping("/editCustomer/{Id}")
    public ResponseEntity<?> editCustomerById(@PathVariable("Id") String Id, @RequestBody CustomerDTO customerDto){
        return ResponseEntity.ok().body(AppResponse.buildSuccessTxn(customerService.editCustomerById(Id, customerDto)));
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<Object> getAllCustomers(
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            HttpServletRequest request
    ){


        PaginationResponse response = customerService.getAllCustomers(request, searchTerm, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok().body(AppResponse.buildSuccessTxn(response));
    }

    @DeleteMapping("/deleteCustomer/{Id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String Id){
        customerService.deleteCustomer(Id);
        return ResponseEntity.ok().body(AppResponse.buildSuccessTxn("Customer deleted successfully"));
    }

    @GetMapping("/getCustomer/{Id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("Id") String Id){
        return ResponseEntity.ok().body(AppResponse.buildSuccessTxn(customerService.getCustomerById(Id)));
    }


@GetMapping("/countCustomers")
    public ResponseEntity<?> countCustomers(HttpServletRequest request){
        return  new ResponseEntity<>(customerService.countCustomers(request),HttpStatus.OK);
    }

}


