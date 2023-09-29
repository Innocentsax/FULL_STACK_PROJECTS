//package com.decagon.OakLandv1be.controllers;
//
//
//import com.decagon.OakLandv1be.entities.Delivery;
//import com.decagon.OakLandv1be.enums.DeliveryStatus;
//import com.decagon.OakLandv1be.services.DeliveryService;
//import com.decagon.OakLandv1be.utils.ApiResponse;
//import com.decagon.OakLandv1be.utils.ResponseManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("api/v1/delivery")
//public class DeliveryController {
//    private final DeliveryService deliveryService;
//    private final ResponseManager responseManager;
//
//    @GetMapping("/{status}")
//    public ResponseEntity<ApiResponse<List<Delivery>>> getDeliveryByStatus(@PathVariable DeliveryStatus deliveryStatus){
//        ApiResponse apiResponse = responseManager.success(deliveryService.getDeliveryByStatus(deliveryStatus));
//        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//    }
//}
