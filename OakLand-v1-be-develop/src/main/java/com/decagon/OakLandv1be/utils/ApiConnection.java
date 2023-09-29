package com.decagon.OakLandv1be.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static org.apache.http.nio.conn.ssl.SSLIOSessionStrategy.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiConnection {

    private String url;
    private String API_KEY = "sk_test_26cc81b3fc91a4e6cd2002ba7f2beeec550cb07f";

    public ApiConnection(String url){
        this.url = url;
        this.enforceTlsV1point2();
    }


    private void enforceTlsV1point2() {
        try {
            SSLContext sslContext = SSLContexts.custom()
                    .useTLS()
                    .build();
            SSLConnectionSocketFactory f = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1.2"},
                    null,
                    BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(f)
                    .build();
            Unirest.setHttpClient(httpClient);

        } catch (NoSuchAlgorithmException | KeyManagementException ex) {
           ex.printStackTrace();
        }
    }

    public JSONObject connectAndQuery(ApiQuery query){
        HttpResponse<JsonNode> queryForResponse = null;
        try{
            queryForResponse = Unirest.post(url)
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .fields(query.getParams())
                    .asJson();


        }catch (UnirestException e){
            e.printStackTrace();
        }
        assert queryForResponse != null;
        return queryForResponse.getBody().getObject();
    }

//    public JSONObject connectAndQueryWithGet() {
//        HttpResponse<JsonNode> queryForResponse = null;
//        try {
//            queryForResponse = Unirest.get(url)
//                    .header("Accept", "application/json")
//                    .header("Authorization", "Bearer " + apiKey)
//                    .asJson();
//
//        } catch (UnirestException e) {
//            e.printStackTrace();
//        }
//        assert queryForResponse != null;
//        return queryForResponse.getBody().getObject();
//    }


}
