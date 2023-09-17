package com.wakacast.dto.pages_criteria;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class ApplicantPage {
    private int pageNumber = 0;
    private int pageSize = 10;
}
