package model;

import model.exceptions.InvalidAmountException;
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

    //MODIFIES: this
    //EFFECTS: adds amount to total carbs amount consumed and adds to total calories consumed
    //         throws InvalidAmountException if grams is less than 0
    public void addCarbsConsumed(int grams) throws InvalidAmountException {
        if (grams < 0) {
            throw new InvalidAmountException();
        }
        carbsConsumed += grams;
        totalCaloriesConsumed += grams * calPerGramCarb;
    }

    //MODIFIES: this
    //EFFECTS: adds amount to total protein amount consumed and adds to total calories consumed
    //         throws InvalidAmountException if grams is less than 0
    public void addProteinConsumed(int grams) throws InvalidAmountException {
        if (grams < 0) {
            throw new InvalidAmountException();
        }
        proteinConsumed += grams;
        totalCaloriesConsumed += grams * calPerGramProtein;
    }

    //MODIFIES: this
    //EFFECTS: adds amount to total fat amount consumed and adds to total calories consumed
    //         throws InvalidAmountException if grams is less than 0
    public void addFatConsumed(int grams) throws InvalidAmountException {
        if (grams < 0) {
            throw new InvalidAmountException();
        }
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

    public void setCaloriesConsumed(int caloriesConsumed) {
        totalCaloriesConsumed = caloriesConsumed;
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
    //SOURCE: JsonSerializationDemo - this method was built based on the toJSON method in JsonSerializationDemo
    //EFFECTS: constructs and returns new JSONObject
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

    //EFFECTS: returns a formatted list of daily macros record summary
    public String formatNicely() {
        String s = "<html>";
        s += "<br/>" + "Calorie Goal: " + calorieGoals + " | Calories consumed: " + totalCaloriesConsumed;
        s += "<br/>Carbs Goal: " + carbsGoalGrams + "   | Carbs consumed: " + carbsConsumed;
        s += "<br/>Protein Goal: " + proteinGoalGrams + " | Protein consumed: " + proteinConsumed;
        s += "<br/>Fat Goal: " + fatGoalGrams + "     | Fat consumed: " + fatConsumed;
        s += "<br/></html>";
        return s;
    }
}