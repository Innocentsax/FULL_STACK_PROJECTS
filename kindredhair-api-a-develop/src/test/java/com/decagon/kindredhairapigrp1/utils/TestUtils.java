package com.decagon.kindredhairapigrp1.utils;

import com.decagon.kindredhairapigrp1.DTO.AnswerResponseDTO;
import com.decagon.kindredhairapigrp1.DTO.UserDTO;
import com.decagon.kindredhairapigrp1.DTO.UserRegistrationRequest;
import com.decagon.kindredhairapigrp1.models.Product;
import com.decagon.kindredhairapigrp1.models.ProductRecommendation;
import com.decagon.kindredhairapigrp1.models.UserDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestUtils {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public static String createStringWithLength(int length) {

       return "a".repeat(length);
    }

    public static UserDetails generateMockUserDetails(){

        UserDetails userDetails = new UserDetails();
        userDetails.setProductRecommendation(generateProductRecommendations());
        userDetails.setId(1L);

        return  userDetails;
    }

    public static Set<ProductRecommendation> generateProductRecommendations(){
        Product product1 = new Product();
        product1.setId(1L);
        product1.setAvailable(true);
        product1.setBrand("hello");
        product1.setCategory("oil");
        product1.setDescription("hbhdbd");
        product1.setFeedbacks(null);
        product1.setImageURL("ddhbhdvhv");
        product1.setIngredients("oil");
        product1.setName("grand opek");
        product1.setPrice(2233);
        product1.setProductURL("dfff");
        product1.setSearchDescription("dfdfdf");
        product1.setSearchIngredients("dvjvhvnd");
        product1.setSize("80 oz");
        Product product2 = new Product();
        product2.setId(2L);
        product2.setAvailable(true);
        product2.setBrand("hello");
        product2.setCategory("oil");
        product2.setDescription("hbhdbd");
        product2.setFeedbacks(null);
        product2.setImageURL("ddhbhdvhv");
        product2.setIngredients("oil");
        product2.setName("grand opek");
        product2.setPrice(2233);
        product2.setProductURL("dfff");
        product2.setSearchDescription("dfdfdf");
        product2.setSearchIngredients("dvjvhvnd");
        product2.setSize("80 oz");
        Product product3 = new Product();
        product3.setId(3L);
        product3.setAvailable(true);
        product3.setBrand("hello");
        product3.setCategory("oil");
        product3.setDescription("hbhdbd");
        product3.setFeedbacks(null);
        product3.setImageURL("ddhbhdvhv");
        product3.setIngredients("oil");
        product3.setName("grand opek");
        product3.setPrice(2233);
        product3.setProductURL("dfff");
        product3.setSearchDescription("dfdfdf");
        product3.setSearchIngredients("dvjvhvnd");
        product3.setSize("80 oz");
        ProductRecommendation productRecommendation1 = new ProductRecommendation();
        productRecommendation1.setProduct(product1);
        productRecommendation1.setCategoryName("revive and restore");
        productRecommendation1.setRecommendationCategory("recommendation 1");
        productRecommendation1.setId(1L);
        ProductRecommendation productRecommendation2 = new ProductRecommendation();
        productRecommendation2.setProduct(product2);
        productRecommendation2.setCategoryName("revive and shine");
        productRecommendation2.setRecommendationCategory("recommendation 2");
        productRecommendation2.setId(2L);
        ProductRecommendation productRecommendation3 = new ProductRecommendation();
        productRecommendation3.setProduct(product3);
        productRecommendation3.setCategoryName("grow and restore");
        productRecommendation3.setRecommendationCategory("recommendation 3");
        productRecommendation3.setId(3L);

        Set<ProductRecommendation> productRecommendations = new HashSet<>();
        productRecommendations.add(productRecommendation1);
        productRecommendations.add(productRecommendation2);
        productRecommendations.add(productRecommendation3);
        return productRecommendations;
    }

    public static void serialize(Object obj, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }

    public static UserRegistrationRequest getUserRegistrationRequestDTO(){

        var userDTO = new UserDTO();
        userDTO.setEmail("hello@hello.com");
        userDTO.setEmail("foo");
        userDTO.setPhoneNumber("09098989876");
        userDTO.setRole("USER");
        userDTO.setStatus("none");

        return new UserRegistrationRequest(userDTO, getAnswerResponseDTO());
    }

    public static AnswerResponseDTO getAnswerResponseDTO(){
        var answerResponseDTO = new AnswerResponseDTO();
        String describe = "weak,damaged";
        String allergies = "poverty";
        String priority = "quality";
        String  whatElse ="i exercise a lot";
        String  porosity= "1 - 3 hours";
        String goals = "strength";
        String  styles = "wash n' go";
        String  brandsIUse = "Alika";
        String  brandsIDontLike = "none";
        String  rating = "4";
        String  budget = "$12";
        String type = "4b - tight coils";
        answerResponseDTO.setDescribe(convertAnswersFromStringToList(describe));
        answerResponseDTO.setAllergies(convertAnswersFromStringToList(allergies));
        answerResponseDTO.setBrandsIDontLike(convertAnswersFromStringToList(brandsIDontLike));
        answerResponseDTO.setBrandsIUse(convertAnswersFromStringToList(brandsIUse));
        answerResponseDTO.setBudget(budget);
        answerResponseDTO.setGoals(convertAnswersFromStringToList(goals));
        answerResponseDTO.setPorosity(porosity);
        answerResponseDTO.setPriority(convertAnswersFromStringToList(priority));
        answerResponseDTO.setRating(rating);
        answerResponseDTO.setStyles(convertAnswersFromStringToList(styles));
        answerResponseDTO.setType(convertAnswersFromStringToList(type));
        answerResponseDTO.setWhatElse(convertAnswersFromStringToList(whatElse));
        return answerResponseDTO;
    }

    public static List<String> convertAnswersFromStringToList(String answer){
        return List.of(answer.split(","));
    }

    public static  String convertAnswersToString(List<String> answer){
        return answer.stream().map(str -> str.trim().toLowerCase()).collect(Collectors.joining(","));
    }
}

