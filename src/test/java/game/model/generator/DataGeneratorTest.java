package game.model.generator;

import game.model.Board;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataGeneratorTest {

    @Test
    public void testGetHighestUnlocked() {
        // Setup
        final int expectedResult = 5;

        // Run the test
        final int result = DataGenerator.getHighestUnlocked();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testUnlockLevel() {
        // Setup
        final Integer level = 0;
        final boolean expectedResult = false;

        // Run the test
        final boolean result = DataGenerator.unlockLevel(level);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGenerateGameData() {
        // Setup
        final int lvl = 0;
        final Board board = null;
        final Board expectedResult = null;

        // Run the test
        final Board result = DataGenerator.generateGameData(lvl, board);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
