package com.decagon.kindredhairapigrp1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;

import com.decagon.kindredhairapigrp1.models.*;
import com.decagon.kindredhairapigrp1.repository.UserRepository;
import com.decagon.kindredhairapigrp1.services.UserService;
import com.decagon.kindredhairapigrp1.repository.HairProfileQuestionRepository;
import com.decagon.kindredhairapigrp1.services.HairProfileQuestionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;


import java.util.Set;

@SpringBootTest
class KindredhairApiGrp1ApplicationTests {

    @Autowired
    private HairProfileQuestionService hairProfileQuestionService;

    @MockBean
    private HairProfileQuestionRepository hairProfileQuestionRepository;

    @BeforeEach
    public void setUp(){

        HashSet<HairProfileAnswer> answers = new HashSet<HairProfileAnswer>();

        HairProfileAnswer answer1 = new HairProfileAnswer();
        answer1.setId(1L);
        answer1.setContent("My first answer.");

        HairProfileAnswer answer2 = new HairProfileAnswer();
        answer2.setId(2L);
        answer2.setContent("My second answer.");

        answers.add(answer1);
        answers.add(answer2);

        HairProfileQuestion question = new HairProfileQuestion();
        question.setId(1L);
        question.setTitle("My first title.");
        question.setContent("My first question?");
        question.setAnswers(answers);

        when(hairProfileQuestionRepository.findAll()).thenReturn(List.of(question));
    }

    @Test
    public void whenGettingQuestionsAndAnswers(){
        assertEquals(hairProfileQuestionService.getAllQuestions().getBody().getStatus(), 302);
        assertEquals(hairProfileQuestionService.getAllQuestions().getBody().getMessage(), "All questions with their respective answers.");
    }

}