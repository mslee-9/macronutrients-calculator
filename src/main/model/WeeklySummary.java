package model;

import java.util.ArrayList;

public class WeeklySummary {

    private ArrayList<DailyMacros> sevenDayRecord;

    //EFFECTS: construct new weekly summary with seven instances of DailyMacros
    //         representing seven days in a week
    public WeeklySummary() {
        sevenDayRecord = new ArrayList();

        for (int i = 0; i < 7; i++) {
            sevenDayRecord.add(new DailyMacros());
        }
    }

    //REQUIRES: 0<= index <= 6
    //EFFECTS: returns DailyMacros instance at corresponding index of sevenDayRecord
    public DailyMacros getDailyMacro(int index) {
        return sevenDayRecord.get(index);
    }
}
