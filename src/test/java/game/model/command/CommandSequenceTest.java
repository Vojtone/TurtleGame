package game.model.command;

import game.model.Board;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class CommandSequenceTest {

    @Mock
    private Board mockB;

    private CommandSequence commandSequenceUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        commandSequenceUnderTest = new CommandSequence(mockB);
    }

    @Test
    public void testAddCommand() {
        // Setup
        final ITurtleCommand command = null;

        // Run the test
        commandSequenceUnderTest.addCommand(command);

        // Verify the results
    }

    @Test
    public void testRemoveLastCommand() {
        // Setup

        // Run the test
        commandSequenceUnderTest.removeLastCommand();

        // Verify the results
    }

    @Test
    public void testRemoveCommand() {
        // Setup
        final int index = 0;

        // Run the test
        commandSequenceUnderTest.removeCommand(index);

        // Verify the results
    }

    @Test
    public void testGetSize() {
        // Setup
        final int expectedResult = 0;

        // Run the test
        final int result = commandSequenceUnderTest.getSize();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testClear() {
        // Setup

        // Run the test
        commandSequenceUnderTest.clear();

        // Verify the results
    }

    @Test
    public void testExecute() {
        // Setup
        final String expectedResult = "You didn't visited all fields :(";

        // Run the test
        final String result = commandSequenceUnderTest.execute();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
