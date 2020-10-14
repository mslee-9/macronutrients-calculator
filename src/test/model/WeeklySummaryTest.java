package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WeeklySummaryTest {

    WeeklySummary testWeeklySummary;

    @BeforeEach
    void setup() {
         testWeeklySummary= new WeeklySummary();
    }

    @Test
    void testConstructor() {
        ArrayList<DailyMacros> testSevenDayRecord = testWeeklySummary.getSevenDayRecord();
        assertEquals(7, testSevenDayRecord.size());
    }

    @Test
    void testGetDailyMacro() {
        DailyMacros indexZero =  testWeeklySummary.getDailyMacro(0);
        indexZero.setCalorieGoal(2500);

        DailyMacros indexFour =  testWeeklySummary.getDailyMacro(4);
        indexFour.setCalorieGoal(2000);

        DailyMacros indexSix =  testWeeklySummary.getDailyMacro(6);
        indexSix.setCalorieGoal(1500);

        assertEquals(2500, testWeeklySummary.getDailyMacro(0).getCalorieGoal());
        assertEquals(2000, testWeeklySummary.getDailyMacro(4).getCalorieGoal());
        assertEquals(1500, testWeeklySummary.getDailyMacro(6).getCalorieGoal());
    }
}