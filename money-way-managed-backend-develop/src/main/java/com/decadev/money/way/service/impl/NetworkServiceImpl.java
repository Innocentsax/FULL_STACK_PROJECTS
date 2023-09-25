package com.decadev.money.way.service.impl;

import com.decadev.money.way.dto.response.NetworkDTO;
import com.decadev.money.way.model.Network;
import com.decadev.money.way.repository.NetworkRepository;
import com.decadev.money.way.service.NetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NetworkServiceImpl implements NetworkService {
    private final NetworkRepository networkRepository;


    @Override
    public ResponseEntity<NetworkDTO> getNetworks() {

        List<Network> networkList = networkRepository.findAll();
        if(networkList.isEmpty()){
            throw new UsernameNotFoundException("network not found");
        }
        var networkDto = NetworkDTO.builder()
                .status("success")
                .message("bill categories")
                .data(networkList)
                .build();

        return ResponseEntity.ok(networkDto);
    }

    @Override
    public ResponseEntity<NetworkDTO> getNetworksByBillerCode(String billerCode) {

        List<Network> networkList = networkRepository.findByBillerCode(billerCode);
        if(networkList.isEmpty()){
            throw new UsernameNotFoundException("biller code not found");
        }
        var networkDto = NetworkDTO.builder()
                .status("success")
                .message("bill categories")
                .data(networkList)
                .build();

        return ResponseEntity.ok(networkDto);
    }
}
