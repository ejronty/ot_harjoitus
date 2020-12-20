
package tyovalinekirjanpito.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ToolTest {

    Tool tool;

    @Before
    public void setUp() {
        tool = new Tool("hammer", 1, false);
    }

    @Test
    public void theNewToolExists() {
        assertTrue(tool != null);
    }

    @Test
    public void differentToolsAreNotEquals() {
        Tool screwdriver = new Tool("screwdriver", 2, false);
        assertFalse(tool.equals(screwdriver));
    }

    // Tämä on ohjelman haluttu toimintatapa, eli yhdellä nimellä löytyy
    // vain yksi työväline.
    @Test
    public void toolsWithTheSameNameAreRecognizedAsSame() {
        Tool hammer = new Tool("hammer", 3, false);
        assertTrue(tool.equals(hammer));
    }

    @Test
    public void toStringReturnsTheNameOfTheTool() {
        assertEquals("hammer", tool.toString());
    }
}
