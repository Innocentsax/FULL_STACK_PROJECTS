//package com.decagon.OakLandv1be.services.serviceImpl;
//
//import com.decagon.OakLandv1be.entities.Delivery;
//import com.decagon.OakLandv1be.enums.DeliveryStatus;
//import com.decagon.OakLandv1be.repositries.DeliveryRepository;
//import com.decagon.OakLandv1be.services.DeliveryService;
//import com.decagon.OakLandv1be.utils.ResponseManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@RequiredArgsConstructor
//@Service
//public class DeliveryServiceImpl implements DeliveryService{
//    private final DeliveryRepository deliveryRepository;
//    private final ResponseManager responseManager;
//
//    @Override
//    public List<Delivery> getDeliveryByStatus(DeliveryStatus deliveryStatus){
//        return deliveryRepository.findAll().parallelStream()
//                .filter(deliveries -> deliveries.getStatus().name().equals(deliveryStatus))
//                .collect(Collectors.toList());
//    }
//}
