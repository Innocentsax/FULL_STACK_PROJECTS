package com.easyLend.userservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile implements Serializable {


    private static final long serialVersionUID = -1138446817700416884L;

    @JsonProperty
    private String userId;
    private String email;
    private String fullName;


    @Override
    public String toString() {
        return "UserProfile{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
