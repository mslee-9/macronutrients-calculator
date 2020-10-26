package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents a day's calories & macronutrient goals and records
public class DailyMacros implements Writable {
    private static final int calPerGramCarb = 4;
    private static final int calPerGramProtein = 4;
    private static final int calPerGramFat = 9;

    private int calorieGoals;
    private double carbsGoalPercentage;
    private double proteinGoalPercentage;
    private double fatGoalPercentage;

    private int carbsGoalGrams;
    private int proteinGoalGrams;
    private int fatGoalGrams;

    private int totalCaloriesConsumed;
    private int carbsConsumed;
    private int proteinConsumed;
    private int fatConsumed;

    //EFFECTS: constructs an instance of DailyMacros with default settings of 0
    public DailyMacros() {
        calorieGoals = 0;
        carbsGoalPercentage = 0;
        proteinGoalPercentage = 0;
        fatGoalPercentage = 0;

        carbsGoalGrams = 0;
        proteinGoalGrams = 0;
        fatGoalGrams = 0;

        totalCaloriesConsumed = 0;
        carbsConsumed = 0;
        proteinConsumed = 0;
        fatConsumed = 0;
    }

    //MODIFIES: this
    //EFFECTS: calculates macro goals in grams according to inputted target breakdown in percentages,
    //         and sets goals to nearest integer in grams
    public void calculateMacroGoals() {
        carbsGoalGrams = (int) Math.round((calorieGoals * carbsGoalPercentage) / calPerGramCarb);
        proteinGoalGrams = (int) Math.round((calorieGoals * proteinGoalPercentage) / calPerGramProtein);
        fatGoalGrams = (int) Math.round((calorieGoals * fatGoalPercentage) / calPerGramFat);
    }

    //EFFECTS: returns the difference between actual calories consumed and calorie goal
    public int compareCalorieGoals() {
        return totalCaloriesConsumed - calorieGoals;
    }

    //REQUIRES: grams >= 0
    //MODIFIES: this
    //EFFECTS: adds amount to total carbs amount consumed and adds to total calories consumed
    public void addCarbsConsumed(int grams) {
        carbsConsumed += grams;
        totalCaloriesConsumed += grams * calPerGramCarb;
    }

    //REQUIRES: grams >= 0
    //MODIFIES: this
    //EFFECTS: adds amount to total protein amount consumed and adds to total calories consumed
    public void addProteinConsumed(int grams) {
        proteinConsumed += grams;
        totalCaloriesConsumed += grams * calPerGramProtein;
    }

    //REQUIRES: grams >= 0
    //MODIFIES: this
    //EFFECTS: adds amount to total fat amount consumed and adds to total calories consumed
    public void addFatConsumed(int grams) {
        fatConsumed += grams;
        totalCaloriesConsumed += grams * calPerGramFat;
    }

    public int getTotalCaloriesConsumed() {
        return totalCaloriesConsumed;
    }

    public int getCalorieGoal() {
        return calorieGoals;
    }

    public int getCarbsGoalGrams() {
        return carbsGoalGrams;
    }

    public int getProteinGoalGrams() {
        return proteinGoalGrams;
    }

    public int getFatGoalGrams() {
        return fatGoalGrams;
    }

    public int getCarbsConsumedGrams() {
        return carbsConsumed;
    }

    public int getProteinConsumedGrams() {
        return proteinConsumed;
    }

    public int getFatConsumedGrams() {
        return fatConsumed;
    }

    public void setCalorieGoal(int calorieGoal) {
        calorieGoals = calorieGoal;
    }

    public void setCarbsGoalsPercentage(double carbsGoal) {
        carbsGoalPercentage = carbsGoal;
    }

    public void setProteinGoalsPercentage(double proteinGoal) {
        proteinGoalPercentage = proteinGoal;
    }

    public void setFatGoalsPercentage(double fatGoal) {
        fatGoalPercentage = fatGoal;
    }

    public void setCarbsGoalsGrams(int carbsGoal) {
        carbsGoalGrams = carbsGoal;
    }

    public void setProteinGoalsGrams(int proteinGoal) {
        proteinGoalGrams = proteinGoal;
    }

    public void setFatGoalsGrams(int fatGoal) {
        fatGoalGrams = fatGoal;
    }

    public void setCaloriesConsumed(int calorieGoal) {
        calorieGoals = calorieGoal;
    }

    public void setCarbsConsumed(int carbs) {
        carbsConsumed = carbs;
    }

    public void setProteinConsumed(int protein) {
        proteinConsumed = protein;
    }

    public void setFatConsumed(int fat) {
        fatConsumed = fat;
    }

    @Override
    //SOURCE: JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("calorie goal", calorieGoals);
        json.put("carbs goal (g)", carbsGoalGrams);
        json.put("protein goal (g)", proteinGoalGrams);
        json.put("fat goal (g)", fatGoalGrams);
        json.put("calories consumed", totalCaloriesConsumed);
        json.put("carbs consumed (g)", carbsConsumed);
        json.put("protein consumed (g)", proteinConsumed);
        json.put("fat consumed (g)", fatConsumed);
        return json;
    }
}