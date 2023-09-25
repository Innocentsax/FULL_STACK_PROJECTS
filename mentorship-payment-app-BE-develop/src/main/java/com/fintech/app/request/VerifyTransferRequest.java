package com.fintech.app.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VerifyTransferRequest {

    private String event;
    @JsonProperty("event.type")
    private String eventType;

    private Data data;

    public VerifyTransferRequest() {

    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VerifyTransferRequest{" + "event=" + event + ", data=" + data + '}';
    }

    public static class Data {

        @JsonProperty("id")
        private Long id;

        @JsonProperty("tx_ref")
        private String txRef;

        @JsonProperty("flw_ref")
        private String flwRef;

        @JsonProperty("amount")
        private String amount;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("charged_amount")
        private String chargedAmount;

        @JsonProperty("app_fee")
        private String appFee;

        @JsonProperty("merchant_fee")
        private String merchantFee;

        @JsonProperty("narration")
        private String narration;

        @JsonProperty("status")
        private String status;

        @JsonProperty("payment_type")
        private String paymentType;

        private Customer customer;

        public Data() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getMerchantFee() {
            return merchantFee;
        }

        public void setMerchantFee(String merchantFee) {
            this.merchantFee = merchantFee;
        }

        public String getTxRef() {
            return txRef;
        }

        public void setTxRef(String txRef) {
            this.txRef = txRef;
        }

        public String getFlwRef() {
            return flwRef;
        }

        public void setFlwRef(String flwRef) {
            this.flwRef = flwRef;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getChargedAmount() {
            return chargedAmount;
        }

        public void setChargedAmount(String chargedAmount) {
            this.chargedAmount = chargedAmount;
        }

        public String getAppFee() {
            return appFee;
        }

        public void setAppFee(String appFee) {
            this.appFee = appFee;
        }

        public String getNarration() {
            return narration;
        }

        public void setNarration(String narration) {
            this.narration = narration;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", txRef='" + txRef + '\'' +
                    ", flwRef='" + flwRef + '\'' +
                    ", amount='" + amount + '\'' +
                    ", currency='" + currency + '\'' +
                    ", chargedAmount='" + chargedAmount + '\'' +
                    ", appFee='" + appFee + '\'' +
                    ", merchantFee='" + merchantFee + '\'' +
                    ", narration='" + narration + '\'' +
                    ", status='" + status + '\'' +
                    ", paymentType='" + paymentType + '\'' +
                    ", customer=" + customer +
                    '}';
        }
    }

    public static class Customer {
        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("phone_number")
        private String phoneNumber;

        @JsonProperty("email")
        private String email;

        public Customer() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "Customer{" + "id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", email=" + email + '}';
        }

    }

}
