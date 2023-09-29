package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.config.tokens.TokenService;
import com.decagon.OakLandv1be.dto.NewProductRequestDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.dto.*;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.exceptions.ProductNotFoundException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;

import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.PickupCenter;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.exceptions.*;


import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import com.decagon.OakLandv1be.repositries.*;
import com.decagon.OakLandv1be.services.AdminService;
import com.decagon.OakLandv1be.services.JavaMailService;
import com.decagon.OakLandv1be.utils.ApiResponse;

import com.decagon.OakLandv1be.utils.UserUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static com.decagon.OakLandv1be.utils.UserUtil.extractEmailFromPrincipal;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.UUID;
import static com.decagon.OakLandv1be.enums.TokenStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final ProductRepository productRepository;
    private final PersonRepository personRepository;
    private final CustomerRepository customerRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final PickupRepository pickupRepository;

    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    private final JavaMailService javaMailService;
    private final HttpServletRequest request;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ProductResponseDto fetchASingleProduct(Long product_id) {
        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ProductNotFoundException("This product was not found"));
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(UserUtil.formatToLocale(BigDecimal.valueOf(product.getPrice())))
                .imageUrl(product.getImageUrl())
                .availableQty(product.getAvailableQty())
                .subCategory(product.getSubCategory())
                .color(product.getColor())
                .description(product.getDescription())
                .build();
    }

    @Override
    public ApiResponse<ProductResponseDto> addNewProduct(NewProductRequestDto newProductRequestDto) {
        if (productRepository.existsByName(newProductRequestDto.getName()))
            throw new AlreadyExistsException("Product with name '" +
                    newProductRequestDto.getName() + "' already exists");

        SubCategory subCategory = subCategoryRepository
                .findByName(newProductRequestDto.getSubCategory())
                .orElseThrow(() ->
                        new ProductNotFoundException("SubCategory does not exist"));

//        String imageUrl = newProductRequestDto.getProductImage();

        Product product = Product.builder()
                .name(newProductRequestDto.getName())
                .price(newProductRequestDto.getPrice())
                .imageUrl(newProductRequestDto.getImageUrl())
                .availableQty(newProductRequestDto.getAvailableQty())
                .subCategory(subCategory)
                .color(newProductRequestDto.getColor())
                .description(newProductRequestDto.getDescription())
                .build();

        Product newProduct = productRepository.save(product);
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .name(newProduct.getName())
                .price(UserUtil.formatToLocale(BigDecimal.valueOf(newProductRequestDto.getPrice())))
                .availableQty(newProduct.getAvailableQty())
                .subCategory(SubCategory.builder()
                        .name(subCategory.getName())
                        .category(subCategory.getCategory())
                        .build())
                .imageUrl(newProduct.getImageUrl())
                .color(newProduct.getColor())
                .description(newProduct.getDescription())
                .build();
        return new ApiResponse<>("Product Added", productResponseDto, HttpStatus.CREATED);
    }

    @Override
    public void deleteProduct(Long product_id) {
        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);

    }

    @Override
    public String deactivateUser(Long customerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken))
            throw new ResourceNotFoundException("Please Login");
        String email = authentication.getName();
        personRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        Person customer = personRepository.findById(customerId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean isActive = !customer.isActive();
        customer.setActive(isActive);
        personRepository.save(customer);
        return isActive ? "Account Re-activated" : "Account deactivated";
    }

    @Override
    public ApiResponse<Product> updateProduct(Long productId, UpdateProductDto updateproductDto) {
        Product product = productRepository.findById(productId).
                orElseThrow(() ->
                        new ProductNotFoundException("Product does not exist"));

        SubCategory subCategory = subCategoryRepository
                .findByName(updateproductDto.getSubCategory())
                .orElseThrow(() ->
                        new ProductNotFoundException("SubCategory does not exist"));


        product.setName(updateproductDto.getName());
        product.setPrice(updateproductDto.getPrice());
        product.setImageUrl(updateproductDto.getImageUrl());
        product.setAvailableQty(updateproductDto.getAvailableQty());
        product.setSubCategory(subCategory);
        product.setColor(updateproductDto.getColor());
        product.setDescription(updateproductDto.getDescription());

        Product updatedProduct = productRepository.save(product);
        return new ApiResponse<>("product updated", true, updatedProduct);
    }

    @Override
    public Set<AddressResponseDto> viewAllCustomerAddress(Long customerId){
        String email = extractEmailFromPrincipal().get();
        personRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(("Incorrect credentials or not found")));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new ResourceNotFoundException("Not Found"));

        Set<Address> addressList = customer.getAddressBook();
        Set<AddressResponseDto> addressResponse = new HashSet<>();
        addressList
                .forEach(address -> {
                    AddressResponseDto response = AddressResponseDto.builder()
                            .id(address.getId())
                            .fullName(address.getFullName())
                            .street(address.getStreet())
                            .state(address.getState())
                            .country(address.getCountry())
                            .phone(address.getPhone())
                            .isDefault(address.getIsDefault())
                            .build();
                    addressResponse.add(response);
                });
        return addressResponse;
    }

    @Transactional
    public AdminResponseDto createAdmin(AdminRequestDto adminRequestDto) throws IOException {
        boolean emailExist = personRepository.existsByEmail(adminRequestDto.getEmail());
        if (emailExist)
            throw new AlreadyExistsException("This Email address already exists");

        Admin admin = new Admin();
        String pwd = UUID.randomUUID().toString();
        Person person = Person.builder()
                .firstName(adminRequestDto.getFirstName())
                .lastName(adminRequestDto.getLastName())
                .email(adminRequestDto.getEmail())
                .password(passwordEncoder.encode(pwd))
                .phone(adminRequestDto.getPhoneNumber())
                .gender(Gender.valueOf(adminRequestDto.getGender().toUpperCase()))
                .address(adminRequestDto.getAddress())
                .date_of_birth(adminRequestDto.getDate_of_birth())
                .role(Role.ADMIN)
                .isActive(true)
                .verificationStatus(false)
                .build();
        personRepository.save(person);
        admin.setPerson(person);
        adminRepository.save(admin);

        AdminResponseDto adminResponseDto = new AdminResponseDto();
        BeanUtils.copyProperties(person, adminResponseDto);

        String validToken = tokenService.generateVerificationToken(adminRequestDto.getEmail());
        Token token = new Token();
        token.setToken(validToken);
        token.setTokenStatus(ACTIVE);
        token.setPerson(person);
        tokenRepository.save(token);


//        System.out.println("Password :" +person.getPassword());

        javaMailService.sendMail(adminRequestDto.getEmail(),
                "Verify your email address",
                "Hi " + person.getFirstName() + " " + person.getLastName() + ",Use this password to Login and ensure you reset the password as you login ."
                        + "Password  is :" + "<<-- " + pwd + "  -->>" +
                        "To complete your registration, we need you to verify your email address \n" + "http://" + request.getServerName() + ":3000" + "/verifyRegistration?token=" + validToken);
        return adminResponseDto;
    }


    public PickupCenter updatePickupCenter(Long pickupCenterId, UpdatePickUpCenterDto request) {
        PickupCenter center = pickupRepository.findById(pickupCenterId).
                orElseThrow(() -> new PickupCenterNotFoundException("Pickup Center does not exist"));

        center.setName(request.getName());
        center.setAddress(request.getAddress());
        center.setEmail(request.getEmail());
        center.setPhone(request.getPhone());

        PickupCenter pickupCenter = pickupRepository.save(center);
        return pickupCenter;
    }

}

