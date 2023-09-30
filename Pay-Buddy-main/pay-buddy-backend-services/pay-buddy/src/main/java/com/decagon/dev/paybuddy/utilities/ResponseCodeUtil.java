package com.decagon.dev.paybuddy.utilities;

import com.decagon.dev.paybuddy.enums.ResponseCodeEnum;
import com.decagon.dev.paybuddy.dtos.requests.ResponseParam;
import com.decagon.dev.paybuddy.restartifacts.BaseResponse;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
public class ResponseCodeUtil {

    public <T extends BaseResponse> T updateResponseData(T baseResponse, ResponseCodeEnum responseCodeEnum) {
        return updateResponseData(baseResponse, responseCodeEnum, Collections.emptyMap());
    }

    public <T extends BaseResponse> T updateResponseData(T baseResponse,
                                                         ResponseCodeEnum responseCodeEnum, Map<String, String> params) {
        List<ResponseParam> responseParams = params.entrySet().stream().map(map -> new ResponseParam(map.getKey(),
                map.getValue())).collect(Collectors.toList());
        String responseCodeDescription = getFormattedResponseCodeDescription(responseCodeEnum, responseParams);
        return updateResponseData(baseResponse, responseCodeEnum, responseCodeDescription);
    }

    public <T extends BaseResponse> T updateResponseData(T baseResponse, ResponseCodeEnum responseCode, String description) {
        baseResponse.assignResponseCodeAndDescription(responseCode.getCode(), description);
        return baseResponse;
    }

    public String getFormattedResponseCodeDescription(ResponseCodeEnum responseCodeEnum, List<ResponseParam> responseParams) {
        return getMessage(responseCodeEnum, responseParams);
    }

    public <T> T updateResponseDataReturnObject(BaseResponse response, ResponseCodeEnum responseCodeEnum, T object) {
        updateResponseData(response, responseCodeEnum);
        return object;
    }

    private String getMessage(ResponseCodeEnum responseCodeEnum, List<ResponseParam> params) {
        if (StringUtils.isBlank(responseCodeEnum.getDescription())) {
            return null;
        }
        return this.buildFullMessage(responseCodeEnum.getDescription(), params);
    }

    private String buildFullMessage(String template, List<ResponseParam> params) {
        if (!CollectionUtils.isEmpty(params)) {
            Map<String, String> valueMap = params.stream()
                    .collect(Collectors.toMap(ResponseParam::getKey, ResponseParam::getValue));
            return StringSubstitutor.replace(template, valueMap);
        }
        return template;
    }
}
