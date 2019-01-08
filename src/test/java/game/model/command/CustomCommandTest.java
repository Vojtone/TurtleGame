package game.model.command;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomCommandTest {

    private List<ITurtleCommand> commands;

    private CustomCommand customCommandUnderTest;

    @Before
    public void setUp() {
        commands = Arrays.asList();
        customCommandUnderTest = new CustomCommand(commands);
    }

    @Test
    public void testExecute() {
        // Setup
        final boolean expectedResult = true;

        // Run the test
        final boolean result = customCommandUnderTest.execute();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetImageURL() {
        // Setup
        final String expectedResult = "./src/main/resources/images/loop.png";

        // Run the test
        final String result = customCommandUnderTest.getImageURL();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
