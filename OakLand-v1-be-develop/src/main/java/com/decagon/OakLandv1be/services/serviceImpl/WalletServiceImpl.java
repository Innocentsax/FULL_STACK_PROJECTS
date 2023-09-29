package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.*;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.exceptions.InsufficientBalanceInWalletException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.exceptions.UnauthorizedUserException;
import com.decagon.OakLandv1be.exceptions.UserNotFoundException;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.TransactionRepository;
import com.decagon.OakLandv1be.repositries.WalletRepository;
import com.decagon.OakLandv1be.services.JavaMailService;
import com.decagon.OakLandv1be.services.WalletService;
import com.decagon.OakLandv1be.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import static com.decagon.OakLandv1be.enums.PaymentPurpose.DEPOSIT;
import static com.decagon.OakLandv1be.enums.TransactionStatus.COMPLETED;
import static com.decagon.OakLandv1be.utils.UserUtil.extractEmailFromPrincipal;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final PersonRepository personRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    private final JavaMailService mailService;

    @Value("${admin.super.email}")
    private String superAdminEmail;
    private final CustomerRepository customerRepository;


    @Override
    public FundWalletResponseDto fundWallet(FundWalletRequest request) {
        Person person = personRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Wallet wallet = person.getCustomer().getWallet();
        wallet.setAccountBalance(wallet.getAccountBalance().add(request.getAmount()));
        walletRepository.save(wallet);

        Transaction transaction = Transaction.builder()
                .wallet(wallet)
                .amount(String.valueOf(request.getAmount()))
                .purpose(DEPOSIT)
                .reference(String.valueOf(UUID.randomUUID()))
                .status(COMPLETED).build();
        transactionRepository.save(transaction);

        try {
            mailService.sendMail(person.getEmail(), "Wallet deposit", "Your wallet has been credited with "
                    + request.getAmount() + ". Your new balance is now " + wallet.getAccountBalance());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return FundWalletResponseDto.builder()
                .fullName(person.getFirstName() + " " + person.getLastName())
                .depositAmount(request.getAmount())
                .newBalance(wallet.getAccountBalance()).build();

        // throw new UnauthorizedUserException("Login to carry out this operation");
    }


    @Override
    public BigDecimal getWalletBalance() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();

            Person person = personRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

            Wallet wallet = person.getCustomer().getWallet();
            BigDecimal walletBalance = wallet.getAccountBalance();

            try {
                mailService.sendMail(person.getEmail(), "Wallet Balance", "Your wallet is " + walletBalance + wallet.getBaseCurrency());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return walletBalance;
        }
        throw new UnauthorizedUserException("User does not have access");
    }
    @Override
    public Boolean processPayment(BigDecimal grandTotal){

        //get the logged in customer wallet
        Wallet customerWallet=getCurrentlyLoggedInCustomerWallet();

        //confirm if the current balance is enough
        if(customerWallet.getAccountBalance().compareTo(grandTotal)>=0){
            //deduct the ground total from the wallet balance
            customerWallet.setAccountBalance(customerWallet.getAccountBalance().subtract(grandTotal));

            //get the admin wallet
            Wallet adminWallet=personRepository.findByEmail(superAdminEmail).orElseThrow(
                    ()-> new UserNotFoundException("System Error: Please contact the admin")
            ).getSuperAdmin().getWallet();
            // add the ground total to super admin wallet
            adminWallet.setAccountBalance(adminWallet.getAccountBalance().add(grandTotal));
            // send an email to the customer
            //TODO         <=================================
            try {

                String customerEmail= UserUtil.extractEmailFromPrincipal().orElseThrow(
                        ()->new UsernameNotFoundException("Please login to continue")
                );

                mailService.sendMail(customerEmail, "Transaction Alert", "Transaction Amount is "  + grandTotal);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return true;
        }
        throw new InsufficientBalanceInWalletException("Insufficient balance, Please fund your wallet.");
    }

    Wallet getCurrentlyLoggedInCustomerWallet(){
        return personRepository.findByEmail(UserUtil.extractEmailFromPrincipal().orElseThrow(
                ()->new UsernameNotFoundException("Please login to continue")
        )).orElseThrow(
                ()-> new UserNotFoundException("Please login to continue")
        ).getCustomer().getWallet();
    }


    @Override
    public WalletInfoResponseDto viewWalletInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();

            Person person = personRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            Wallet wallet = person.getCustomer().getWallet();

            String currencyString = UserUtil.formatToLocale(wallet.getAccountBalance());

            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("en", "NG"));
            symbols.setCurrencySymbol("₦");


            return WalletInfoResponseDto.builder()
                    .firstName(person.getFirstName())
                    .lastName(person.getLastName())
                    .email(person.getEmail())
                    .walletBalance(currencyString)
                    .baseCurrency(String.valueOf(wallet.getBaseCurrency()))
                    .build();
        }
        throw new UnauthorizedUserException("User does not have access");
    }



    @Override
    public Page<TransactionResponseDto> fetchAllTransactions(Integer pageNo, Integer pageSize, String sortBy){
        String email = extractEmailFromPrincipal().get();

        Person person = personRepository.findByEmail(email).orElseThrow();
        Wallet wallet = person.getCustomer().getWallet();

        Set<Transaction> transactions = wallet.getTransactions();

        List<Transaction> transactionsList = new ArrayList<>(transactions);

        List<TransactionResponseDto> response = transactionsList.stream().map(this::responseMapper).collect(Collectors.toList());

        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);


        int minimum = pageNo*pageSize;
        int max = Math.min(pageSize * (pageNo + 1), transactions.size());

        return new PageImpl<>(
                response.subList(minimum, max), pageRequest, response.size()
        );

    }

    @Override
    public Page<FundWalletResponseDto> viewCustomerWalletByPagination(Integer pageNo, Integer pageSize, String sortBy) {
        List<FundWalletResponseDto> fundWalletResponseDtos =
            customerRepository.findAll().stream().map(customer -> FundWalletResponseDto.builder()
                    .fullName(customer.getPerson().getFirstName() + " " + customer.getPerson().getLastName())
                    .depositAmount(customer.getWallet().getAccountBalance())
                    .build()).collect(Collectors.toList());

        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);
        int max = Math.min(pageSize * (pageNo + 1), fundWalletResponseDtos.size());
        return new PageImpl<> (fundWalletResponseDtos.subList(pageNo*pageSize, max), pageable, fundWalletResponseDtos.size());

    }


    protected TransactionResponseDto responseMapper(Transaction transaction){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

        return TransactionResponseDto.builder()
                .id(transaction.getId())
                .date(date.format(transaction.getCreatedAt()))
                .time(time.format(transaction.getCreatedAt()))
                .amount(transaction.getAmount())
                .purpose(String.valueOf(transaction.getPurpose()).toLowerCase())
                .status(String.valueOf(transaction.getStatus()).toLowerCase())
                .reference(transaction.getReference())
                .build();
    }
}
