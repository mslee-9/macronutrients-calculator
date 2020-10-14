package model;

public class DailyMacros {
    private static int calPerGramCarb = 4;
    private static int calPerGramProtein = 4;
    private static int calPerGramFat = 9;

    private int calorieGoals;

    private double carbsGoalPercentage;
    private double proteinGoalPercentage;
    private double fatGoalPercentage;

    private int carbsGoalGrams;
    private int proteinGoalGrams;
    private int fatGoalGrams;

    private int carbsConsumed;
    private int proteinConsumed;
    private int fatConsumed;

    //EFFECTS: constructs an instance of DailyMacros with default settings of 0
    public DailyMacros() {
        this.carbsConsumed = 0;
        this.proteinConsumed = 0;
        this.fatConsumed = 0;
        this.carbsGoalGrams = 0;
        this.proteinGoalGrams = 0;
        this.fatGoalGrams = 0;
    }

    //MODIFIES: this
    //EFFECTS: calculates macro goals according to inputted percentages, and sets goals by grams
    public String calculateMacroGoals() {
        carbsGoalGrams = (int) Math.round((calorieGoals * carbsGoalPercentage) / calPerGramCarb);
        proteinGoalGrams = (int) Math.round((calorieGoals * proteinGoalPercentage) / calPerGramProtein);
        fatGoalGrams = (int) Math.round((calorieGoals * fatGoalPercentage) / calPerGramFat);

        return "Carbs:" + carbsGoalGrams + "Protein:" + proteinGoalGrams + "Fat:" + fatGoalGrams;
    }

//    public String compareGoals() {
//        int totalCalories = carbsConsumed * calPerGramCarb + proteinConsumed * calPerGramProtein + fatConsumed * calPerGramProtein;
//        int calorieDifference = calorieGoals - totalCalories;
//
//    }

    public int getCalorieGoal() {
        return calorieGoals;
    }

    public int getCarbsGrams() {
        return carbsGoalGrams;
    }

    public int getProteinGrams() {
        return proteinGoalGrams;
    }

    public int getFatGrams() {
        return fatGoalGrams;
    }

    public void setCalorieGoal(int calorieGoal) {
        calorieGoals = calorieGoal;
    }

    //MODIFIES: this
    //EFFECTS:
    public void setCarbsGoalsPercentage(double carbsGoal) {
        carbsGoalPercentage = carbsGoal;
    }

    //MODIFIES: this
    //EFFECTS:
    public void setProteinGoalsPercentage(double proteinGoal) {
        proteinGoalPercentage = proteinGoal;
    }

    //MODIFIES: this
    //EFFECTS:
    public void getFatGaolsPercentage(double fatGoal) {
        fatGoalPercentage = fatGoal;
    }
}
