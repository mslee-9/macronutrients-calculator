package persistence;

import model.DailyMacros;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkDailyMacros(int totalCalories, int carbs, int protein, int fat, DailyMacros dm) {
        assertEquals(totalCalories, dm.getTotalCaloriesConsumed());
        assertEquals(carbs, dm.getCarbsConsumedGrams());
        assertEquals(protein, dm.getProteinConsumedGrams());
        assertEquals(fat, dm.getFatConsumedGrams());
    }
}
