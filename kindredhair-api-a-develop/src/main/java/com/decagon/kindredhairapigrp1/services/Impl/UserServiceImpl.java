package com.decagon.kindredhairapigrp1.services.Impl;

import com.decagon.kindredhairapigrp1.DTO.*;
import com.decagon.kindredhairapigrp1.models.*;
import com.decagon.kindredhairapigrp1.repository.HairEducationRepository;
import com.decagon.kindredhairapigrp1.repository.ProductRecommendationRepository;
import com.decagon.kindredhairapigrp1.repository.UserDetailsRepository;
import com.decagon.kindredhairapigrp1.repository.UserRepository;
import com.decagon.kindredhairapigrp1.services.ProductRecommendationService;
import com.decagon.kindredhairapigrp1.services.UserService;
import com.decagon.kindredhairapigrp1.utils.UserHairEducationUtil;
import com.decagon.kindredhairapigrp1.utils.api.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private  ProductRecommendationService productRecommendationService;
    private UserRepository userRepository;
    private UserDetailsRepository userDetailsRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private ProductRecommendationRepository productRecommendationRepository;
    private UserHairEducationUtil userHairEducationUtil;
    private HairEducationRepository hairEducationRepository;

    public UserServiceImpl (
            ProductRecommendationService productRecommendationService,
            UserRepository userRepository,
            UserDetailsRepository userDetailsRepository,
            BCryptPasswordEncoder passwordEncoder,
            ProductRecommendationRepository productRecommendationRepository,
            UserHairEducationUtil userHairEducationUtil,
            HairEducationRepository hairEducationRepository
    ) {
        this.productRecommendationService = productRecommendationService;
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
        this.productRecommendationRepository = productRecommendationRepository;
        this.userHairEducationUtil = userHairEducationUtil;
        this.hairEducationRepository = hairEducationRepository;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Returns a user by email and a throws a UsernameNotFoundException when
     * no user with the provided name exists
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public User getUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid User Email"));
    }

    @Override
    @Transactional
    public ApiResponse registerUser(UserDTO userDTO, AnswerResponseDTO answerResponseDto) {
        ApiResponse response = new ApiResponse();
        User user = new User();
        try {
            getUserByEmail(userDTO.getEmail());
            response.setMessage("The user email exists already");
            response.setStatus(400);
        } catch (UsernameNotFoundException ex) {
            UserDetails userDetails = new UserDetails();
            user.setEmail(userDTO.getEmail());
            user.setRole(userDTO.getRole());
            user.setStatus(userDTO.getStatus());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            User theUser = userRepository.save(user);
            userDetails.setUser(theUser);
            UserDetails theUserDetails = userDetailsRepository.save(userDetails);

            List<ProductRecommendation> productRecommendationList = generateUserProductRecommendation(answerResponseDto, theUserDetails.getId());
            productRecommendationList.forEach( productRecommendation -> productRecommendationRepository.save(productRecommendation));

            List<String> userHairEducationList = userHairEducationUtil.getUserHairEducation(answerResponseDto);
            userHairEducationList.forEach(hairEducation -> {
                UserHairEducation userHairEducation = new UserHairEducation();
                userHairEducation.setUserDetails(theUserDetails);
                userHairEducation.setInformation(hairEducation);
                hairEducationRepository.save(userHairEducation);
            });

            response.setMessage("Succesfully registered.");
            response.setStatus(201);
        }catch (Exception ex){
            response.setMessage(ex.getMessage());
            response.setStatus(400);
        }
        return response;
    }


    public ResponseEntity<ApiResponse<Object>> getUserProfileDetails(Long id){
        if (id < 1) {
            var res = new ApiResponse<>(HttpStatus.BAD_REQUEST);
            res.setMessage("User id must be a number greater than zero");
            return new ResponseEntity<>(res, HttpStatus.valueOf(res.getStatus()));
        }
        User user = getUserById(id);
        if (user == null) {
            var res = new ApiResponse<>(HttpStatus.NOT_FOUND);
            res.setMessage("User not found.");
            return new ResponseEntity<>(res, HttpStatus.valueOf(res.getStatus()));
        }
        if(!user.getEnabled()) {
            var res = new ApiResponse<>(HttpStatus.UNAUTHORIZED);
            res.setMessage("User not enabled.");
            return new ResponseEntity<>(res, HttpStatus.valueOf(res.getStatus()));
        }

        UserDetails userDetails = user.getUserDetails();
        UserProfileResponseDTO userProfileResponseDTO = new UserProfileResponseDTO();

        userProfileResponseDTO.setFirstname(userDetails.getFirstname());
        userProfileResponseDTO.setLastname(userDetails.getLastname());
        userProfileResponseDTO.setCartItems(userDetails.getCartItems());
        userProfileResponseDTO.setUserFavorites(userDetails.getUserFavorites());
        userProfileResponseDTO.setUserHairProfile(userDetails.getUserHairProfile());

        var res = new ApiResponse<>(HttpStatus.OK);
        res.setData(userProfileResponseDTO);
        var message = "User profile details.";
        res.setMessage(message);
        return new ResponseEntity<>(res, HttpStatus.valueOf(res.getStatus()));
    }

    @Override
    public ApiResponse<Map<String, List<ProductDTO>>> getUserRecommendedProduct(Long userId) {
        ApiResponse<Map<String, List<ProductDTO>>> response = new ApiResponse<>();
        try {
            Map<String, List<ProductDTO>> productRecommendationMap = productRecommendationService.getProductRecommendation(userId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Success");
            response.setData(productRecommendationMap);
        } catch(Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setError(e.getMessage());
            response.setMessage("Error fetching product recommendation");
        }
        return response;
    }

    /**
     * Method updates user hair product recommendation.
     * it first generates a new list of product recommendation based on the users updated
     * profile response. It then gets and deletes the old recommendations from the database and
     * then saves the newly generated product recommendation.
     * @param userId
     * @param answerResponseDTO
     * @return
     */
    @Override
    @Transactional
    public ApiResponse<Object> updateUserRecommendation(long userId, AnswerResponseDTO answerResponseDTO) {
        var response = new ApiResponse<>();
        try{
            User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            List<ProductRecommendation> newRecommendation = generateUserProductRecommendation(answerResponseDTO, userId);
            List<ProductRecommendation> oldUserRecommendation
                    = productRecommendationRepository.findProductRecommendationByUserDetails(user.getUserDetails());
            productRecommendationRepository.deleteInBatch(oldUserRecommendation);
            productRecommendationRepository.saveAll(newRecommendation);
             response.setStatus(HttpStatus.OK.value());
            response.setMessage("User profile updated");
        }catch (Exception e){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setError(e.getMessage());
            response.setMessage("Failed to update user profile");
        }
        return response;
    }


    /**
     * Method takes user answers, generates 3 priority lists and 3 product recommendation categories based on the user response
     * It passes the priority list to the populateRecommendationLists which generates 3 lists of recommended products.
     * with the users details set on each product recommendation.
     * @param answerResponseDTO
     * @param userDetailsId
     * @return
     */
    @Override
    public List<ProductRecommendation> generateUserProductRecommendation(AnswerResponseDTO answerResponseDTO, long userDetailsId){
        String describe = convertAnswersToString(answerResponseDTO.getDescribe());
        String allergies = convertAnswersToString(answerResponseDTO.getAllergies());
        String priority = convertAnswersToString(answerResponseDTO.getPriority());
        String whatElse = convertAnswersToString(answerResponseDTO.getWhatElse());
        String porosity = answerResponseDTO.getPorosity();
        String goals = convertAnswersToString(answerResponseDTO.getGoals());
        String styles = convertAnswersToString(answerResponseDTO.getStyles());
        String brandsIUse = convertAnswersToString(answerResponseDTO.getBrandsIUse());
        String brandsIDontLike = convertAnswersToString(answerResponseDTO.getBrandsIDontLike());
        int rating;
        String budget = answerResponseDTO.getBudget();
        String type = convertAnswersToString(answerResponseDTO.getType());
        try{
            rating =  Integer.parseInt(answerResponseDTO.getRating());
        }catch (NumberFormatException e){
            rating = 5;
        }

        String answers = describe + whatElse  + goals + styles;

        List<ProductPriorityDTO> firstList, secondList, thirdList;
        String firstCategory, secondCategory, thirdCategory;
        //A list of products prioritized by the users describe answer
        firstList = productRecommendationService.generatePriorityList(describe,allergies,priority,"",
                porosity,goals,styles,rating,brandsIUse,brandsIDontLike,budget,type);
        //Product recommendation category based on users describe answer
        firstCategory = productRecommendationService.getProductRecommendationCategory("describe", describe);

        if(whatElse.split(",").length > 1){
            /*if user provides multiple what else answers a second and third list of prioritized products are
            generated based on each of both what else answers provided by user
            the same applies for Product recommendation category*/
            String[] whatElseArray = whatElse.split(",");
            secondList = productRecommendationService.generatePriorityList(
                    "", allergies, priority, whatElseArray[0], porosity,
                    goals, "", rating, brandsIUse, brandsIDontLike,budget,type
            );
            secondCategory = productRecommendationService.getProductRecommendationCategory(
                    "whatElse", whatElseArray[0]
            );
            thirdList = productRecommendationService.generatePriorityList(
                    "", allergies, priority, whatElseArray[1],
                    porosity, goals, "", rating, brandsIUse, brandsIDontLike, budget, type
            );
            thirdCategory = productRecommendationService.getProductRecommendationCategory("whatElse", whatElseArray[1]);
        }else{
            //if user provides one what else answer a second list of prioritized products is generated based on that answer
            //while a third list is generate based on the users style answer
            //the same applies for Product recommendation category
            secondList = productRecommendationService.generatePriorityList(
                    "", allergies, priority, whatElse, porosity, goals,
                    "", rating, brandsIUse, brandsIDontLike, budget, type
            );
            secondCategory = productRecommendationService.getProductRecommendationCategory("whatElse", whatElse);
            thirdList = productRecommendationService.generatePriorityList(
                    "", allergies, priority, "", porosity, goals,
                    styles, rating, brandsIUse, brandsIDontLike,budget, type
            );
            thirdCategory = productRecommendationService.getProductRecommendationCategory("styleMap", styles);
        }

        List<List<ProductPriorityDTO>>  superList = new ArrayList<>();
        superList.add(firstList);
        superList.add(secondList);
        superList.add(thirdList);

        List<List<ProductDTO>> recommendations = productRecommendationService.populateRecommendationLists(superList, answers);
        List<ProductRecommendation> productRecommendations = new ArrayList<>();
        UserDetails userDetails = new UserDetails();
        userDetails.setId(userDetailsId);
        for(ProductDTO product: recommendations.get(0)){
            Product newProduct = new Product();
            newProduct.setId(product.getId());
            productRecommendations.add(new ProductRecommendation("recommendation 1",firstCategory, userDetails, newProduct ));
        }
        for(ProductDTO product: recommendations.get(1)){
            Product newProduct = new Product();
            newProduct.setId(product.getId());
            productRecommendations.add(new ProductRecommendation("recommendation 2",secondCategory, userDetails,  newProduct));
        }
        for(ProductDTO product: recommendations.get(2)){
            Product newProduct = new Product();
            newProduct.setId(product.getId());
            productRecommendations.add(new ProductRecommendation("recommendation 3",thirdCategory, userDetails, newProduct));
        }
        return productRecommendations;
    }

    private   String convertAnswersToString(List<String> answer){
        return answer.stream().map(str -> str.trim().toLowerCase()).collect(Collectors.joining(","));
    }
}
