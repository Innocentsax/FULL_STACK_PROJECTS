package com.hrsupportcentresq014.dtos.response;

import com.hrsupportcentresq014.entities.Award;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllAwardsResponseDTO {
    private List<Award> awardList;
    private Long totalElement;
    private int totalPage;
    private int currentPage;
}
