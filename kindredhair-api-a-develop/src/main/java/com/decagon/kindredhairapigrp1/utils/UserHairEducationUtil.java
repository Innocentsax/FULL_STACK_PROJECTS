package com.decagon.kindredhairapigrp1.utils;

import com.decagon.kindredhairapigrp1.DTO.AnswerResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserHairEducationUtil {

    private   String convertAnswersToString(List<String> answer){
        return answer.stream().map(str -> str.trim().toLowerCase()).collect(Collectors.joining(","));
    }
    /*
   Method to get all user hair education.
   Method takes in a DTO with user's answer to the various questions
    */
    public List<String> getUserHairEducation(AnswerResponseDTO answer) {
        List<String> userEducation = new ArrayList<>();

        //allergies
        if (answer.getAllergies() != null){
            String allergies = getEducation("allergies");
            userEducation.add(allergies);
        }

        //describe
        String describe = getDescribeEducation(convertAnswersToString(answer.getDescribe()));
        if (describe != null)
            userEducation.add(describe);

        //what else
        String whatElse = convertAnswersToString(answer.getWhatElse());
        List<String> whatElseEducationList = getUserWhatElseEducation(whatElse);
        userEducation.addAll(whatElseEducationList);

        //porosity
        String porosity = getUserPorosityEducation(answer.getPorosity());
        if (porosity != null)
            userEducation.add(porosity);

        //styles
        String styles = convertAnswersToString(answer.getStyles());
        List<String> styleEducationList = getUserStyleEducation(styles);
        userEducation.addAll(styleEducationList);

        return userEducation;
    }


    List<String> getUserStyleEducation(String answer) {
        List<String> userStyleEducationList = new ArrayList<>();

        answer = answer.toLowerCase();

        if (answer.contains("heat")) {
            userStyleEducationList.add(getEducation("heat"));
        }
        if (answer.matches(".*(crochet|weave|wig).*")) {
            userStyleEducationList.add(getEducation("protective styles"));
        }
        if (answer.contains("braids") && !answer.matches(".*(crochet|weave|wig).*")) {
            userStyleEducationList.add(getEducation("braids"));
        }

        return userStyleEducationList;
    }

    String getDescribeEducation(String answer) {
        answer = answer.replaceAll("fine|healthy", "").replaceAll(",+", ",")
                .replaceAll(",$", "").toLowerCase();

        String describe = null;

        if (answer.split(",").length > 2) {
            answer = answer.replaceAll(",", ", ");
            describe = "You say you have " + answer + " hair (phew!). We hear you. " +
                    "Let's get you products with nourishing oils like avocado, " +
                    "coconut, and argan to heal and rejuvenate your strands.";
        }
        return describe;
    }

    String getUserPorosityEducation(String answer) {
        String porosityEducation = null;
        if (answer.contains("3") && !answer.contains("1")) {
            porosityEducation = getEducation("low porosity");
        }
        if (answer.contains("1") && !answer.contains("3")) {
            porosityEducation = getEducation("high porosity");
        }
        return porosityEducation;
    }

    List<String> getUserWhatElseEducation(String answer) {

        answer = answer.toLowerCase();
        List<String> userWhatElseEducationList = new ArrayList<>();

        boolean dandruffIsPartOfCombination = false;
        boolean dyeIsPartOfCombination = false;
        boolean exerciseIsPartOfCombination = false;

        if (answer.contains("exercise") && answer.contains("dandruff")) {
            userWhatElseEducationList.add(getEducation("exercise & dandruff"));
            dandruffIsPartOfCombination = true;
            exerciseIsPartOfCombination = true;
        }
        if (answer.contains("dye") && answer.contains("dandruff") && !answer.contains("exercise")) {
            userWhatElseEducationList.add(getEducation("dyes & dandruff"));
            dandruffIsPartOfCombination = true;
            dyeIsPartOfCombination = true;
        }
        if (answer.contains("dandruff") && !dandruffIsPartOfCombination) {
            userWhatElseEducationList.add(getEducation("dandruff"));
        }
        if (answer.contains("dye") && !dyeIsPartOfCombination) {
            userWhatElseEducationList.add(getEducation("dyed hair"));
        }
        if (answer.contains("exercise") && !exerciseIsPartOfCombination) {
            userWhatElseEducationList.add(getEducation("exercise"));
        }
        if (answer.contains("transitioning")) {
            userWhatElseEducationList.add(getEducation("transitioning"));
        }
        if (answer.contains("allergies")) {
            userWhatElseEducationList.add(getEducation("allergies"));
        }
        if (answer.contains("thin hair")) {
            userWhatElseEducationList.add(getEducation("thin hair"));
        }
        if (answer.contains("relaxers")) {
            userWhatElseEducationList.add(getEducation("relaxers"));
        }
        if (answer.contains("tender scalp")) {
            userWhatElseEducationList.add(getEducation("tender scalp"));
        }

        return userWhatElseEducationList;
    }


    String getEducation(String string) {
        String allEducation = "dandruff@Fight pesky, flaky dandruff with vitamin rich products. Products packed with jojoba and almond all work to soften and release dandruff in a healthy way.\n" +
                "dyed hair@Dying your hair is fun but very dehydrating. Let's make sure you're using products that protect your color treated hair and keep your hair from getting overly dry and dull. These products have ingredients like avocado and baobab to keep your hair at maximum health and strength.\n" +
                "high porosity@Looks like you've got high porosity hair which means that your hair drinks in moisture easily, but can also lose moisture just as easily. You need generous doses of protein and vitamin rich products that will infuse your hair with moisture and strengthen your strands.\n" +
                "low porosity@Looks like you've got low porosity hair. Your hair is healthy but it easily repels moisture. You need healthy doses of humectants - like honey - to infuse moisture into your hair strands and keep helpful ingredients there.\n" +
                "transitioning@We love a good transition story! Make sure you are getting regular trims to keep your hair nice and strong and ready for long-term growth. We'll show you products that help you hair soften and grow faster.\n" +
                "protective styles@Braids, weaves and wigs, Oh my! We love protective styles too. You'll need healing nutrients like grapeseed and avocado oils that will keep your edges and strands healthy and primed to last through different styles.\n" +
                "braids@Braids will never get old. But! You need products with great oils that will keep your healthy and edges strong and withstand the weight of these beautiful but sometimes damaging extensions.\n" +
                "allergies@To keep your body safe and strong during allergy season, its best to rotate the products you use - that way you allow yourself breaks from different ingredients while enjoying their benefits.\n" +
                "thin hair@Let's get you products with nourishing oils like sunflower seed, jojoba and avocado to fortify, repair and strengthen your hair.\n" +
                "heat@Nothing wrong with a blow out or press every now and then. Always apply heat protectors before you use heat. You want your hair looking chic and sleek not sizzled and fizzled.\n" +
                "relaxers@Protein all day - Using relaxers makes your hair more prone to breakage. You need heavy doses of protein and vitamin rich products that will infuse your hair with moisture and strengthen your strands.\n" +
                "tender scalp@Make sure to look out for healing ingredients with soothing properties like aloe vera and coconut - they'll work to reduce inflammation, moisturize your scalp and protect your strands during styling.\n" +
                "exercise@We love that you get moving! It stimulates blood flow which helps hair growth. To keep your hair from wearing out quickly, you need products that refresh your scalp to prevent dryness and breakage.\n" +
                "dyes & dandruff@Let's make sure you're using products that attack dandruff and protect your color treated hair. These products have ingredients like avocado oil, apple cider vinegar and tea tree oil to keep your hair at maximum health.\n" +
                "exercise & dandruff@Exercise - We love that you get moving! It stimulates blood flow which helps hair growth. To keep your hair from wearing out quickly, you need products that refresh your scalp to prevent dryness and breakage and actively fight dandruff";


        Map<String, String> allEducationMap = getEducationMap(allEducation);

        return allEducationMap.get(string);
    }

    static Map<String, String> getEducationMap(String string) {
        String[] list = string.split("\n");
        Map<String, String> map = new HashMap<>();
        for (String str : list) {
            String[] arr = str.split("@");
            map.put(arr[0], arr[1]);
        }
        return map;
    }
}
