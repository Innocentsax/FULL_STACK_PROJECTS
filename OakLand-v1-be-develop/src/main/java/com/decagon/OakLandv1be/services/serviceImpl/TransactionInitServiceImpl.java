package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.dto.TransactionDto;
import com.decagon.OakLandv1be.dto.TransactionInitRequestDto;
import com.decagon.OakLandv1be.dto.TransactionInitResponseDto;
import com.decagon.OakLandv1be.entities.Amount;
import com.decagon.OakLandv1be.entities.PaystackTransaction;
import com.decagon.OakLandv1be.entities.Transaction;
import com.decagon.OakLandv1be.exceptions.EmptyListException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.repositries.PaystackTransactionRepository;
import com.decagon.OakLandv1be.repositries.TransactionRepository;
import com.decagon.OakLandv1be.services.TransactionInitService;
import com.decagon.OakLandv1be.services.WalletService;
import com.decagon.OakLandv1be.utils.ApiConnection;
import com.decagon.OakLandv1be.utils.ApiQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TransactionInitServiceImpl implements TransactionInitService {

    private final PaystackTransactionRepository transactionRepository;
    private final TransactionRepository transactionRepo;
    private ApiConnection apiConnection;

    private final String key = "sk_test_26cc81b3fc91a4e6cd2002ba7f2beeec550cb07f";

    private final WalletService walletService;

    private final HttpServletRequest servletRequest;



    @Override
    public JSONObject initTransaction(Amount amount) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        String amountInKobo = amount.getAmount() + "00";

        TransactionInitRequestDto request = new TransactionInitRequestDto();
        request.setAmount(amountInKobo);
        request.setEmail(email);
        request.setReference(UUID.randomUUID().toString());
        request.setCallback_url("http://" + servletRequest.getServerName() + ":3000" + "/confirm-payment?reference=" + request.getReference());


        apiConnection = new ApiConnection("https://api.paystack.co/transaction/initialize");

        ApiQuery query = new ApiQuery();
        query.putParams("amount", request.getAmount());
        query.putParams("email", request.getEmail());
        query.putParams("reference", request.getReference());
        query.putParams("callback_url", request.getCallback_url());

        PaystackTransaction transaction = PaystackTransaction.builder()
                .reference(request.getReference())
                .email(email)
                .success(true).build();
        transactionRepository.save(transaction);

        return apiConnection.connectAndQuery(query);
    }


    @Override
    public TransactionInitResponseDto verifyPayment(String reference) {

        TransactionInitResponseDto responseDto = null;
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("https://api.paystack.co/transaction/verify/" + reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + key);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

            } else {
                throw new Exception("Error Occured while connecting to paystack url");
            }
            ObjectMapper mapper = new ObjectMapper();

            responseDto = mapper.readValue(result.toString(), TransactionInitResponseDto.class);

            if (responseDto == null || responseDto.getStatus().equals("false")) {
                throw new Exception("An error occurred while verifying payment");
            } else if (responseDto.getData().getStatus().equals("success")) {
                //PAYMENT IS SUCCESSFUL, APPLY VALUE TO THE TRANSACTION
                responseDto.setStatus(true);
                responseDto.setMessage("Payment successful");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return responseDto;

    }


    @Override
    public String finalizeTransaction(String reference){
        TransactionInitResponseDto trans = verifyPayment(reference);
        if(trans.getStatus()){
            PaystackTransaction transDetails=transactionRepository.findByReference(reference).orElseThrow(
                    ()->     new ResourceNotFoundException("No such transaction in our record")
            );

            walletService.fundWallet(FundWalletRequest
                    .builder()
                    .email(transDetails.getEmail())
                    .amount(trans.getData().getAmount()
                    .divide(new BigDecimal(100)))
                    .build()
            );
            transDetails.setSuccess(true);
            transactionRepository.save(transDetails);
            return "Transaction Completed";
        }
        return "Transaction not successful";
    }

    @Override
    public Page<TransactionDto> viewAllTransactionsPaginated(Integer pageNo, Integer pageSize, String sortBy, boolean isAscending) {
        List<Transaction> transactions = transactionRepo.findAll();
        if(transactions.isEmpty()){
            throw new EmptyListException("Sorry there are no Transactions to display");
        }
        List<TransactionDto> transactionDtos = transactions.stream()
                .map(transaction -> TransactionDto.builder()
                        .status(transaction.getStatus())
                        .order(transaction.getOrder())
                        .build()).collect(Collectors.toList());
        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);
        int max = Math.min(pageSize * (pageNo + 1), transactionDtos.size());
        return new PageImpl<>(transactionDtos.subList(pageNo*pageSize, max), pageable, transactionDtos.size());
    }
}
