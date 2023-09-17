package com.wakacast.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseData {
    private HttpResponse responseDetails;
    private ApiResponse apiResponse;

    public ResponseData(HttpResponse responseDetails) {
        this.responseDetails = responseDetails;
    }

    public ResponseData(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
    }
}
