
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
        office = new Office("main office", 1);
        tool = new Tool("hammer", 1, false);
    }

    @Test
    public void theNewOfficeExists() {
        assertTrue(office != null);
    }

    @Test
    public void aNewOfficeHasNoTools() {
        assertEquals(0, office.getToolNames().size());
    }

    @Test
    public void addingToolsToAnOfficeWorks1() {
        office.addTool(tool.getName(), 1);
        assertEquals(1, office.getToolNames().size());
    }

    @Test
    public void addingToolsToAnOfficeWorks2() {
        office.addTool(tool.getName(), 1);
        assertTrue(office.getToolNames().contains(tool.getName()));
    }

    @Test
    public void itIsNotPossibleToAddTheSameToolTwice() {
        office.addTool(tool.getName(), 1);
        office.addTool(tool.getName(), 1);
        assertEquals(1, office.getToolNames().size());
    }

    @Test
    public void toStringReturnsTheNameOfTheOffice() {
        assertEquals("main office", office.toString());
    }
}
