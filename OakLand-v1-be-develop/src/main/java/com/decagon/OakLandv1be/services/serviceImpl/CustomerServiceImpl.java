package com.decagon.OakLandv1be.services.serviceImpl;


import com.decagon.OakLandv1be.config.CloudinaryConfig;
import com.decagon.OakLandv1be.config.tokens.TokenService;
import com.decagon.OakLandv1be.dto.CustomerProfileDto;
import com.decagon.OakLandv1be.dto.EditProfileRequestDto;
import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.dto.SignupRequestDto;
import com.decagon.OakLandv1be.dto.SignupResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.BaseCurrency;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.exceptions.*;
import com.decagon.OakLandv1be.repositries.*;

import com.decagon.OakLandv1be.services.CustomerService;
import com.decagon.OakLandv1be.services.JavaMailService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.Mapper;
import com.decagon.OakLandv1be.utils.ResponseManager;
import com.decagon.OakLandv1be.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.decagon.OakLandv1be.enums.TokenStatus.ACTIVE;
import static com.decagon.OakLandv1be.enums.TokenStatus.EXPIRED;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailService javaMailService;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    private final ResponseManager responseManager;
    private final ProductRepository productRepository;
    private final HttpServletRequest request;



    @Override
    @Transactional
    public SignupResponseDto saveCustomer(SignupRequestDto signupRequestDto) throws AlreadyExistsException, IOException {
        boolean emailExist = personRepository.existsByEmail(signupRequestDto.getEmail());
        if (emailExist)
            throw new AlreadyExistsException("This Email address already exists");

        Customer customer = new Customer();

        Person person = new Person();
        BeanUtils.copyProperties(signupRequestDto, person);
        person.setCustomer(customer);
        person.setRole(Role.CUSTOMER);
        person.setVerificationStatus(false);
        person.setGender(Gender.valueOf(signupRequestDto.getGender().toUpperCase()));
        person.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        Wallet wallet = Wallet.builder()
                .baseCurrency(BaseCurrency.NAIRA)
                .accountBalance(BigDecimal.ZERO)
                .customer(customer)
                .build();

        customer.setWallet(wallet);
        customer.setPerson(person);

        personRepository.save(person);
        walletRepository.save(wallet);
        customerRepository.save(customer);

        String validToken = tokenService.generateVerificationToken(signupRequestDto.getEmail());
        Token token = new Token();
        token.setToken(validToken);
        token.setTokenStatus(ACTIVE);
        token.setPerson(person);
        tokenRepository.save(token);

        String subject = "Verify email address";

        String message =

                "<html> " +
                    "<body>" +
                        "<h5>Hi " + person.getFirstName() + " " + person.getLastName() +",<h5> <br>" +
                        "<p>Thank you for your interest in joining Oakland." +
                        "To complete your registration, we need you to verify your email address \n" +
                        "<br><a href=[[TOKEN_URL]]>CLICK TO VERIFY</a><p>" +
                    "</body> " +
                "</html>";


        String url = "http://" + request.getServerName() + ":3000" + "/verifyRegistration?token=" + validToken;

        message = message.replace("[[TOKEN_URL]]", url);

        javaMailService.sendMailAlt(signupRequestDto.getEmail(), subject, message);

        // use the user object to create UserResponseDto Object
        SignupResponseDto signupResponseDto = new SignupResponseDto();
        BeanUtils.copyProperties(person, signupResponseDto);
        return signupResponseDto;
    }

    @Override
    public ResponseEntity<ApiResponse> resendVerificationToken(String email) throws EmailNotFoundException, IOException {
        boolean emailExists = personRepository.existsByEmail(email);
        if (!emailExists) {
            throw new EmailNotFoundException("Email not found");
        }
        Person person = personRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with this email"));

        if (!person.getVerificationStatus()) {
            String validToken = tokenService.generateVerificationToken(email);
            Token token = new Token();
            token.setToken(validToken);
            token.setTokenStatus(ACTIVE);
            token.setPerson(person);
            tokenRepository.save(token);

            String subject = "Verify email address";

            String message =

                    "<html> " +
                            "<body>" +
                            "<h5>Hi " + person.getFirstName() + " " + person.getLastName() +",<h5> <br>" +
                            "<p>Thank you for your interest in joining Oakland." +
                            "To complete your registration, we need you to verify your email address \n" +
                            "<br><a href=[[TOKEN_URL]]>CLICK TO VERIFY AGAIN</a><p>" +
                            "</body> " +
                            "</html>";


            String url = "http://" + request.getServerName() + ":3000" + "/verifyRegistration?token=" + validToken;

            message = message.replace("[[TOKEN_URL]]", url);

            javaMailService.sendMailAlt(email, subject, message);

        }else
            throw new AlreadyExistsException("User is already verified");
        return new ResponseEntity<>(responseManager.success("Verification token resent. Check your email"), HttpStatus.OK);
    }



    @Override
    public ResponseEntity<ApiResponse> verifyRegistration(String token) {

        Token verificationToken = tokenRepository.findByToken(token).orElseThrow(
                () -> new InvalidTokenException("Token Not Found"));

        if (verificationToken.getTokenStatus().equals(EXPIRED))
            throw new InvalidTokenException("Token already used");

        verificationToken.getPerson().setVerificationStatus(true);
        verificationToken.setTokenStatus(EXPIRED);
        tokenRepository.save(verificationToken);
        return new ResponseEntity<>(responseManager.success("Account verification successful"), HttpStatus.OK);

    }

    @Override
    public void editProfile(EditProfileRequestDto editProfileRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken))
            throw new ResourceNotFoundException("Please Login");
        String email = authentication.getName();
        Person customer = personRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        customer.setFirstName(editProfileRequestDto.getFirstName());
        customer.setLastName(editProfileRequestDto.getLastName());
        customer.setEmail(editProfileRequestDto.getEmail());
        customer.setPhone(editProfileRequestDto.getPhone());
        customer.setDate_of_birth(editProfileRequestDto.getDate_of_birth());
        customer.setAddress(editProfileRequestDto.getAddress());

        personRepository.save(customer);
    }

    @Override
    public void addProductToFavorites(Long pid) {
        Product product = productRepository.findById(pid).
                orElseThrow(() -> new ProductNotFoundException("This product was not found"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken))
            throw new ResourceNotFoundException("Please Login");
        String email = authentication.getName();
        Person person = personRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Set<Product> favorites = person.getCustomer().getFavorites();
        if (favorites.contains(product)) {
            throw new AlreadyExistsException("This product is already in favorites");
        }

        favorites.add(product);
        person.getCustomer().setFavorites(favorites);
        customerRepository.save(person.getCustomer());
    }


    @Override
    public void removeProductFromFavorites(Long pid) {
        Product product = productRepository.findById(pid).
                orElseThrow(() -> new ProductNotFoundException("This product was not found"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken))
            throw new ResourceNotFoundException("Please Login");
        String email = authentication.getName();
        Person person = personRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Set<Product> favorites = person.getCustomer().getFavorites();
        if (favorites.contains(product)) {
            throw new AlreadyExistsException("This product is already in favorites");
        }
        favorites.remove(product);
        person.getCustomer().setFavorites(favorites);
        customerRepository.save(person.getCustomer());
    }

    @Override
    public Customer getCurrentlyLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!(authentication instanceof AnonymousAuthenticationToken))
//            throw new UnauthorizedUserException("Login to carry out this operation");
        Person loggedInUser = personRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("No user with this email"));
        return loggedInUser.getCustomer();
    }

    @Override
    public ProductCustResponseDto viewASingleFavorite(Long product_id) {
        String email = UserUtil.extractEmailFromPrincipal()
                .orElseThrow(() -> new UserNotFoundException("user does not exist"));
        Customer customer = customerRepository.findByPersonEmail(email).orElseThrow
                (()-> new UserNotFoundException("user not found"));

        Set<Product> favorites = customer.getFavorites();
        Product product = productRepository.findById(product_id).orElseThrow(()-> new ProductNotFoundException("product"+ product_id+"not available"));
        if(!favorites.contains(product)){
            throw new ResourceNotFoundException("Products not found in the favorites");
        }
        return ProductCustResponseDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .color(product.getColor())
                .description(product.getDescription())
                .build();
    }

    @Override
    public Page<ProductCustResponseDto> viewFavouritesByPagination(Integer pageNo, Integer pageSize, String sortBy) {
        String email = UserUtil.extractEmailFromPrincipal()
                .orElseThrow(() -> new UserNotFoundException("user does not exist"));
        Customer customer = customerRepository.findByPersonEmail(email).orElseThrow
                (() -> new UserNotFoundException("user not found"));
        List<Product> products = new ArrayList<>(customer.getFavorites());

        List<ProductCustResponseDto> productCustResponseDtos = new ArrayList<>();

        products.forEach(product -> {
            productCustResponseDtos.add(
                    ProductCustResponseDto.builder()
                            .name(product.getName())  // make sure this is the correct method call
                            .price(product.getPrice())
                            .imageUrl(product.getImageUrl())
                            .color(product.getColor())
                            .description(product.getDescription())
                            .build()
            );
        });
        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);

        int minimum = pageNo*pageSize;
        int max = Math.min(pageSize * (pageNo + 1), products.size());

        Page<ProductCustResponseDto> page = new PageImpl<>
                (productCustResponseDtos.subList(minimum, max), pageable,
                        productCustResponseDtos.size());

        return page;
    }

    public CustomerProfileDto viewProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = authentication.getName();
        Person loggedInCustomer = personRepository.findByEmail(loggedInUserEmail)
                .orElseThrow(() -> new UserNotFoundException("No user with this email"));
        return CustomerProfileDto.builder()
                .firstName(loggedInCustomer.getFirstName())
                .lastName(loggedInCustomer.getLastName())
                .email(loggedInCustomer.getEmail())
                .gender(loggedInCustomer.getGender())
                .date_of_birth(loggedInCustomer.getDate_of_birth())
                .phone(loggedInCustomer.getPhone())
                .verificationStatus(loggedInCustomer.getVerificationStatus())
                .address(loggedInCustomer.getAddress())
                .build();

    }

    @Override
    public Page<CustomerProfileDto> viewAllCustomersProfileWithPaginationSorting(Integer pageNo, Integer pageSize, String sortBy) {
        List<Person> personPage = personRepository.findAll();
        List<CustomerProfileDto> customerProfileDtos = personPage.stream()
                .map(person -> CustomerProfileDto.builder()
                        .id(person.getId())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .email(person.getEmail())
                        .gender(person.getGender())
                        .date_of_birth(person.getDate_of_birth())
                        .phone(person.getPhone())
                        .verificationStatus(person.getVerificationStatus())
                        .address(person.getAddress())
                        .build())
                .collect(Collectors.toList());

        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);
        int max = Math.min(pageSize * (pageNo + 1), customerProfileDtos.size());
        return new PageImpl<> (customerProfileDtos.subList(pageNo*pageSize, max), pageable, customerProfileDtos.size());
    }
}

