package com.wakacast.dto;

import com.wakacast.enums.SubscriptionPlan;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionDTO {
    private SubscriptionPlan subscriptionPlan;
}
