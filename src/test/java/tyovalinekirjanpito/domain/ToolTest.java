
package tyovalinekirjanpito.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ToolTest {
    
    Tool tool;
    
    @Before
    public void setUp() {
        tool = new Tool("hammer");
    }
    
    @Test
    public void theNewToolExists() {
        assertTrue(tool != null);
    }
    
    @Test
    public void differentToolsAreNotEquals() {
        Tool screwdriver = new Tool("screwdriver");
        assertFalse(tool.equals(screwdriver));
    }
    
    @Test
    public void toolsWithTheSameNameAreRecognizedAsSame() {
        Tool hammer = new Tool("hammer");
        assertTrue(tool.equals(hammer));
    }
}
