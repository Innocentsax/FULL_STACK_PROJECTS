package com.wakacast.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseClass{
    private String roleTitle;
}
