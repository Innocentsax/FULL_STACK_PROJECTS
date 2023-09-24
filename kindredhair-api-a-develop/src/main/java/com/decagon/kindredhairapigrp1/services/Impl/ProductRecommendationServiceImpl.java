package com.decagon.kindredhairapigrp1.services.Impl;

import com.decagon.kindredhairapigrp1.DTO.ProductDTO;
import com.decagon.kindredhairapigrp1.DTO.ProductPriorityDTO;
import com.decagon.kindredhairapigrp1.models.ProductRecommendation;
import com.decagon.kindredhairapigrp1.models.User;
import com.decagon.kindredhairapigrp1.repository.UserRepository;
import com.decagon.kindredhairapigrp1.services.ProductRecommendationService;
import com.decagon.kindredhairapigrp1.services.ProductService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductRecommendationServiceImpl implements ProductRecommendationService {

    ProductService productService;

    UserRepository userRepository;

    public ProductRecommendationServiceImpl(ProductService productService, UserRepository userRepository) {
        this.productService = productService;
        this.userRepository = userRepository;
    }

    // Ingredients to prioritize for describe
    private String weakIngredients = "coconut,argan,grapeseed,shea,aloe,olive,honey,avocado";
    private String dryIngredients = "coconut,aloe,tea tree,apple,witch hazel,yogurt,chamomile,argan,jojoba," +
            "almond,olive,grapeseed,shea,avocado,abyssinian,nettle,panthenol,silk protein,soy,mango";
    private String damagedIngredients = "coconut,argan,jojoba,almond,olive,shea,avocado,chamomile," +
            "wheat protein,maracuja,silk protein,soy";
    private String splitIngredients = "coconut,jojoba,olive,almond,chamomile,apple";
    private String dullIngredients = "coconut,jojoba,olive,grapeseed,aloe,castor,honey,avocado,sunflower,chamomile" +
            ",apple,vitamin e,rice,wheat protein,rosemary,panthenol,silk protein";
    private String fizzyIngredients = "argan,olive,shea,coconut,sunflower,neem,rosemary,silk protein";
    private String coarseIngredients = "argan,aloe,olive,avocado,sunflower,chamomile,wheat protein";

    // Ingredients to prioritize for Porosity
    private String highPorosityIngredients =
            "coconut,olive,shea,avocado,beeswax,triglyceride,sunflower,apricot,wheat,protein";
    private String lowPorosityIngredients =
            "honey,aloe vera,egg,molasses,castor,sugar,flaxseed,agave,mango,aloe vera";

    // Ingredients to prioritize for WhatElse
    private String dandruffIngredients =
            "jojoba,almond,olive,grapeseed,lemongrass,avocado,chamomile,neem,apple,rice,nettle";
    private String thinHairIngredients = "coconut,jojoba,sunflower,apple,wheat protein,biotin,panthenol";
    private String relaxerIngredients = "argan,shea,babassu,maracuja,sacha inchi";
    private String tenderScalpIngrdients = "coconut,aloe vera,tea tree,apple,witch hazel,yogurt,argan,lemongrass";
    private String dyedIngredients = "shea,coconut,chamomile";

    // Ingredients to prioritize for Goals
    private String growthIngredients = "castor,jojoba,almond,lavender,shea,aloe vera,coconut,avocado" +
            ",tea tree,safflower,apple,rice,biotin,pimento,nettle,rosemary";
    private String moistureIngredients = "coconut,aloe vera,tea tree,apple,witch hazel,yogurt,chamomile,argan,jojoba" +
            ",almond,olive,grapeseed,shea,avocado,abyssinian,nettle,panthenol,silk protein,soy,mango";
    private String strengthIngredients = "coconut,argan,grapeseed,neem,vitamin e,rice,rosemary,mango";
    private String damageIngredients = "argan,shea,babassu,maracuja,sacha inchi";
    private String transitionIngredients = "argan,shea,babassu,maracuja,sacha inchi";
    private String restoreEdgeIngredients = "coconut,argan,grapeseed,neem,vitamin e,rice,rosemary,mango";

    // Ingredients to prioritize for Style
    private String heatStyleIngredients = "shea,jojoba,Abyssinian";


    private ProductPriorityDTO foundProduct =null;
    private final List<String> ingredients = new ArrayList<>();

    private final List<String> descriptions = new ArrayList<>();
    private List<ProductPriorityDTO> priorityList = new ArrayList<>();


    @Override
    public List<ProductPriorityDTO> generatePriorityList(String describe, String allergies, String priority,
                                                         String whatElse, String porosity, String goals,
                                                         String styles, int rating, String brandsIUse,
                                                         String brandsIDontLike, String budget, String type) {
        int maxBudget = budgetToCentsConverter(budget);
        addDescribeIngredientsToList(describe);
        addGoalsIngredientsToList(goals);
        addPorosityIngredientsToList(porosity);
        addStylesIngredient(styles);
        addWhatElseIngredientsToList(whatElse);
        addGoalsDescriptionToList(goals);
        addPorosityDescription(porosity);
        addPriorityDescriptionsToList(priority);
        addStyleDescriptionsToList(styles);
        addWhatElseDescriptionToList(whatElse);
        addTypeDescriptionToList(type);
        removeAllergicIngredientsFromList(allergies);
        Map<String, Long> ingredientPriorityByCount = ingredients.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> descriptionPriorityByCount = descriptions.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<ProductDTO> products = productService.findAllAvailableProducts();

        for (ProductDTO product : products) {
            ProductPriorityDTO productPriorityDTO = new ProductPriorityDTO();
            productPriorityDTO.setProductDTO(product);
            for (String ingredient : ingredientPriorityByCount.keySet()) {
                if (product.getIngredients().toLowerCase().contains(ingredient)) {
                    productPriorityDTO.setIngredientPriority(productPriorityDTO.getIngredientPriority()
                            + (int) (ingredientPriorityByCount.get(ingredient)).longValue());
                    productPriorityDTO.setNumOfIngredients(productPriorityDTO.getNumOfIngredients() + 1);
                }
            }
            int sum = 0;
            for (String desc : descriptionPriorityByCount.keySet()) {
                if (product.getDescription().toLowerCase().contains(desc)) {
                    sum+= descriptionPriorityByCount.get(desc);
                    productPriorityDTO.setNumOfDescription(productPriorityDTO.getNumOfDescription() + 1);
                }
            }
            if(maxBudget != 0 && product.getPrice() > maxBudget){
                productPriorityDTO.setIngredientPriority(0);
                productPriorityDTO.setDescriptionPriority(0);
                productPriorityDTO.setNumOfIngredients(0);
                productPriorityDTO.setNumOfDescription(0);
            }
            productPriorityDTO.setDescriptionPriority(sum);
            priorityList.add(productPriorityDTO);
        }
        removeBrandsIDontLikeFromList(rating, brandsIUse, brandsIDontLike);
        priorityList.sort((a, b) -> {
            if (a.getIngredientPriority() != b.getIngredientPriority()) {
                return b.getIngredientPriority() - a.getIngredientPriority();
            } else if (a.getDescriptionPriority() != b.getDescriptionPriority()) {
                return b.getDescriptionPriority() - a.getDescriptionPriority();
            } else if (b.getNumOfIngredients() != a.getNumOfIngredients()) {
                return b.getNumOfIngredients() - a.getNumOfIngredients();
            }
            return b.getNumOfDescription() - a.getNumOfDescription();
        });

        return priorityList.stream().limit(100).collect(Collectors.toList());
    }


    @Override
    public Map<String, List<ProductDTO>> getProductRecommendation(Long userId) throws Exception {
        User user =  userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Invalid User Details"));

        Map<String, List<ProductDTO>> userRecommendation = new HashMap<>();
        Map<String, String> userRecommendationCategory = new HashMap<>();

        Set<ProductRecommendation> productRecommendations =  user.getUserDetails().getProductRecommendation();
        List<ProductDTO> productList;

        for (ProductRecommendation recommendation :productRecommendations ) {
            if(!userRecommendation.containsKey(recommendation.getRecommendationCategory())){
                productList   = new ArrayList<>();
            }else {
                productList = userRecommendation.get(recommendation.getRecommendationCategory());
            }
            if(!userRecommendationCategory.containsKey(recommendation.getCategoryName())){
                userRecommendationCategory.put(recommendation.getRecommendationCategory(), recommendation.getCategoryName());
            }
            productList.add(new ProductDTO.ProductDTOBuilder().setProduct(recommendation.getProduct(), false).build());
            userRecommendation.put(recommendation.getRecommendationCategory(), productList);
        }

        Map<String, List<ProductDTO>> productRecommendationMap = new LinkedHashMap<>();
        /*
        Revive and replenish category can occur more than once. You want to check there's multiple revive and replenish
        categories before adding to the map and denoting them as "revive and replenish", "revive and replenish 2", etc.
         */
        int counter = 1;
        String reviveAndReplenish = "revive and replenish";
        boolean reviveAndReplenishExists = false;
        if(reviveAndReplenish.equals(userRecommendationCategory.get("recommendation 1"))){
            reviveAndReplenishExists = true;
        }
        productRecommendationMap.put(userRecommendationCategory.get("recommendation 1"), userRecommendation.get("recommendation 1"));
        //check for second category
        if(reviveAndReplenish.equals(userRecommendationCategory.get("recommendation 2"))){
            if (reviveAndReplenishExists){
                counter++;
                productRecommendationMap.put(reviveAndReplenish + " " + counter, userRecommendation.get("recommendation 2"));
            } else{
                productRecommendationMap.put(reviveAndReplenish, userRecommendation.get("recommendation 2"));
                reviveAndReplenishExists = true;
            }

        }else{
            productRecommendationMap.put(userRecommendationCategory.get("recommendation 2"), userRecommendation.get("recommendation 2"));
        }
        //check for third category
        if(reviveAndReplenish.equals(userRecommendationCategory.get("recommendation 3")) && reviveAndReplenishExists){
            productRecommendationMap.put(reviveAndReplenish + " " + ++counter, userRecommendation.get("recommendation 3"));
        }else{
            productRecommendationMap.put(userRecommendationCategory.get("recommendation 3"), userRecommendation.get("recommendation 3"));
        }
        return productRecommendationMap;
    }

    @Override
    public String getProductRecommendationCategory(String type, String answer){
        //Answer of fine and healthy should not have any effect on getting the hair profile/recommendation category
        answer = answer.replaceAll("fine|healthy", "")
                .replaceAll(",+", ",")
                .replaceAll(",$", "");

    /*
     Strings of user answers and possible hair profile/product recommendation category
     The String is used to generate a map with user answer as key and
     hair profile/product recommendation category as value
     */
        String describe = "dry@hydrate and soothe\n" +
                "dry,frizzy@moisturize and define\n" +
                "split@repair and restore\n" +
                "frizzy@refine and define\n" +
                "dull,frizzy@shine and define\n" +
                "weak,dull,dry@strengthen and renew\n" +
                "weak,dry@strengthen and renew\n" +
                "coarse,frizzy@soften and shape\n" +
                "coarse@soothe your strands\n" +
                "dry,weak,damaged,split,dull,frizzy,coarse@revive\n" +
                "dry,damaged@moisturize and revive\n" +
                "dry,split@moisturize and repair\n" +
                "dull,dry@strengthen and renew\n" +
                "coarse,dry@soothe your strands\n" +
                "weak,damaged@heal and restore\n" +
                "split,weak@repair and restore\n" +
                "weak,dull@revive and strengthen\n" +
                "weak,frizzy@refine and define\n" +
                "weak,frizzy@refine and define\n" +
                "weak,coarse@gently fortify\n" +
                "damaged,split@fortify and heal\n" +
                "damaged,dull@restore and shine\n" +
                "damaged,frizzy@restore and define\n" +
                "damaged,coarse@gently fortify\n" +
                "split,dull@repair and restore\n" +
                "split,frizzy@refine and define\n" +
                "split,coarse@fortify and heal\n" +
                "dull,coarse@restore and shine\n" +
                "split,weak,damaged@repair and restore\n" +
                "split,weak,damaged,dull@repair and restore\n" +
                "dry,split,weak,damaged@repair and restore\n" +
                "dry,split,weak,damaged,dull@repair and restore\n" +
                "dull,weak,damaged@repair and restore\n" +
                "dry,weak,damaged@repair and restore\n" +
                "dry,dull,frizzy@revitalize and moisturize\n" +
                "dry,dull,frizzy,coarse@revitalize and moisturize";
        String whatElse = "i'm transitioning to natural hair@for a smooth transition\n" +
                "i have thin hair@thicken your strands\n" +
                "i exercise a lot@enjoy being active\n" +
                "i have a tender scalp@gentle solutions\n" +
                "i have locs@for your locs\n" +
                "i have dandruff@fight dandruff";
        String goals = "growth (length)@strengthen and renew\n" +
                "strength@strengthen and renew\n" +
                "repair chemical/color damages@revitalize and moisturize\n" +
                "more moisture@moisturize and revive\n" +
                "transition to natural hair@revive\n" +
                "restore edges@revive\n" +
                "get rid of dandruff@fortify and heal\n" +
                "growth (length),strength@strengthen and renew\n" +
                "growth (length),strength,more moisture@revive and strengthen\n" +
                "strength,more moisture@revive and strengthen";
        String style = "wash n' go@soften and shape\n" +
                "wash n' go,twist out@shine and define\n" +
                "updo@soften and shape\n" +
                "weave,wig,braids,crochet@revive and replenish\n" +
                "heat styles@soften and shape";

        Map<String, String> map;
        String sortedAnswer = reArrangeKey(answer);

        switch (type){
            case "describe":
                map = getMap(describe);
                break;
            case "whatElse":
                map = getMap(whatElse);
                break;
            case "styleMap":
                map = getMap(style);
                break;
            case "goals":
            default:
                map = getMap(goals);
        }

        return  map.get(sortedAnswer) == null ? "revive and replenish" : map.get(sortedAnswer);
    }

    /**
     * Method to get products from our product priority list for each user answer
     * and fill up the matching recommendation category Method takes a list of our
     * three product priority lists and a string containing all the user's answers
     * and returns a list of three lists of products for each recommendation
     * category
     *
     * @param superListOfProductLists
     * @param answer
     * @return
     */
    @Override
    public List<List<ProductDTO>> populateRecommendationLists(
            List<List<ProductPriorityDTO>> superListOfProductLists, String answer) {
        /*
         * List of product types is a string to keep track of the type of product that
         * have been added to a particular recommendation category to ensure a product
         * type doesn't get added more than once
         */
        String listOneProductTypes = "", listTwoProductTypes = "", listThreeProductTypes = "";
        /*
         * Lists for each recommendation category
         */
        List<ProductDTO> firstRecommendationCategoryList = new ArrayList<>(),
                secondRecommendationCategoryList = new ArrayList<>(),
                thirdRecommendationCategoryList = new ArrayList<>();

        // add cream to one product category
        if (answer.contains("dry")) {
            for (ProductPriorityDTO productPriorityDTO : superListOfProductLists.get(0)) {
                if (productPriorityDTO.getProductDTO().getCategory().contains("cream")) {
                    firstRecommendationCategoryList.add(productPriorityDTO.getProductDTO());
                    listOneProductTypes += "cream";
                    foundProduct = productPriorityDTO;
                    break;
                }
            }
            removeProductFromLists(superListOfProductLists, foundProduct);
        }
        // add growth to one category
        if (answer.contains("weak")) {
            for (ProductPriorityDTO productPriorityDTO : superListOfProductLists.get(0)) {
                if (productPriorityDTO.getProductDTO().getCategory().contains("oil")
                        && productPriorityDTO.getProductDTO().getDescription().contains("grow")) {
                    firstRecommendationCategoryList.add(productPriorityDTO.getProductDTO());
                    listOneProductTypes += "oil";
                    foundProduct = productPriorityDTO;
                    break;
                }
            }
            removeProductFromLists(superListOfProductLists, foundProduct);
        }
        /*
         * We want to add oils to two lists. The if/else checks if an oil has already
         * been added to the first list If so then we don't need to add again. We just
         * add the second oil to the second list
         */
        if (answer.matches(".*(dry|growth|moisture|transition|restore|weave|wig|braids|twist|wash|heat|updo).*")) {
            if (!listOneProductTypes.contains("oil")) {
                for (ProductPriorityDTO productPriorityDTO : superListOfProductLists.get(0)) {
                    if (productPriorityDTO.getProductDTO().getCategory().contains("oil")) {
                        firstRecommendationCategoryList.add(productPriorityDTO.getProductDTO());
                        listOneProductTypes += "oil";
                        foundProduct = productPriorityDTO;
                        break;
                    }
                }
                removeProductFromLists(superListOfProductLists, foundProduct);
            }
            for (ProductPriorityDTO productPriorityDTO : superListOfProductLists.get(1)) {
                if (productPriorityDTO.getProductDTO().getCategory().contains("oil")) {
                    secondRecommendationCategoryList.add(productPriorityDTO.getProductDTO());
                    listTwoProductTypes += "oil";
                    foundProduct = productPriorityDTO;
                    break;
                }
            }
            removeProductFromLists(superListOfProductLists, foundProduct);
        }
        // add deep conditioner or hair mask to all categories
        if (answer.matches(".*(dry|damaged|weak|coarse).*")) {
            for (ProductPriorityDTO productPriorityDTO : superListOfProductLists.get(0)) {
                if (productPriorityDTO.getProductDTO().getCategory().matches("deep.*|.*mask.*")) {
                    firstRecommendationCategoryList.add(productPriorityDTO.getProductDTO());
                    listOneProductTypes += "deep conditioner" + "mask";
                    foundProduct = productPriorityDTO;
                    break;
                }
            }
            removeProductFromLists(superListOfProductLists, foundProduct);
            for (ProductPriorityDTO productPriorityDTO : superListOfProductLists.get(1)) {
                if (productPriorityDTO.getProductDTO().getCategory().matches("deep.*|.*mask.*")) {
                    secondRecommendationCategoryList.add(productPriorityDTO.getProductDTO());
                    listTwoProductTypes += "deep conditioner" + "mask";
                    foundProduct = productPriorityDTO;
                    break;
                }
            }
            removeProductFromLists(superListOfProductLists, foundProduct);
            for (ProductPriorityDTO productPriorityDTO : superListOfProductLists.get(2)) {
                if (productPriorityDTO.getProductDTO().getCategory().matches("deep.*|.*mask.*")) {
                    thirdRecommendationCategoryList.add(productPriorityDTO.getProductDTO());
                    listThreeProductTypes += "deep conditioner" + "mask";
                    foundProduct = productPriorityDTO;
                    break;
                }
            }
            removeProductFromLists(superListOfProductLists, foundProduct);
        }
        // add deep conditioner to one category (check if a deep conditioner has been
        // added already)
        if (answer.matches(".*(split|frizzy|fine|healthy|dull).*")) {
            if (!listOneProductTypes.matches(".*(deep|mask).*")) {
                for (ProductPriorityDTO productPriorityDTO : superListOfProductLists.get(0)) {
                    if (productPriorityDTO.getProductDTO().getCategory().contains("deep")) {
                        firstRecommendationCategoryList.add(productPriorityDTO.getProductDTO());
                        listOneProductTypes += "deep conditioner" + "mask";
                        foundProduct = productPriorityDTO;
                        break;
                    }
                }
                removeProductFromLists(superListOfProductLists, foundProduct);
            }
        }
        // add leave-in conditioner to two categories
        if (answer.matches(".*(dry|damaged|weak|coarse|dull|transitioning|moisture).*")) {
            if (!listOneProductTypes.contains("leave")) {
                for (ProductPriorityDTO productPriorityDTO : superListOfProductLists.get(0)) {
                    if (productPriorityDTO.getProductDTO().getCategory().contains("leave")) {
                        firstRecommendationCategoryList.add(productPriorityDTO.getProductDTO());
                        listOneProductTypes += productPriorityDTO.getProductDTO().getCategory();
                        foundProduct = productPriorityDTO;
                        break;
                    }
                }
                removeProductFromLists(superListOfProductLists, foundProduct);
            }

            for (ProductPriorityDTO productPriorityDTO : superListOfProductLists.get(1)) {
                if (productPriorityDTO.getProductDTO().getCategory().contains("leave")) {
                    secondRecommendationCategoryList.add(productPriorityDTO.getProductDTO());
                    listTwoProductTypes += productPriorityDTO.getProductDTO().getCategory();
                    foundProduct = productPriorityDTO;
                    break;
                }
            }
            removeProductFromLists(superListOfProductLists, foundProduct);
        }

        // add style cream to one category
        if (answer.matches(".*(wash|twist|heat|updo|blow).*")) {
            for (ProductPriorityDTO productPriorityDTO : superListOfProductLists.get(1)) {
                if (productPriorityDTO.getProductDTO().getCategory().contains("cream")
                        && productPriorityDTO.getProductDTO().getDescription().contains("style")) {
                    secondRecommendationCategoryList.add(productPriorityDTO.getProductDTO());
                    listTwoProductTypes += productPriorityDTO.getProductDTO().getCategory();
                    foundProduct = productPriorityDTO;
                    break;
                }
            }
            removeProductFromLists(superListOfProductLists, foundProduct);
        }
        // add curl cream to one category
        if (answer.matches(".*(wash|twist).*")) {
            if (!listTwoProductTypes.contains("cream")) {
                for (ProductPriorityDTO productPriorityDTO : superListOfProductLists.get(1)) {
                    if (productPriorityDTO.getProductDTO().getCategory().contains("cream")
                            && productPriorityDTO.getProductDTO().getDescription().contains("curl")) {
                        secondRecommendationCategoryList.add(productPriorityDTO.getProductDTO());
                        listTwoProductTypes += productPriorityDTO.getProductDTO().getCategory();
                        foundProduct = productPriorityDTO;
                        break;
                    }
                }
                removeProductFromLists(superListOfProductLists, foundProduct);
            }
        }
        // add gel and edge control to one category
        if (answer.matches(".*(twist|braid|crotchet).*")) {
            for (ProductPriorityDTO productPriorityDTO : superListOfProductLists.get(1)) {
                if (productPriorityDTO.getProductDTO().getCategory().contains("gel")) {
                    secondRecommendationCategoryList.add(productPriorityDTO.getProductDTO());
                    listTwoProductTypes += productPriorityDTO.getProductDTO().getCategory();
                    foundProduct = productPriorityDTO;
                    break;
                }
            }
            removeProductFromLists(superListOfProductLists, foundProduct);
            for (ProductPriorityDTO productPriorityDTO : superListOfProductLists.get(1)) {
                if (productPriorityDTO.getProductDTO().getCategory().contains("edge")) {
                    secondRecommendationCategoryList.add(productPriorityDTO.getProductDTO());
                    listTwoProductTypes += productPriorityDTO.getProductDTO().getCategory();
                    foundProduct = productPriorityDTO;
                    break;
                }
            }
            removeProductFromLists(superListOfProductLists, foundProduct);
        }

        // add edge control to one category
        if (answer.contains("restore edges")) {
            for (ProductPriorityDTO productPriorityDTO : superListOfProductLists.get(2)) {
                if (productPriorityDTO.getProductDTO().getCategory().contains("edge")) {
                    thirdRecommendationCategoryList.add(productPriorityDTO.getProductDTO());
                    listThreeProductTypes+= productPriorityDTO.getProductDTO().getCategory();
                    foundProduct = productPriorityDTO;
                    break;
                }
            }
            removeProductFromLists(superListOfProductLists, foundProduct);
        }

        List<String> productTypesList = new ArrayList<>();
        List<List<ProductDTO>> listOfRecommendationCategoryLists = new ArrayList<>();

        listOfRecommendationCategoryLists.add(firstRecommendationCategoryList);
        productTypesList.add(listOneProductTypes);

        listOfRecommendationCategoryLists.add(secondRecommendationCategoryList);
        productTypesList.add(listTwoProductTypes);

        listOfRecommendationCategoryLists.add(thirdRecommendationCategoryList);
        productTypesList.add(listThreeProductTypes);

        /*
         * for each list we call the fillRemainingSlotsInLists method to fill up the
         * list till we have five products or there are no more products to recommend
         */
        for (List<ProductDTO> productList : listOfRecommendationCategoryLists) {
            int index = listOfRecommendationCategoryLists.indexOf(productList);
            fillRemainingSlotsInLists(superListOfProductLists, productList, productTypesList.get(index), index);
        }
        return listOfRecommendationCategoryLists;
    }


    private List<String> getListOfIngredients(String descriptn) {
        switch (descriptn) {
            case "dry":
                return Arrays.asList(dryIngredients.split(","));
            case "weak":
                return Arrays.asList(weakIngredients.split(","));
            case "damaged":
                return Arrays.asList(damagedIngredients.split(","));
            case "split":
                return Arrays.asList(splitIngredients.split(","));
            case "dull":
                return Arrays.asList(dullIngredients.split(","));
            case "fizzy":
                return Arrays.asList(fizzyIngredients.split(","));
            case "coarse":
                return Arrays.asList(coarseIngredients.split(","));
            case "highPorosity":
                return Arrays.asList(highPorosityIngredients.split(","));
            case "lowPorosity":
                return Arrays.asList(lowPorosityIngredients.split(","));
            case "dandruff":
                return Arrays.asList(dandruffIngredients.split(","));
            case "thinHair":
                return Arrays.asList(thinHairIngredients.split(","));
            case "relaxer":
                return Arrays.asList(relaxerIngredients.split(","));
            case "tenderScalp":
                return Arrays.asList(tenderScalpIngrdients.split(","));
            case "dyed":
                return Arrays.asList(dyedIngredients.split(","));
            case "growth":
                return Arrays.asList(growthIngredients.split(","));
            case "moisture":
                return Arrays.asList(moistureIngredients.split(","));
            case "strength":
                return Arrays.asList(strengthIngredients.split(","));
            case "repair":
                return Arrays.asList(damageIngredients.split(","));
            case "transitioning":
                return Arrays.asList(transitionIngredients.split(","));
            case "restoreEdge":
                return Arrays.asList(restoreEdgeIngredients.split(","));
            case "heat":
                return Arrays.asList(heatStyleIngredients.split(","));
            default:
                return Arrays.asList("".split(","));
        }
    }

    private void addPorosityDescription(String porosity) {

        if (porosity.contains("3") && porosity.contains("1")) {
            this.descriptions.add("mid porosity");
        } else if (porosity.contains("3")) {
            this.descriptions.add("low porosity");
        } else if (porosity.contains("1")) {
            this.descriptions.add("high porosity");
        }
    }

    private void addTypeDescriptionToList(String hairType) {
        hairType = hairType.toLowerCase();
        Function<String, List<String>> toList = str -> Arrays.asList(str.split(","));
        if(hairType.contains("4")){
            descriptions.addAll(toList.apply(("s pattern,tight,zig zag,coil")));
        }
        if(hairType.contains("3")){
            descriptions.addAll(toList.apply(("ringlet,curly,curl")));
        }
        if(hairType.contains("2")){
            descriptions.add("wavy");
        }
        if(hairType.contains("1")){
            descriptions.add("straight");
        }
    }

    private void addWhatElseDescriptionToList(String whatElse) {

        Function<String, List<String>> toList = str -> Arrays.asList(str.split(","));

        whatElse = whatElse.toLowerCase();

        if (whatElse.matches(".*tender[\\w ]*scalp.*")) {
            descriptions.addAll(toList.apply(("tender,scalp,dry")));
        }

        if (whatElse.matches(".*transition[\\w ]*natural.*")) {
            descriptions.addAll(toList.apply(("chemically treated,transition")));
        }

        if (whatElse.contains("dandruff")) {
            descriptions.add("dandruff");
        }

        if (whatElse.matches(".*thin[\\w ]*hair.*")) {
            descriptions.addAll(toList.apply("thin hair,thinning"));
        }

        if (whatElse.contains("relaxer")) {
            descriptions.addAll(toList.apply(("chemically treated,transition,relaxed")));
        }
        if (whatElse.matches(".*(dye).*")) {
            descriptions.add("dye");
        }

    }

    private void addPriorityDescriptionsToList(String priority) {
        priority = priority.toLowerCase();
        Function<String, List<String>> toList = str -> Arrays.asList(str.split(","));

        if (priority.contains("sustainability")) {
            descriptions.add("sustainably sourced");
        }

        if (priority.contains("smell") || priority.contains("fragrance")) {
            descriptions.addAll(toList.apply("smell,fragrance"));
        }

        if (priority.contains("black")) {
            descriptions.add("black owned");
        }
    }

    private void addStyleDescriptionsToList(String style) {
        style = style.toLowerCase();
        Function<String, List<String>> toList = str -> Arrays.asList(str.split(","));

        if (style.contains("wash") || style.contains("twist")) {
            descriptions.addAll(toList.apply("wash n go,wash and go,twist out"));
        }

        if (style.matches(".*(curl|brush|hot|press).*")) {
            descriptions.addAll(toList.apply("heat,press,press and curl,curl"));
        }

        if (style.contains("blow")) {
            descriptions.add("blow out");
        }
    }

    private void addGoalsDescriptionToList(String goals) {

        goals = goals.toLowerCase();

        Function<String, List<String>> toList = str -> Arrays.asList(str.split(","));

        if (goals.contains("growth")) {
            descriptions.addAll(toList.apply(("grow,growth")));
        }
        if (goals.contains("moisture")) {
            descriptions.addAll(toList.apply(("moisture,moisturizing")));
        }
        if (goals.contains("strength")) {
            descriptions.addAll(toList.apply(("strength,strong,strengthen")));
        }
        if (goals.matches(".*transition.*")) {
            descriptions.addAll(toList.apply(("transition,chemically treated")));
        }
        if (goals.matches(".*restore[\\w ]*edge.*")) {
            descriptions.add("edges");
        }
        if (goals.matches(".*(repair|damage).*")) {
            descriptions.addAll(toList.apply(("transition,chemically treated,color")));
        }

        if (goals.contains("dandruff")) {
            descriptions.add("dandruff");
        }
    }

    private void removeAllergicIngredientsFromList(String allergies) {
        allergies = allergies.toLowerCase();
        ingredients.removeAll(Arrays.asList(allergies.split(",")));
    }

    private void addStylesIngredient(String style) {
        style = style.toLowerCase();

        if (style.matches(".*(curl|curls|brush|hot|press).*")) {
            ingredients.addAll(getListOfIngredients("heat"));
        }
    }

    private void addGoalsIngredientsToList(String goals) {

        goals = goals.toLowerCase();

        if (goals.contains("growth")) {
            ingredients.addAll(getListOfIngredients("growth"));
        }
        if (goals.contains("moisture")) {
            ingredients.addAll(getListOfIngredients("moisture"));
        }
        if (goals.matches(".*transition.*")) {
            ingredients.addAll(getListOfIngredients("transitioning"));
        }
        if (goals.matches(".*restore[\\w ]*edge.*")) {
            ingredients.addAll(getListOfIngredients("restoreEdge"));
        }

        if (goals.contains("strength")) {
            ingredients.addAll(getListOfIngredients("strength"));
        }
        if (goals.matches(".*(repair|damage).*")) {
            ingredients.addAll(getListOfIngredients("repair"));
        }

        if (goals.contains("dandruff")) {
            ingredients.addAll(getListOfIngredients("dandruff"));
        }
    }

    private void addDescribeIngredientsToList(String describe) {
        // Get ingredients for describe
        for (String ans : describe.replaceAll(" ", "").split(",")) {
            ingredients.addAll(getListOfIngredients(ans));
        }
    }

    private void addPorosityIngredientsToList(String porosity) {
        porosity = porosity.toLowerCase();
        if (porosity.contains("3") && porosity.contains("more") || porosity.contains("over")) {
            ingredients.addAll(getListOfIngredients("lowPorosity"));
            return;
        }

        if (porosity.contains("1") && porosity.contains("less")) {
            ingredients.addAll(getListOfIngredients("highPorosity"));
        }
    }

    private void addWhatElseIngredientsToList(String whatElse) {

        whatElse = whatElse.toLowerCase();

        if (whatElse.matches(".*tender[\\w ]*scalp.*")) {
            ingredients.addAll(getListOfIngredients("tenderScalp"));
        }

        if (whatElse.contains("dandruff")) {
            ingredients.addAll(getListOfIngredients("dandruff"));
        }

        if (whatElse.matches(".*thin[\\w ]*hair.*")) {
            ingredients.addAll(getListOfIngredients("thinHair"));
        }

        if (whatElse.contains("relaxer")) {
            ingredients.addAll(getListOfIngredients("relaxer"));
        }
        if (whatElse.matches(".*(dye|dyed).*")) {
            ingredients.addAll(getListOfIngredients("dyed"));
        }
    }

    public void removeBrandsIDontLikeFromList(int rating, String brandsIUse, String brandsIDontLike) {
        String brands = "";
        if (rating < 3 && !brandsIUse.trim().isEmpty()) {
            brands = brandsIUse;
        }
        if (!brandsIDontLike.trim().isEmpty()) {
            brands = brands.isEmpty() ? brandsIDontLike : brands + "," + brandsIDontLike;
        }
        String finalBrands = brands;
        Predicate<ProductPriorityDTO> checkBrandName = productDTO -> finalBrands
                .contains(productDTO.getProductDTO().getBrand().toLowerCase());
        priorityList.removeIf(checkBrandName);
    }

    /*
     * Takes the list of the three product priority lists, the recommendation list
     * to be updated, its corresponding list of product types that have been added
     * and its index in our listOfRecommendationCategoryLists
     */
    private void fillRemainingSlotsInLists(List<List<ProductPriorityDTO>> superListofProductLists,
                                           List<ProductDTO> list, String listOfProductTypes, int index) {
        /*
         * We have to add either a co-wash or a shampoo and a conditioner
         */
        while (!
                ((listOfProductTypes.contains("shampoo") && listOfProductTypes.contains("conditioner"))
                || listOfProductTypes.contains("co-wash") || superListofProductLists.get(index).isEmpty())
        ) {
            for (ProductPriorityDTO productPriorityDTO : superListofProductLists.get(index)) {

                if (productPriorityDTO.getProductDTO().getCategory().contains("co-wash") &&
                        !(listOfProductTypes.contains("shampoo") || listOfProductTypes.contains("conditioner"))) {

                    list.add(productPriorityDTO.getProductDTO());
                    listOfProductTypes += productPriorityDTO.getProductDTO().getCategory();
                    foundProduct = productPriorityDTO;
                    break;
                }
                if (productPriorityDTO.getProductDTO().getCategory().contains("shampoo") &&
                        !listOfProductTypes.contains("co-wash")) {

                    list.add(productPriorityDTO.getProductDTO());
                    listOfProductTypes += productPriorityDTO.getProductDTO().getCategory();
                    foundProduct = productPriorityDTO;
                    break;
                }
                if (productPriorityDTO.getProductDTO().getCategory().contains("conditioner") &&
                        !(listOfProductTypes.contains("conditioner") || listOfProductTypes.contains("co-wash"))) {

                    list.add(productPriorityDTO.getProductDTO());
                    listOfProductTypes += productPriorityDTO.getProductDTO().getCategory();
                    foundProduct = productPriorityDTO;
                    break;
                }
            }
            removeProductFromLists(superListofProductLists, foundProduct);
        }

        while (list.size() < 5 && !superListofProductLists.get(index).isEmpty()) {
            for (ProductPriorityDTO productPriorityDTO : superListofProductLists.get(index)) {
                if (!listOfProductTypes.contains(productPriorityDTO.getProductDTO().getCategory()) ) {
                    list.add(productPriorityDTO.getProductDTO());
                    listOfProductTypes += productPriorityDTO.getProductDTO().getCategory();
                    foundProduct = productPriorityDTO;
                    break;
                }
            }
            removeProductFromLists(superListofProductLists, foundProduct);
        }
    }

    private void removeProductFromLists(List<List<ProductPriorityDTO>> superListofProductLists,
                                              ProductPriorityDTO productPriorityDTO) {
        for (List<ProductPriorityDTO> productList : superListofProductLists) {
            productList.remove(productPriorityDTO);
        }
    }

    private int budgetToCentsConverter(String budget) {
        if(budget.trim().isEmpty() || budget.contains("more") ) return 0;
        Matcher matcher = Pattern.compile("\\$\\d+").matcher(budget);
        if(budget.contains("less") && matcher.find()){
            budget = matcher.group();
        }else{
            while(matcher.find()){
                budget = matcher.group();
            }
        }

        budget = budget.replaceAll(" |\\$", "");

        return (Integer.parseInt(budget) + 3) * 100;
    }

    private String reArrangeKey(String userAnswer) {
        String[] sortedAnswer = userAnswer.split(",");
        Arrays.sort(sortedAnswer);
        return Stream.of(sortedAnswer).filter(a -> !a.isEmpty()).map(String::trim).collect(Collectors.joining(","));
    }

    private Map<String, String> getMap(String string) {
        String[] list = string.split("\n");
        Map<String, String> map = new HashMap<>();
        for (String str : list) {
            String[] arr = str.split("@");
            String sortedKey = reArrangeKey(arr[0]);
            map.put(sortedKey, arr[1]);
        }
        return map;
    }
}
