package model;

import model.exceptions.InvalidWeekIndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeeklySummaryTest {

    WeeklySummary testWeeklySummary;

    @BeforeEach
    void setup() {
        testWeeklySummary = new WeeklySummary("Stella's Week 1");
    }

    @Test
    void testConstructor() {
        ArrayList<DailyMacros> testSevenDayRecord = testWeeklySummary.getSevenDayRecord();
        assertEquals("Stella's Week 1", testWeeklySummary.getName());
        assertEquals(7, testSevenDayRecord.size());
    }

    @Test
    void testGetDailyMacroExpectInvalidWeekIndexExceptionNegative() {
        try {
            DailyMacros indexNegativeOne = testWeeklySummary.getDailyMacro(-1);
            fail();
        } catch (InvalidWeekIndexException e) {
        }
    }

    @Test
    void testGetDailyMacroExpectInvalidWeekIndexExceptionPositive() {
        try {
            DailyMacros indexEight = testWeeklySummary.getDailyMacro(8);
            fail();
        } catch (InvalidWeekIndexException e) {
        }
    }

    @Test
    void testGetDailyMacroNoException() {
        try {
            DailyMacros indexZero = testWeeklySummary.getDailyMacro(0);
            indexZero.setCalorieGoal(2500);

            DailyMacros indexFour = testWeeklySummary.getDailyMacro(4);
            indexFour.setCalorieGoal(2000);

            DailyMacros indexSix = testWeeklySummary.getDailyMacro(6);
            indexSix.setCalorieGoal(1500);

            assertEquals(2500, testWeeklySummary.getDailyMacro(0).getCalorieGoal());
            assertEquals(2000, testWeeklySummary.getDailyMacro(4).getCalorieGoal());
            assertEquals(1500, testWeeklySummary.getDailyMacro(6).getCalorieGoal());
        } catch (InvalidWeekIndexException e) {
            fail();
        }
    }

    @Test
    void testGetWeeklySummaryNoException() {
        try {
            DailyMacros indexZero = testWeeklySummary.getDailyMacro(0);
            indexZero.setCalorieGoal(2500);

            DailyMacros indexFour = testWeeklySummary.getDailyMacro(4);
            indexFour.setCalorieGoal(2000);

            DailyMacros indexSix = testWeeklySummary.getDailyMacro(6);
            indexSix.setCalorieGoal(1500);

            List<DailyMacros> testListDM = Collections.unmodifiableList(testWeeklySummary.getSevenDayRecord());
            assertEquals(testListDM, testWeeklySummary.getWeeklySummaryList());
        } catch (InvalidWeekIndexException e) {
            fail();
        }
    }

}