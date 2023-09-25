package com.decadev.money.way.service;

import com.decadev.money.way.dto.response.NetworkDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface NetworkService {
    ResponseEntity<NetworkDTO> getNetworks();
    ResponseEntity<NetworkDTO> getNetworksByBillerCode(String billerCode);
}
