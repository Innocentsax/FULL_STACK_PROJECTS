package com.example.hive.config;

import com.example.hive.client.PayStackClient;
import com.example.hive.constant.AppConstants;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import static com.example.hive.utils.TransactionUtil.getAuthHeader;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {
    @Value("${secret.key}")
    private  String PAY_STACK_SECRET_KEY;

    @Bean
    public PayStackClient payStackClient(){

        WebClient webClient = WebClient.builder()
                .baseUrl(AppConstants.PSTK_BASE_URI)
                .defaultHeaders(header -> header.addAll(getAuthHeader(PAY_STACK_SECRET_KEY)))
                .build();

        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .build()
                .createClient(PayStackClient.class);
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }
}
