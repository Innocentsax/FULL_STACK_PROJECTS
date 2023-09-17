package com.decagon.safariwebstore.configuration.paystackconfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InitializeTransaction {
    public static InitializeTransactionResponse initTransaction(InitializeTransactionRequest request) throws Exception {

        InitializeTransactionResponse initializeTransactionResponse = null;

        try {

            // convert transaction to json then use it as a body to post json
            Gson gson = new Gson();

            // add paystack charges to the amount
            StringEntity postingString = new StringEntity(gson.toJson(request));

            CloseableHttpClient client = HttpClientBuilder.create().build();

            HttpPost post = new HttpPost("https://api.paystack.co/transaction/initialize");
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer sk_test_42da6dc739d3dc54596e4bc6a41f4cb163078299");

            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

            } else {
                throw new Exception("Failure initializing paystack transaction");
            }

            ObjectMapper mapper = new ObjectMapper();

            initializeTransactionResponse = mapper.readValue(result.toString(), InitializeTransactionResponse.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return initializeTransactionResponse;
    }
}

