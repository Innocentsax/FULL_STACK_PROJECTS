package com.decagon.fintechpaymentapisqd11a.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.decagon.fintechpaymentapisqd11a.models.FlwBank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlwGetAllBanksResponse {

    private String status;
    private String message;
    private List<FlwBank> data = new ArrayList<>();

}
