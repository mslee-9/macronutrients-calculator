package persistence;

import model.DailyMacros;
import model.exceptions.InvalidWeekIndexException;
import model.WeeklySummary;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            WeeklySummary ws = new WeeklySummary("My weekly summary");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //do nothing; should pass
        }
    }

    @Test
    void testWriterWeeklySummary() {
        try {
            WeeklySummary ws = new WeeklySummary("My weekly summary");
            DailyMacros monday = ws.getDailyMacro(0);
            monday.setCaloriesConsumed(2000);
            monday.setCarbsConsumed(200);
            monday.setProteinConsumed(150);
            monday.setFatConsumed(50);
            JsonWriter writer = new JsonWriter("./data/testWriterWeeklySummary.json");
            writer.open();
            writer.write(ws);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterWeeklySummary.json");
            ws = reader.read();
            assertEquals("My weekly summary", ws.getName());
            checkDailyMacros(2000, 200, 150, 50, monday);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (InvalidWeekIndexException e) {
            fail();
        }
    }
}