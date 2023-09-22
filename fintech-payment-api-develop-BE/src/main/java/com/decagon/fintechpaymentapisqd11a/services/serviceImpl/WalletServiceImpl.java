package com.decagon.fintechpaymentapisqd11a.services.serviceImpl;

import com.decagon.fintechpaymentapisqd11a.dto.WalletDto;
import com.decagon.fintechpaymentapisqd11a.exceptions.UserNotFoundException;
import com.decagon.fintechpaymentapisqd11a.models.Users;
import com.decagon.fintechpaymentapisqd11a.models.Wallet;
import com.decagon.fintechpaymentapisqd11a.repositories.UsersRepository;
import com.decagon.fintechpaymentapisqd11a.repositories.WalletRepository;
import com.decagon.fintechpaymentapisqd11a.request.FlwWalletRequest;
import com.decagon.fintechpaymentapisqd11a.response.FlwVirtualAccountResponse;
import com.decagon.fintechpaymentapisqd11a.services.WalletService;
import com.decagon.fintechpaymentapisqd11a.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@RequiredArgsConstructor
@Service
public class WalletServiceImpl implements WalletService {

private final WalletRepository walletRepository;
private final UsersRepository usersRepository;

    @Override
    public WalletDto viewWalletDetails() throws UserNotFoundException {
        WalletDto walletDto =new WalletDto();
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users = usersRepository.findByEmail(user.getUsername()).
                orElseThrow(()-> new UserNotFoundException("Not found"));
        Wallet wallet = users.getWallet();
        walletDto.setBalance(wallet.getBalance());
        walletDto.setAcctNumber(wallet.getAcctNumber());
        BeanUtils.copyProperties(users, walletDto);
        return walletDto;
    }


    @Override
    public Wallet createWallet(FlwWalletRequest walletRequest) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + Constant.AUTHORIZATION);
        FlwWalletRequest payload = generatePayload(walletRequest);
        HttpEntity<FlwWalletRequest> request = new HttpEntity<>(payload, headers);

        FlwVirtualAccountResponse body = restTemplate.exchange(
                Constant.CREATE_VIRTUAL_ACCOUNT_API,
                HttpMethod.POST,
                request,
                FlwVirtualAccountResponse.class
        ).getBody();

        Wallet wallet = Wallet.builder()
                .bankName(body.getData().getBankName())
                .acctNumber(body.getData().getAccountNumber())
                .balance(Double.parseDouble(body.getData().getAmount()))
                .build();
        return wallet;
    }

    private FlwWalletRequest generatePayload(FlwWalletRequest flwWalletRequest){
        FlwWalletRequest jsonData = FlwWalletRequest.builder()
                .firstname(flwWalletRequest.getFirstname())
                .lastname(flwWalletRequest.getLastname())
                .email(flwWalletRequest.getEmail())
                .bvn(flwWalletRequest.getBvn())
                .is_permanent(true)
                .phonenumber(flwWalletRequest.getPhonenumber())
                .tx_ref("FinTech Payment App SQ-11A")
                .narration("Payment")
                .build();

            return jsonData;
    }
}
