package com.fintech.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fintech.app.model.FlwBank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class FlwBankResponse {
    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private List<FlwBank> data;

    public FlwBankResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public List<FlwBank> getData() {
        return data;
    }

    public void setData(List<FlwBank> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FlwBankResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

//    @Override
//    public String toString() {
//        return "FlwBankResponse{" + "status=" + status + ", message=" + message + ", data=" + data + '}';
//    }
}
