package com.example.cedarxpressliveprojectjava010.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
public class Mapper {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
