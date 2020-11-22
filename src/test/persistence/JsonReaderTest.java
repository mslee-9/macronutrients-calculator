package persistence;

import model.DailyMacros;
import model.Exceptions.InvalidWeekIndexException;
import model.WeeklySummary;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WeeklySummary ws = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // do nothing; should pass
        }
    }

    @Test
    void testReaderWeeklySummary() {
        JsonReader reader = new JsonReader("./data/testReaderWeeklySummary.json");
        try {
            WeeklySummary ws = reader.read();
            assertEquals("My weekly summary", ws.getName());
            checkDailyMacros(2000, 200, 150, 50, ws.getDailyMacro(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidWeekIndexException e) {
            fail();
        }
    }

}