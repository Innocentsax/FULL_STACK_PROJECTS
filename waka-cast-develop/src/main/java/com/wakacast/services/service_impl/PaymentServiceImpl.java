package com.wakacast.services.service_impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.SubscriptionDTO;
import com.wakacast.dto.payment_dtos.initial_transaction.InitializeTransactionRequestDTO;
import com.wakacast.dto.payment_dtos.initial_transaction.InitializeTransactionResponseDTO;
import com.wakacast.dto.payment_dtos.verify_transaction.VerifyTransactionResponse;
import com.wakacast.enums.SubscriptionPlan;
import com.wakacast.enums.SubscriptionStatus;
import com.wakacast.enums.UserType;
import com.wakacast.exceptions.BadRequestException;
import com.wakacast.exceptions.UserWithEmailNotFound;
import com.wakacast.models.Subscription;
import com.wakacast.models.User;
import com.wakacast.repositories.SubscriptionRepository;
import com.wakacast.repositories.UserRepository;
import com.wakacast.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static com.wakacast.global_constants.Constants.BASE_URL;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String AUTHORIZATION = "Authorization";

    @Value("${payStack.secret.key}")
    private String key;

    @Override
    public InitializeTransactionResponseDTO initializeTransaction(SubscriptionDTO subscriptionDTO, HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        String token = request.getHeader(AUTHORIZATION).substring(7);
        User user = userRepository.findUserByEmail(email).orElseThrow(()->
                new UserWithEmailNotFound("User not found"));

        Subscription subscription = new Subscription();

        InitializeTransactionRequestDTO transactionRequestDTO = new InitializeTransactionRequestDTO();
        transactionRequestDTO.setEmail(email);
        if (subscriptionDTO.getSubscriptionPlan().equals(SubscriptionPlan.MONTHLY)) {
            transactionRequestDTO.setPlan("PLN_44tcursqb7tdk17");
            subscription.setSubscriptionPlan(SubscriptionPlan.MONTHLY);
        } else if (subscriptionDTO.getSubscriptionPlan().equals(SubscriptionPlan.ANNUALLY)) {
            transactionRequestDTO.setPlan("PLN_3p906ppvtgvtvig");
            subscription.setSubscriptionPlan(SubscriptionPlan.ANNUALLY);
        } else throw new BadRequestException("Please select a subscription plan");
        transactionRequestDTO.setCallbackUrl(BASE_URL +"api/payment/confirm/" + token);

        ResponseEntity<InitializeTransactionResponseDTO> response = initializeTransaction(
                transactionRequestDTO);
        subscription.setStatus(SubscriptionStatus.INACTIVE);
        subscription.setReference(Objects.requireNonNull(response.getBody()).getData().getReference());
        subscription.setSubscriber(user);
        subscriptionRepository.save(subscription);
        return response.getBody();
    }

    private ResponseEntity<InitializeTransactionResponseDTO> initializeTransaction(
            InitializeTransactionRequestDTO transactionRequestDTO
    ) {
        String url = "https://api.paystack.co/transaction/initialize";
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTHORIZATION, "Bearer " + key);
        HttpEntity<InitializeTransactionRequestDTO> entity = new HttpEntity<>(
                transactionRequestDTO, headers);
        return restTemplate.postForEntity(
                url, entity, InitializeTransactionResponseDTO.class);
    }

    @Override
    public String confirmPayment(String token, String reference) throws IOException {
        String email = jwtTokenUtil.getUserEmailFromToken(token);
        Subscription subscription = subscriptionRepository
                .findSubscriptionBySubscriberEmailAndReference(email, reference).orElseThrow(()->
                        new BadRequestException("Invalid transaction"));

        StringBuilder result = verifyPayment(reference);
        VerifyTransactionResponse verifyTransactionResponse;

        ObjectMapper mapper = new ObjectMapper();

        try {
            verifyTransactionResponse = mapper.readValue(result.toString(), VerifyTransactionResponse.class);
            User user = subscription.getSubscriber();

            if (verifyTransactionResponse.getData().getStatus().equals("success")) {
                subscription.setStatus(SubscriptionStatus.ACTIVE);
                if (user.getUserType().equals(UserType.UNSUBSCRIBED)) {
                    user.setUserType(UserType.SUBSCRIBED);
                    userRepository.save(user);
                }
                subscriptionRepository.save(subscription);
                return "Payment successfully made";
            } else {
                if (user.getUserType().equals(UserType.SUBSCRIBED)) {
                    user.setUserType(UserType.UNSUBSCRIBED);
                    subscription.setStatus(SubscriptionStatus.INACTIVE);
                    userRepository.save(user);
                    subscriptionRepository.save(subscription);
                    return "Payment not successful";
                }
            }

        } catch (JsonProcessingException e) {
            throw new BadRequestException("You've already made payment or An error occurred while verifying payment ");
        }
        return "verifyTransactionResponse.getData().getStatus()";
    }

    private StringBuilder verifyPayment(String reference) throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://api.paystack.co/transaction/verify/" + reference);
        request.addHeader("Content-type", "application/json");
        request.addHeader(AUTHORIZATION, "Bearer " + key);

        StringBuilder result = new StringBuilder();

        HttpResponse response = client.execute(request);

        if (response.getStatusLine().getStatusCode() == 200) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

        } else {
            throw new IOException("Error Occurred while connecting to pay-stack url");
        }
        return result;
    }


}
