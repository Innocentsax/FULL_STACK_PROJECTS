package com.example.food.util;

import com.example.food.model.Users;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserUtil {

    public String getAuthenticatedUserEmail(){

      UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedEmail = userDetails.getUsername();
        System.out.println(loggedEmail);
      return userDetails.getUsername();
    }

    public Users currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principle = authentication.getPrincipal();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(principle, Users.class);
    }
}
