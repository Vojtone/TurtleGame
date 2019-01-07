package game.model.command;

import game.model.Board;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class TurnLeftCommandTest {

    @Mock
    private Board mockB;

    private TurnLeftCommand turnLeftCommandUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        turnLeftCommandUnderTest = new TurnLeftCommand(mockB);
    }

    @Test
    public void testExecute() {
        // Setup
        final boolean expectedResult = false;

        // Run the test
        final boolean result = false;

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetName() {
        // Setup
        final String expectedResult = "Turn left";

        // Run the test
        final String result = turnLeftCommandUnderTest.getName();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetImageURL() {
        // Setup
        final String expectedResult = "./src/main/resources/images/left.png";

        // Run the test
        final String result = turnLeftCommandUnderTest.getImageURL();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
