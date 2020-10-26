package persistence;

import model.DailyMacros;
import model.WeeklySummary;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads DailyMacros from JSON data stored in file
public class JsonReader {
    private String source;

    //EFFECTS:
    public JsonReader(String source) {
        this.source = source;
    }

    public WeeklySummary read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWeeklySummary(jsonObject);
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private WeeklySummary parseWeeklySummary(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        WeeklySummary ws = new WeeklySummary(name);
        addSevenDayRecord(ws, jsonObject);
        return ws;
    }

    private void addSevenDayRecord(WeeklySummary ws, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sevenDayRecord");
        for (Object json: jsonArray) {
            JSONObject nextDailyMacros = (JSONObject) json;
            addDailyMacros(ws, nextDailyMacros);
        }
    }

    private void addDailyMacros(WeeklySummary ws, JSONObject jsonObject) {
        int calGoal = jsonObject.getInt("calorie goal");
        int carbsGoal = jsonObject.getInt("carbs goal (g)");
        int proteinGoal = jsonObject.getInt("protein goal (g)");
        int fatGoal = jsonObject.getInt("fat goal (g)");
        int caloriesConsumed = jsonObject.getInt("calories consumed");
        int carbsConsumed = jsonObject.getInt("carbs consumed (g)");
        int proteinConsumed = jsonObject.getInt("protein consumed (g)");
        int fatConsumed = jsonObject.getInt("fat consumed (g)");

        DailyMacros dm = new DailyMacros();//PARAMETERS
        dm.setCalorieGoal(calGoal);
        dm.setCarbsGoalsGrams(carbsGoal);
        dm.setProteinGoalsGrams(proteinGoal);
        dm.setFatGoalsGrams(fatGoal);
        dm.setCaloriesConsumed(caloriesConsumed);
        dm.setCarbsConsumed(carbsConsumed);
        dm.setProteinConsumed(proteinConsumed);
        dm.setFatConsumed(fatConsumed);
        //ADD INTO WR
    }
}
