package com.wakacast.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class PersonaResponse {
    private List<String> userPersonas;
}
