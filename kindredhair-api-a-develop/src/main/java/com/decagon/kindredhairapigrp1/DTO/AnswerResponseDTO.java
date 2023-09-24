package com.decagon.kindredhairapigrp1.DTO;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AnswerResponseDTO {
        private List<String> describe;
        private List<String> allergies;
        private List<String> priority;
        private List<String> whatElse;
        private String porosity;
        private List<String> goals;
        private List<String> styles;
        private List<String> brandsIUse;
        private List<String> brandsIDontLike;
        private String rating;
        private String budget;
        private List<String> type;
}
