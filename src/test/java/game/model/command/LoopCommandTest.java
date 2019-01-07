package game.model.command;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LoopCommandTest {

    private int i;
    private List<ITurtleCommand> commands;

    private LoopCommand loopCommandUnderTest;

    @Before
    public void setUp() {
        i = 0;
        commands = Arrays.asList();
        loopCommandUnderTest = new LoopCommand(i, commands);
    }

    @Test
    public void testExecute() {
        // Setup
        final boolean expectedResult = true;

        // Run the test
        final boolean result = loopCommandUnderTest.execute();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetName() {
        // Setup
        final String expectedResult = "Loop: [0]:()";

        // Run the test
        final String result = loopCommandUnderTest.getName();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetImageURL() {
        // Setup
        final String expectedResult = "./src/main/resources/images/loop.png";

        // Run the test
        final String result = loopCommandUnderTest.getImageURL();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetIterations() {
        // Setup
        final Integer expectedResult = 0;

        // Run the test
        final Integer result = loopCommandUnderTest.getIterations();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
