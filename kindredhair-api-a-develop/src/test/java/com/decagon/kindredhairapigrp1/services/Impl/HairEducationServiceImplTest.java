package com.decagon.kindredhairapigrp1.services.Impl;

import com.decagon.kindredhairapigrp1.models.UserDetails;
import com.decagon.kindredhairapigrp1.models.UserHairEducation;
import com.decagon.kindredhairapigrp1.repository.HairEducationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HairEducationServiceImplTest {
    @Mock
    HairEducationRepository hairEducationRepositoryMock;
    @InjectMocks
    HairEducationServiceImpl hairEducationServiceImpl;

    List<UserHairEducation> userHairEducation =  new ArrayList<>();
    UserHairEducation userHairEducationInfo1 = new UserHairEducation();
    UserHairEducation userHairEducationInfo2 = new UserHairEducation();
    @BeforeEach
    public void setup(){
        userHairEducationInfo1.setInformation("Use Dollars To Wash Your Hair");
        userHairEducationInfo2.setInformation("Use Pounds To Wash Your Hair");
        userHairEducationInfo1.setUserDetails(new UserDetails());
        userHairEducation.add(userHairEducationInfo1);
        userHairEducation.add(userHairEducationInfo2);

    }
    @Test
    public void getUserHairEducation(){
        when(hairEducationRepositoryMock.findAllByUserDetails_Id(50L)).thenReturn(userHairEducation);
        Assertions.assertEquals(200, hairEducationServiceImpl.getUserHairEducation(50L).getBody().getStatus());
        Assertions.assertEquals("Successful", hairEducationServiceImpl.getUserHairEducation(50L).getBody().getMessage());
    }

    @Test
    public void  getUserHairEducation_InvalidID(){
        Assertions.assertEquals("UserID Must Be A Positive Integer", hairEducationServiceImpl.getUserHairEducation(-500L).getBody().getMessage());
        Assertions.assertEquals(400, hairEducationServiceImpl.getUserHairEducation(-500L).getBody().getStatus());

    }
}