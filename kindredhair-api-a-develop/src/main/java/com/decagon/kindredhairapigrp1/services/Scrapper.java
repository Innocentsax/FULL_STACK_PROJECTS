package com.decagon.kindredhairapigrp1.services;

import com.decagon.kindredhairapigrp1.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface Scrapper {
    /**
     * The method scrapes the web for products and saves the products
     * to a products list
     */
    void scrape();

    default boolean productCheck(String ingredients, String price, String name, String description) {
        boolean  isNotProductCollections = !name
                .toLowerCase()
                .matches(".*(ki(t|ts)|collectio(n|ns)|pack|bundle|body|brush|massager|series|gift|duo|trio).*");
        return ingredients != null
                && !ingredients.isEmpty()
                && price != null
                && !price.isBlank()
                && isNotProductCollections
                && description != null
                && !description.trim().isEmpty();
    }

    default String getSearchIngredient(String productIngredient) {
        productIngredient = productIngredient == null ? "": productIngredient.toLowerCase();
        String ing = "coconut,aloe,tea tree,apple,witch hazel,yogurt,chamomile,argan,jojoba,almond,olive,grapeseed" +
                ",shea,avocado,abyssinian,nettle,panthenol,silk protein,soy,mango,honey,avocado,shea,wheat protein" +
                ",maracuja,castor,sunflower,vitamin e,rice,rosemary,neem,"
                + "beeswax,triglyceride,apricot,egg,molasses,sugar,flaxseed,agave,lemongrass,biotin" +
                ",babassu,sacha inchi,lavender,safflower,pimento";

        String[] ingredients = ing.split(",");
        List<String> searchIngredient = new ArrayList<>();
        for (String ingredient : ingredients) {
            if (productIngredient.contains(ingredient.toLowerCase())) {
                searchIngredient.add(ingredient);
            }
        }
        return String.join(" ", searchIngredient);
    }

    default String getSearchDescription(String productDescription) {
        productDescription = productDescription == null ? "" : productDescription.toLowerCase();
        String descriptionString = "type 4,type four,type 3,type three,type two,type one,type 2,type 1,"
                + "high porosity,mid porosity,low porosity,chemically treated,dandruff,thin hair,thinning,"
                + "relaxed,tender,scalp,dry,dye,color treated,cowash,moisturizing,grow,moisture,strong,strength"
                + ",transition,chemically treated,color damage,edges,"
                + "sustainably sourced,smell,fragrance,black owned,wash n go,wash and go,twist out,blow out"
                + "heat,press,press and curl,braids,protective styles,crochet,french braids,extensions,weave,wig"
                + "moisturized,scent,repair,damage,revitalize,strong,hydrate,full,thick,fuller,2a,2b,2c,2d,3a,3b,3c,3d,4a,4b,4c,4d";

        String[] descriptions = descriptionString.split(",");
        List<String> searchDescriptions = new ArrayList<>();
        for (String description : descriptions) {
            if (productDescription.contains(description.toLowerCase())) {
                searchDescriptions.add(description);
            }
        }
        return String.join(" ", searchDescriptions);
    }

    default void generateOtherData (Product product) {
            String category = getCategory(product.getName(), product.getDescription());
            String searchIngredients = getSearchIngredient(product.getIngredients());
            String searchDescription = getSearchDescription(product.getDescription());
            product.setCategory(category);
            product.setSearchIngredients(searchIngredients);
            product.setSearchDescription(searchDescription);
    }

    default String getCategory(String title, String description) {
        String[] categories = { "shampoo", "cream", "creme", "mask", "masque", "edge control", "gel", "hair milk",
                "condition", "co-wash", "co wash", "cowash", "oil", "relaxer" };
        title = title == null ? "" :  title.toLowerCase();
        description = description == null ? "" : description.toLowerCase();

        for (String category : categories) {
            if (title.contains(category) || description.contains(category)) {
                if (category.equals("creme")) {
                    return "cream";
                }
                if (category.equals("masque")) {
                    return "mask";
                }
                if (category.equals("cowash") || category.equals("co wash")) {
                    return "co-wash";
                }
                if (category.equals("hair milk")) {
                    return "leave-in conditioner";
                }
                if (category.equals("condition")) {
                    if (title.contains("deep condition") || description.contains("deep condition")) {
                        return "deep conditioner";
                    }
                    if (title.contains("leave-in") || description.contains("leave-in") || title.contains("leave in")
                            || description.contains("leave in")) {
                        return "leave-in conditioner";
                    }
                    return "conditioner";
                }

                return category;
            }
        }
        return "other";
    }
}
