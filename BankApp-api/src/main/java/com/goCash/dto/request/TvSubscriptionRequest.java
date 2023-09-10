package com.goCash.dto.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data

public class TvSubscriptionRequest {
    @JsonProperty("request_id")
    private String requestId;

    @NotBlank(message = "Service ID cannot be blank")
    private String serviceID;

    @NotBlank(message = "Billers code cannot be blank")
    private String billersCode;

    @NotBlank(message = "Quantity cannot be blank")
    private String quantity;

    @NotBlank(message = "Subscription type cannot be blank")
    @JsonProperty("subscription_type")
    private String subscriptionType;

    @NotBlank(message = "Variation code cannot be blank")
    @JsonProperty("variation_code")
    private String variationCode;

    @Positive(message = "Amount must be positive")
    private int amount;

    @Pattern(regexp = "\\d{11}", message = "Number must be a valid 11-digit phone number")
    private String phone;
}

