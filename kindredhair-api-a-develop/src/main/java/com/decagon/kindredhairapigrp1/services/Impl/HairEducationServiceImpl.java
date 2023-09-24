package com.decagon.kindredhairapigrp1.services.Impl;

import com.decagon.kindredhairapigrp1.models.UserHairEducation;
import com.decagon.kindredhairapigrp1.repository.HairEducationRepository;
import com.decagon.kindredhairapigrp1.services.HairEducationService;
import com.decagon.kindredhairapigrp1.utils.api.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HairEducationServiceImpl implements HairEducationService {

    private HairEducationRepository hairEducationRepository;

    HairEducationServiceImpl(HairEducationRepository hairEducationRepository){
        this.hairEducationRepository = hairEducationRepository;
    }

    @Override
    public  ResponseEntity<ApiResponse<Object>> getUserHairEducation(Long userID) {
        ApiResponse response;
        if (userID < 1){
            response =  new ApiResponse<>(HttpStatus.BAD_REQUEST);
            response.setMessage("UserID Must Be A Positive Integer");
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
        }
        try {
            // get user Education Info from the DB;
            List<UserHairEducation> getUserHairEducation =  hairEducationRepository.findAllByUserDetails_Id(userID);
            if ( getUserHairEducation == null ){
                response =  new ApiResponse<>(HttpStatus.NOT_FOUND);
                response.setMessage("User ID NOT FOUND");
            } else if (getUserHairEducation.size() == 0){
                response =  new ApiResponse<>(HttpStatus.OK);
                response.setMessage("Hair Education Information Not Found");
            }else {
                response = new ApiResponse<>(HttpStatus.OK);
                response.setMessage("Successful");
                response.setData(getUserHairEducation);
            }

        } catch (Exception exception) {
            response =  new ApiResponse<>(HttpStatus.SERVICE_UNAVAILABLE);
            response.setError(exception.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

}