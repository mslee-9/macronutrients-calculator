package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DailyMacrosTest {

    DailyMacros testDailyMacros;

    @BeforeEach
    void setup() {
        testDailyMacros = new DailyMacros();
        testDailyMacros.setCalorieGoal(2000);
    }

    @Test
    void testCalculateMacroGoalsTypical() {
        testDailyMacros.setCarbsGoalsPercentage(0.4);
        testDailyMacros.setProteinGoalsPercentage(0.3);
        testDailyMacros.setFatGoalsPercentage(0.3);

        testDailyMacros.calculateMacroGoals();

        assertEquals(200, testDailyMacros.getCarbsGoalGrams());
        assertEquals(150, testDailyMacros.getProteinGoalGrams());
        assertEquals(67, testDailyMacros.getFatGoalGrams());
    }

    @Test
    void testCompareCalorieGoals() {
        testDailyMacros.addCarbsConsumed(250);
        testDailyMacros.addProteinConsumed(140);
        testDailyMacros.addFatConsumed(60);

        int consumed = testDailyMacros.getTotalCaloriesConsumed();
        int goal = testDailyMacros.getCalorieGoal();
        int difference = consumed - goal;
        assertEquals(difference, testDailyMacros.compareCalorieGoals());
    }

    @Test
    void testAddCarbsConsumed() {
        assertEquals(0, testDailyMacros.getCarbsConsumedGrams());

        testDailyMacros.addCarbsConsumed(0);
        assertEquals(0, testDailyMacros.getCarbsConsumedGrams());

        testDailyMacros.addCarbsConsumed(2);
        assertEquals(2, testDailyMacros.getCarbsConsumedGrams());

        testDailyMacros.addCarbsConsumed(150);
        assertEquals(152, testDailyMacros.getCarbsConsumedGrams());
    }

    @Test
    void testAddProteinConsumed() {
        assertEquals(0, testDailyMacros.getProteinConsumedGrams());

        testDailyMacros.addProteinConsumed(0);
        assertEquals(0, testDailyMacros.getProteinConsumedGrams());

        testDailyMacros.addProteinConsumed(2);
        assertEquals(2, testDailyMacros.getProteinConsumedGrams());

        testDailyMacros.addProteinConsumed(150);
        assertEquals(152, testDailyMacros.getProteinConsumedGrams());
    }

    @Test
    void testAddFatConsumed() {
        assertEquals(0, testDailyMacros.getFatConsumedGrams());

        testDailyMacros.addFatConsumed(0);
        assertEquals(0, testDailyMacros.getFatConsumedGrams());

        testDailyMacros.addFatConsumed(2);
        assertEquals(2, testDailyMacros.getFatConsumedGrams());

        testDailyMacros.addFatConsumed(150);
        assertEquals(152, testDailyMacros.getFatConsumedGrams());
    }

    @Test
    void testFormatNicely() {
        String formatted = testDailyMacros.formatNicely();
        String s = "";
        s += "\tCalorie Goal: " + testDailyMacros.getCalorieGoal()
                + " | Calories consumed: " + testDailyMacros.getTotalCaloriesConsumed();
        s += "\n\tCarbs Goal: " + testDailyMacros.getCarbsGoalGrams()
                + "   | Carbs consumed: " + testDailyMacros.getCarbsConsumedGrams();
        s += "\n\tProtein Goal: " + testDailyMacros.getProteinGoalGrams()
                + " | Protein consumed: " + testDailyMacros.getProteinConsumedGrams();
        s += "\n\tFat Goal: " + testDailyMacros.getFatGoalGrams()
                + "     | Fat consumed: " + testDailyMacros.getFatConsumedGrams();
        s += "\n";
        assertEquals(s, formatted);
    }
}