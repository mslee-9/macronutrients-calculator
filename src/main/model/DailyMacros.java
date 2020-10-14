package model;

public class DailyMacros {
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
    //EFFECTS: calculates macro goals in grams according to inputted goal breakdown in percentages,
    //         and sets goals to nearest integer in grams
    public void calculateMacroGoals() {
        carbsGoalGrams = (int) Math.round((calorieGoals * carbsGoalPercentage) / calPerGramCarb);
        proteinGoalGrams = (int) Math.round((calorieGoals * proteinGoalPercentage) / calPerGramProtein);
        fatGoalGrams = (int) Math.round((calorieGoals * fatGoalPercentage) / calPerGramFat);
    }

    //MODIFIES: this
    //EFFECTS: calculates and updates total calories consumed, and returns the difference
    //         between actual consumption and calorie goal
    public int compareCalorieGoals() {
        return totalCaloriesConsumed - calorieGoals;
    }

    //REQUIRES: grams >= 0
    //MODIFIES: this
    //EFFECTS: adds amount in grams of carbs to carbs amount consumed
    public void addCarbsConsumed(int grams) {
        carbsConsumed += grams;
        totalCaloriesConsumed += grams * calPerGramCarb;
    }

    //REQUIRES: grams >= 0
    //MODIFIES: this
    //EFFECTS: adds amount in grams of protein to protein amount consumed
    public void addProteinConsumed(int grams) {
        proteinConsumed += grams;
        totalCaloriesConsumed += grams * calPerGramProtein;
    }

    //REQUIRES: grams >= 0
    //MODIFIES: this
    //EFFECTS: adds amount in grams of fat to fat amount consumed
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
}