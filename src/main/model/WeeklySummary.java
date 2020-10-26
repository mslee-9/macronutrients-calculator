package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a collection of records of each day in a week from Monday through Sunday
public class WeeklySummary implements Writable {
    private String name;
    private ArrayList<DailyMacros> sevenDayRecord;

    //EFFECTS: construct new weekly summary with seven instances of DailyMacros
    //         representing seven days in a week Monday through Sunday
    public WeeklySummary(String name) {
        this.name = name;
        sevenDayRecord = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            sevenDayRecord.add(new DailyMacros());
        }
    }

    //REQUIRES: 0<= index <= 6
    //EFFECTS: returns DailyMacros instance at corresponding index from sevenDayRecord
    public DailyMacros getDailyMacro(int index) {
        return sevenDayRecord.get(index);
    }

    public ArrayList<DailyMacros> getSevenDayRecord() {
        return sevenDayRecord;
    }

    public String getName() {
        return name;
    }

    @Override
    //SOURCE: JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        //SOMETHING?? NAME??
        json.put("weekly record", dailyMacrosToJson());
        return json;
    }

    //SOURCE: JsonSerializationDemo
    // EFFECTS: returns Daily Macros in this WeeklySummary as a JSON array
    private JSONArray dailyMacrosToJson() {
        JSONArray jsonArray = new JSONArray();

        for (DailyMacros dm : sevenDayRecord) {
            jsonArray.put(dm.toJson());
        }

        return jsonArray;
    }
}
