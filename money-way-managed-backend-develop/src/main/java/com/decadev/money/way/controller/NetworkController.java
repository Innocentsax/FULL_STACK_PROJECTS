package com.decadev.money.way.controller;


import com.decadev.money.way.dto.response.NetworkDTO;
import com.decadev.money.way.service.NetworkService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/user/network") 

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class NetworkController {
    private final NetworkService networkService;


    @GetMapping("/get-networks")
    public ResponseEntity<NetworkDTO> getNetworks(@Nullable @RequestParam ("biller_code") String billerCode){
        if (billerCode == null){
            return networkService.getNetworks();
        }
        return networkService.getNetworksByBillerCode(billerCode);
    }
}
