package com.decagon.fintechpaymentapisqd11a.services.serviceImpl;

import com.decagon.fintechpaymentapisqd11a.dto.LocalBankTransferDto;
import com.decagon.fintechpaymentapisqd11a.enums.TransactionStatus;
import com.decagon.fintechpaymentapisqd11a.enums.Transactiontype;
import com.decagon.fintechpaymentapisqd11a.exceptions.IncorrectTransactionPinException;
import com.decagon.fintechpaymentapisqd11a.exceptions.InsufficientBalanceException;
import com.decagon.fintechpaymentapisqd11a.exceptions.InvalidAmountException;
import com.decagon.fintechpaymentapisqd11a.exceptions.WalletNotFoundException;
import com.decagon.fintechpaymentapisqd11a.models.Transaction;
import com.decagon.fintechpaymentapisqd11a.models.Users;
import com.decagon.fintechpaymentapisqd11a.models.Wallet;
import com.decagon.fintechpaymentapisqd11a.repositories.TransactionRepository;
import com.decagon.fintechpaymentapisqd11a.repositories.UsersRepository;
import com.decagon.fintechpaymentapisqd11a.repositories.WalletRepository;
import com.decagon.fintechpaymentapisqd11a.services.LocalTransferService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@AllArgsConstructor
@Builder
public class LocalTransferServiceImpl implements LocalTransferService {


    private final WalletRepository walletRepository;

    private final UsersRepository usersRepository;

    private final TransactionRepository transactionRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public String localTransfer(LocalBankTransferDto localBankTransferDto) {
        UUID uuid = UUID.randomUUID();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = user.getUsername();

        Users loggedInUser = usersRepository.findUsersByEmail(email).get();

        Wallet receiversWallet = walletRepository.findWalletByAcctNumberAndBankName(localBankTransferDto.getAccountNumber(), localBankTransferDto.getBankName()).orElse(null);
        Wallet senderWallet = walletRepository.findWalletByUsersId(loggedInUser.getId());



        if(localBankTransferDto.getAmount().doubleValue() <= 0){
            throw new InvalidAmountException("Invalid transfer amount!");
        }
        if(receiversWallet == null){
            throw new WalletNotFoundException("Invalid Account Number or Bank name!");
        }
        if(senderWallet.getBalance() < localBankTransferDto.getAmount().doubleValue()){
            throw new InsufficientBalanceException("Insufficient balance!");
        }
        if(!passwordEncoder.matches(localBankTransferDto.getPin(), loggedInUser.getTransactionPin())){
            throw new IncorrectTransactionPinException("Incorrect Transaction PIN");
        }

        Transaction senderTransaction = new Transaction();


        try {
            double senderBalance = walletRepository.findWalletByUsersId(usersRepository.findByEmail(user.getUsername()).get().getId()).getBalance();
            double receiverBalance = walletRepository.findWalletByAcctNumber(receiversWallet.getAcctNumber()).getBalance();

            receiversWallet.setBalance(receiverBalance+localBankTransferDto.getAmount().doubleValue());
            senderWallet.setBalance(senderBalance-localBankTransferDto.getAmount().doubleValue());

            walletRepository.save(receiversWallet);
            walletRepository.save(senderWallet);
            senderTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            senderTransaction.setTransactionStatus(TransactionStatus.FAILED);
            throw new RuntimeException(e);
        }


        senderTransaction = Transaction.builder()
                .amount(localBankTransferDto.getAmount())
                .destinationAccountName(receiversWallet.getBankName())
                .narration(localBankTransferDto.getNarration())
                .destinationBank(receiversWallet.getBankName())
                .destinationAccountNumber(localBankTransferDto.getAccountNumber())
                .sourceAccountNumber(senderWallet.getAcctNumber())
                .transactiontype(Transactiontype.DEBIT)
                .transactionDate(localBankTransferDto.getTransactionDate())
                .sourceBank(senderWallet.getBankName())
                .clientRef(uuid.toString())
                .build();

        transactionRepository.save(senderTransaction);

        return "Transaction successful!";



    }
}
