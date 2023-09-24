package com.decagon.kindredhairapigrp1.services.Impl;

import com.decagon.kindredhairapigrp1.models.*;
import com.decagon.kindredhairapigrp1.repository.HairProfileQuestionRepository;
import com.decagon.kindredhairapigrp1.services.HairProfileQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HairProfileQuestionServiceImplTest {

    @Autowired
    private HairProfileQuestionService hairProfileQuestionService;

    @MockBean
    private HairProfileQuestionRepository hairProfileQuestionRepository;

    HairProfileQuestion question = new HairProfileQuestion();

    @BeforeEach
    public void setUp(){

        HashSet<HairProfileAnswer> answers = new HashSet<>();

        HairProfileAnswer answer1 = new HairProfileAnswer();
        answer1.setId(1L);
        answer1.setContent("My first answer.");

        HairProfileAnswer answer2 = new HairProfileAnswer();
        answer2.setId(2L);
        answer2.setContent("My second answer.");

        answers.add(answer1);
        answers.add(answer2);

        question.setId(1L);
        question.setTitle("My first title.");
        question.setContent("My first question?");
        question.setAnswers(answers);

    }

    @Test
    public void whenGettingQuestionsAndAnswers(){
        when(hairProfileQuestionRepository.findAll()).thenReturn(List.of(question));
        assertEquals(hairProfileQuestionService.getAllQuestions().getBody().getStatus(), 302);
        assertEquals(hairProfileQuestionService.getAllQuestions().getBody().getMessage(), "All questions with their respective answers.");
    }

    @Test
    public void whenNoQuestionAndAnswerExist(){
        when(hairProfileQuestionRepository.findAll()).thenReturn(new ArrayList());
        assertEquals(200, hairProfileQuestionService.getAllQuestions().getBody().getStatus());
        assertEquals("No question available yet.", hairProfileQuestionService.getAllQuestions().getBody().getMessage());
    }

}
