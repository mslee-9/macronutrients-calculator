package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a collection of records of each day in a week from Monday through Sunday
public class WeeklySummary implements Writable {
    private String name;
    private ArrayList<DailyMacros> sevenDayRecord;

    //EFFECTS: construct new weekly summary with name and seven instances of DailyMacros
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

    //EFFECTS: returns an unmodifiable list of sevenDayRecord in this weekly summary
    public List<DailyMacros> getWeeklySummaryList() {
        return Collections.unmodifiableList(sevenDayRecord);
    }

    public ArrayList<DailyMacros> getSevenDayRecord() {
        return sevenDayRecord;
    }

    public String getName() {
        return name;
    }

    @Override
    //SOURCE: JsonSerializationDemo - this method was built based on JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name & week", name);
        json.put("weekly record", dailyMacrosToJson());
        return json;
    }

    //SOURCE: JsonSerializationDemo - this method was built based on JsonSerializationDemo
    //EFFECTS: returns Daily Macros in this WeeklySummary as a JSON array
    private JSONArray dailyMacrosToJson() {
        JSONArray jsonArray = new JSONArray();

        for (DailyMacros dm : sevenDayRecord) {
            jsonArray.put(dm.toJson());
        }

        return jsonArray;
    }
}
