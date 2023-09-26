package com.example.money_way.service.impl;

import com.example.money_way.dto.response.BanksResponse;
import com.example.money_way.model.Bank;
import com.example.money_way.repository.BankRepository;
import com.example.money_way.service.BankService;
import com.example.money_way.utils.RestTemplateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankListRepository;
    private final RestTemplateUtil restTemplateUtil;

    @Override
    public Page<Bank> getAllBanks(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return bankListRepository.findAll(pageable);
    }

    @Override
    public void updateBankList() {

        BanksResponse banksResponse = restTemplateUtil.fetchAllBanksFromFlutterwave();

        if (banksResponse != null && banksResponse.getStatus().equalsIgnoreCase("SUCCESS")) {

            for(Map<String, String> bank : banksResponse.getData()){
                Optional<Bank> bankList = bankListRepository.findByBankCode(bank.get("code"));

                if(bankList.isEmpty()){
                    Bank newBank = new Bank(
                            bank.get("name"), bank.get("code")
                    );
                    bankListRepository.save(newBank);
                }
            }
        }
    }
}
