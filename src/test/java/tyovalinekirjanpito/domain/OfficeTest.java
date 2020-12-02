
package tyovalinekirjanpito.domain;


import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class OfficeTest {
    
    Office office;
    Tool tool;
    
    @Before
    public void setUp() {
        office = new Office("main office");
        tool = new Tool("hammer");
    }
    
    @Test
    public void theNewOfficeExists() {
        assertTrue(office != null);
    }
    
    @Test
    public void aNewOfficeHasNoTools() {
        assertEquals(0, office.getTools().size());
    }
    
    @Test
    public void addingToolsToAnOfficeWorks1() {
        office.addTool(tool);
        assertEquals(1, office.getTools().size());
    }
    
    @Test
    public void addingToolsToAnOfficeWorks2() {
        office.addTool(tool);
        assertTrue(office.getTools().contains(tool));
    }
    
    @Test
    public void itIsNotPossibleToAddTheSameToolTwice() {
        office.addTool(tool);
        office.addTool(tool);
        assertEquals(1, office.getTools().size());
    }
    
    @Test
    public void toStringReturnsTheNameOfTheOffice() {
        assertEquals("main office", office.toString());
    }
    
    @Test
    public void itIsPossibleToAddAListOfToolsToAnOfficeAtOnce() {
        ArrayList<Tool> tools = new ArrayList<>();
        tools.add(tool);
        tools.add(new Tool("drill"));
        tools.add(new Tool("screwdriver"));
        
        office.setTools(tools);
        assertEquals(3, office.getTools().size());
    }
}
