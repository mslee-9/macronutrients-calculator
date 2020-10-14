package model;

import java.util.ArrayList;

public class WeeklySummary {

    private ArrayList<DailyMacros> sevenDayRecord;

    //EFFECTS: construct new weekly summary with seven entries of DailyMacros for sevenDayRecord
    public WeeklySummary() {
        sevenDayRecord = new ArrayList();

        for (int i = 0; i < 7; i++) {
            sevenDayRecord.add(new DailyMacros());
        }
    }

    //REQUIRES: index must be between and including 0 and 6
    //EFFECTS: returns DailyMacros instance at corresponding index of sevendayRecord
    public DailyMacros getDailyMacro(int index) {
        return sevenDayRecord.get(index);
    }
}
