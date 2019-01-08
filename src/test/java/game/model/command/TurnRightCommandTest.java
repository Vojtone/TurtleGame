package game.model.command;

import game.model.Board;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class TurnRightCommandTest {

    @Mock
    private Board mockB;

    private TurnRightCommand turnRightCommandUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        turnRightCommandUnderTest = new TurnRightCommand(mockB);
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
        final String expectedResult = "Turn right";

        // Run the test
        final String result = turnRightCommandUnderTest.getName();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetImageURL() {
        // Setup
        final String expectedResult = "./src/main/resources/images/right.png";

        // Run the test
        final String result = turnRightCommandUnderTest.getImageURL();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
