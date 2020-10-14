package model;

import java.util.ArrayList;

// Represents a collection of records of each day in a week from Monday through Sunday
public class WeeklySummary {

    private ArrayList<DailyMacros> sevenDayRecord;

    //EFFECTS: construct new weekly summary with seven instances of DailyMacros
    //         representing seven days in a week Monday through Sunday
    public WeeklySummary() {
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
}
