package com.decagon.kindredhairapigrp1.services.Impl;

import java.util.List;

import com.decagon.kindredhairapigrp1.models.HairProfileQuestion;
import com.decagon.kindredhairapigrp1.repository.HairProfileQuestionRepository;
import com.decagon.kindredhairapigrp1.services.HairProfileQuestionService;

import com.decagon.kindredhairapigrp1.utils.api.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HairProfileQuestionServiceImpl implements HairProfileQuestionService {

    @Autowired
    HairProfileQuestionRepository hairProfileQuestionRepository;

    @Override
    public ResponseEntity<ApiResponse<Object>> getAllQuestions(){
        List<HairProfileQuestion> data = hairProfileQuestionRepository.findAll();

        if(data.size() == 0){
            var res = new ApiResponse<>(HttpStatus.OK);
            res.setMessage("No question available yet.");

            return new ResponseEntity<>(res, HttpStatus.valueOf(res.getStatus()));
        }else{
            var res = new ApiResponse<>(HttpStatus.FOUND);
            res.setData(data);
            var message = "All questions with their respective answers.";
            res.setMessage(message);
            return new ResponseEntity<>(res, HttpStatus.valueOf(res.getStatus()));
        }

    }
}