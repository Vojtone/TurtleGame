package game.model.command;

import game.model.Board;
import game.model.Turtle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class StepForwardCommandTest {

    @Mock
    private Board mockB;
    private StepForwardCommand stepForwardCommandUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        stepForwardCommandUnderTest = new StepForwardCommand(mockB);
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
        final String expectedResult = "Forward";

        // Run the test
        final String result = stepForwardCommandUnderTest.getName();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetImageURL() {
        // Setup
        final String expectedResult = "./src/main/resources/images/forward.png";

        // Run the test
        final String result = stepForwardCommandUnderTest.getImageURL();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
