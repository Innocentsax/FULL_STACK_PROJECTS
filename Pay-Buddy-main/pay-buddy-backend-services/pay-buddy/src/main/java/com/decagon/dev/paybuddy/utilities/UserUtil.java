package com.decagon.dev.paybuddy.utilities;

import com.decagon.dev.paybuddy.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserUtil {

    public String getAuthenticatedUserEmail(){

      UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      return userDetails.getUsername();
    }

    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principle = authentication.getPrincipal();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(principle, User.class);
    }
}
