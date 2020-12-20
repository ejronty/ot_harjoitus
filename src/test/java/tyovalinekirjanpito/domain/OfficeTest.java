
package tyovalinekirjanpito.domain;


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
    public void itIsImpossibleToAddANegativeAmountOfTool() {
        assertFalse(office.addTool(tool.getName(), -1));
    }

    @Test
    public void itIsImpossibleToAddAThousandTools() {
        assertFalse(office.addTool(tool.getName(), 1000));
    }

    @Test
    public void amountOfToolInOfficeCanBeObtained() {
        office.addTool(tool.getName(), 3);
        assertEquals(3, office.getAmount(tool.getName()));
    }

    @Test
    public void toolAvailabilityCanBeChecked() {
        office.addTool(tool.getName(), 4);
        assertTrue(office.containsTool(tool.getName()));
    }

    @Test
    public void toolAvailabilityCanBeChecked2() {
        assertFalse(office.containsTool("Drill"));
    }

    @Test
    public void itIsNotPossibleToAddTheSameToolTwice() {
        office.addTool(tool.getName(), 1);
        office.addTool(tool.getName(), 1);
        assertEquals(1, office.getToolNames().size());
    }

    @Test
    public void toolAmountCanBeUpdated() {
        office.addTool(tool.getName(), 1);
        office.updateAmount(tool.getName(), 3);

        assertEquals(3, office.getAmount(tool.getName()));
    }

    @Test
    public void toolAmountWillNotBeChangedToANegative() {
        office.addTool(tool.getName(), 1);
        assertFalse(office.updateAmount(tool.getName(), -1));
    }

    @Test
    public void toolAmountWontBeUpdatedToMoreThanThousand() {
        office.addTool(tool.getName(), 1);
        assertFalse(office.updateAmount(tool.getName(), 1000));
    }

    @Test
    public void updatingToolAmountToZeroRemovesTheTool() {
        office.addTool(tool.getName(), 1);
        office.updateAmount(tool.getName(), 0);

        assertFalse(office.containsTool(tool.getName()));
    }

    @Test
    public void updatingANonExistingToolAddsTheTool() {
        office.updateAmount("Drill", 1);
        assertTrue(office.containsTool("Drill"));
    }

    @Test
    public void aToolCanBeRemovedFromOffice() {
        office.addTool(tool.getName(), 1);
        office.removeTool(tool.getName());

        assertFalse(office.containsTool(tool.getName()));
    }

    @Test
    public void toStringReturnsTheNameOfTheOffice() {
        assertEquals("main office", office.toString());
    }
}
