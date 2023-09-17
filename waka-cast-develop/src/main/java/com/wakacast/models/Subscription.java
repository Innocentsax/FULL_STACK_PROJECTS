package com.wakacast.models;

import com.wakacast.enums.SubscriptionPlan;
import com.wakacast.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscription extends BaseClass{
    @Enumerated(EnumType.STRING)
    private SubscriptionPlan subscriptionPlan;
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;
    private String reference;
    @ManyToOne
    private User subscriber;

}
