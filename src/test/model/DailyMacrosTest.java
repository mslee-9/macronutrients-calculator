package model;

import model.Exceptions.InvalidAmountException;
import model.Exceptions.InvalidWeekIndexException;
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
    void testCompareCalorieGoalsNoExceptionThrown() {
        try {
            testDailyMacros.addCarbsConsumed(250);
            testDailyMacros.addProteinConsumed(140);
            testDailyMacros.addFatConsumed(60);
        } catch (InvalidAmountException e) {
            fail();
        }

        int consumed = testDailyMacros.getTotalCaloriesConsumed();
        int goal = testDailyMacros.getCalorieGoal();
        int difference = consumed - goal;
        assertEquals(difference, testDailyMacros.compareCalorieGoals());
    }

    @Test
    void testCompareCalorieGoalsExceptionExpectedNegativeCarbs() {
        try {
            testDailyMacros.addCarbsConsumed(-1);
            testDailyMacros.addProteinConsumed(140);
            testDailyMacros.addFatConsumed(60);
            fail();
        } catch (InvalidAmountException e) {
        }
    }

    @Test
    void testCompareCalorieGoalsExceptionExpectedNegativeProtein() {
        try {
            testDailyMacros.addCarbsConsumed(250);
            testDailyMacros.addProteinConsumed(-1);
            testDailyMacros.addFatConsumed(60);
            fail();
        } catch (InvalidAmountException e) {
        }
    }

    @Test
    void testCompareCalorieGoalsExceptionExpectedNegativeFat() {
        try {
            testDailyMacros.addCarbsConsumed(250);
            testDailyMacros.addProteinConsumed(140);
            testDailyMacros.addFatConsumed(-1);
            fail();
        } catch (InvalidAmountException e) {
        }
    }



    @Test
    void testAddCarbsConsumedNoException() {
        try {
            assertEquals(0, testDailyMacros.getCarbsConsumedGrams());

            testDailyMacros.addCarbsConsumed(0);
            assertEquals(0, testDailyMacros.getCarbsConsumedGrams());

            testDailyMacros.addCarbsConsumed(2);
            assertEquals(2, testDailyMacros.getCarbsConsumedGrams());

            testDailyMacros.addCarbsConsumed(150);
            assertEquals(152, testDailyMacros.getCarbsConsumedGrams());
        } catch (InvalidAmountException e) {
            fail();
        }
    }

    @Test
    void testAddCarbsConsumedInvalidAmountExceptionExpected() {
        try {
            assertEquals(0, testDailyMacros.getCarbsConsumedGrams());

            testDailyMacros.addCarbsConsumed(-1);
            fail();
        } catch (InvalidAmountException e) {
        }
    }

    @Test
    void testAddProteinConsumedNoException() {
        try {
            assertEquals(0, testDailyMacros.getProteinConsumedGrams());

            testDailyMacros.addProteinConsumed(0);
            assertEquals(0, testDailyMacros.getProteinConsumedGrams());

            testDailyMacros.addProteinConsumed(2);
            assertEquals(2, testDailyMacros.getProteinConsumedGrams());

            testDailyMacros.addProteinConsumed(150);
            assertEquals(152, testDailyMacros.getProteinConsumedGrams());
        } catch (InvalidAmountException e) {
            fail();
        }
    }

    @Test
    void testAddProteinConsumedInvalidAmountExceptionExpected() {
        try {
            assertEquals(0, testDailyMacros.getProteinConsumedGrams());

            testDailyMacros.addProteinConsumed(-1);
            fail();
        } catch (InvalidAmountException e) {
        }
    }

    @Test
    void testAddFatConsumedNoException() {
        try {
            assertEquals(0, testDailyMacros.getFatConsumedGrams());

            testDailyMacros.addFatConsumed(0);
            assertEquals(0, testDailyMacros.getFatConsumedGrams());

            testDailyMacros.addFatConsumed(2);
            assertEquals(2, testDailyMacros.getFatConsumedGrams());

            testDailyMacros.addFatConsumed(150);
            assertEquals(152, testDailyMacros.getFatConsumedGrams());
        } catch (InvalidAmountException e) {
            fail();
        }
    }

    @Test
    void testAddFatConsumedExpectInvalidAmountException() {
        try {
            assertEquals(0, testDailyMacros.getFatConsumedGrams());

            testDailyMacros.addFatConsumed(-1);
            fail();
        } catch (InvalidAmountException e) {
        }
    }

    @Test
    void testFormatNicely() {
        String formatted = testDailyMacros.formatNicely();

        String s = "<html>";
        s += "<br/>" + "Calorie Goal: " + testDailyMacros.getCalorieGoal() + " | Calories consumed: " + testDailyMacros.getTotalCaloriesConsumed();
        s += "<br/>Carbs Goal: " + testDailyMacros.getCarbsGoalGrams() + "   | Carbs consumed: " + testDailyMacros.getCarbsConsumedGrams();
        s += "<br/>Protein Goal: " + testDailyMacros.getProteinGoalGrams() + " | Protein consumed: " + testDailyMacros.getProteinConsumedGrams();
        s += "<br/>Fat Goal: " + testDailyMacros.getFatGoalGrams() + "     | Fat consumed: " + testDailyMacros.getFatConsumedGrams();
        s += "<br/></html>";

        assertEquals(s, formatted);
    }
}