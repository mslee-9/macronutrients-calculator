package persistence;

import model.DailyMacros;
import model.WeeklySummary;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//SOURCE: JsonSerializationDemo - this class was built based on JsonSerializationDemo
// Represents a reader that reads DailyMacros from JSON data stored in file
public class JsonReader {
    private String source;

    //EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads weekly summary from file and returns it;
    //throws IOException if an error occurs reading data from file
    public WeeklySummary read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWeeklySummary(jsonObject);
    }

    //EFFECTS: reads source file and returns it as string
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses weekly summary from JSON object and returns it
    private WeeklySummary parseWeeklySummary(JSONObject jsonObject) {
        String name = jsonObject.getString("name & week");
        WeeklySummary ws = new WeeklySummary(name);
        addSevenDayRecord(ws, jsonObject);
        return ws;
    }

    //MODIFIES: ws
    // EFFECTS: parses seven day record from JSON object and adds them to weekly Summary
    private void addSevenDayRecord(WeeklySummary ws, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("weekly record");
        for (int i = 0; i < 7; i++) {
            JSONObject nextDailyMacros = (JSONObject) jsonArray.get(i);
            addDailyMacros(ws, nextDailyMacros, i);
        }
    }

    // MODIFIES: ws
    // EFFECTS: parses daily macros from JSON object and adds it to weekly summary
    private void addDailyMacros(WeeklySummary ws, JSONObject jsonObject, int day) {
        int calGoal = jsonObject.getInt("calorie goal");
        int carbsGoal = jsonObject.getInt("carbs goal (g)");
        int proteinGoal = jsonObject.getInt("protein goal (g)");
        int fatGoal = jsonObject.getInt("fat goal (g)");
        int caloriesConsumed = jsonObject.getInt("calories consumed");
        int carbsConsumed = jsonObject.getInt("carbs consumed (g)");
        int proteinConsumed = jsonObject.getInt("protein consumed (g)");
        int fatConsumed = jsonObject.getInt("fat consumed (g)");

        DailyMacros dm = new DailyMacros();
        dm.setCalorieGoal(calGoal);
        dm.setCarbsGoalsGrams(carbsGoal);
        dm.setProteinGoalsGrams(proteinGoal);
        dm.setFatGoalsGrams(fatGoal);
        dm.setCaloriesConsumed(caloriesConsumed);
        dm.setCarbsConsumed(carbsConsumed);
        dm.setProteinConsumed(proteinConsumed);
        dm.setFatConsumed(fatConsumed);
        ws.getSevenDayRecord().set(day,dm);
    }
}
