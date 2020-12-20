
package tyovalinekirjanpito.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tyovalinekirjanpito.dao.OfficeDao;
import tyovalinekirjanpito.dao.TestOfficeDao;
import tyovalinekirjanpito.dao.TestToolDao;
import tyovalinekirjanpito.dao.ToolDao;


public class InventoryServiceTest {

    InventoryService service;
    ToolDao toolDao;
    OfficeDao officeDao;


    @Before
    public void setUp() {
        toolDao = new TestToolDao();
        officeDao = new TestOfficeDao();
        service = new InventoryService(toolDao, officeDao);

        try {
            service.createTool("Tool", false);
            service.createTool("Another", true);
            service.createTool("Third", false);

            service.createOffice("Office");
            service.createOffice("Hq");
        } catch (Exception e) {
            // do nothing
        }
    }

    @Test
    public void toolsCanBeCreatedThroughTheService() throws Exception {
        service.createTool("Hammer", false);

        assertTrue(toolDao.getAll().contains(new Tool("hammer", 1, false)));
    }

    @Test
    public void officesCanBeCreatedThroughTheService() throws Exception {
        service.createOffice("Test office");

        assertTrue(officeDao.getAll().contains(new Office("Test office", 1)));
    }

    @Test
    public void toolNamesCannotContainSpecialCharacters() throws Exception {
        service.createTool("/*#", false);

        assertFalse(toolDao.getAll().contains(new Tool("/*#", 1, false)));
    }

    @Test
    public void officeNamesCannotContainSpecialCharacters() throws Exception {
        service.createOffice("/*#");
        assertFalse(officeDao.getAll().contains(new Office("/*#", 1)));
    }

    @Test
    public void toolsCanBeEditedThroughTheService() throws Exception {
        service.editTool("Tool", "Drill", false);

        assertNotNull(toolDao.findByName("Drill"));
    }

    @Test
    public void ifToolIsRenamedItIsUpdatedInOffices() throws Exception {
        service.addToolToOffice("Office", "Tool", "3");
        service.editTool("Tool", "Drill", false);

        assertTrue(officeDao.findByName("Office").containsTool("Drill"));
    }

    @Test
    public void officesCanBeRenamedThroughTheService() throws Exception {
        service.renameOffice("Office", "Warehouse");

        assertNotNull(officeDao.findByName("Warehouse"));
    }

    @Test
    public void officeNameCannotBeChangredToEmpty() throws Exception {
        assertFalse(service.renameOffice("Office", ""));
    }

    @Test
    public void toolsCanBeDeletedThroughTheService() throws Exception {
        service.delete("tools", "Tool");
        assertNull(toolDao.findByName("Tool"));
    }

    @Test
    public void aDeletedToolIsAlsoRemovedFromOffices() throws Exception {
        service.addToolToOffice("Office", "Tool", "2");
        service.delete("tools", "Tool");
        assertFalse(officeDao.findByName("Office").containsTool("Tool"));
    }

    @Test
    public void officesCanBeDeletedThroughTheService() throws Exception {
        service.delete("offices", "Office");
        assertNull(officeDao.findByName("Office"));
    }

    @Test
    public void aToolCanBeAddedToAnOfficeThroughTheService() throws Exception {
        service.addToolToOffice("Office", "Tool", "3");

        assertTrue(officeDao.findByName("Office").getToolNames().contains("Tool"));
    }

    @Test
    public void aNegativeAmountOfToolCannotBeAdded() {
        assertFalse(service.addToolToOffice("Office", "Tool", "-1"));
    }

    @Test
    public void aToolCannotBeAddedToOfficeTwiceThroughTheService() {
        service.addToolToOffice("Office", "Tool", "3");
        assertFalse(service.addToolToOffice("Office", "Tool", "4"));
    }

    @Test
    public void aToolCanBeRemovedFromAnOfficeThroughTheService() throws Exception {
        service.addToolToOffice("Office", "Tool", "3");
        service.removeToolFromOffice("Office", "Tool");

        assertEquals(0, officeDao.findByName("Office").getToolNames().size());
    }

    @Test
    public void amountOfToolCanBeUpdatedThroughTheService() throws Exception {
        service.addToolToOffice("Office", "Tool", "1");
        service.updateToolAmountInOffice("Office", "Tool", "3", "modify");
        assertEquals(3, officeDao.findByName("Office").getAmount("Tool"));
    }

    @Test
    public void amountOfToolCanBeIncreasedThroughTheService() throws Exception {
        service.addToolToOffice("Office", "Tool", "1");
        service.updateToolAmountInOffice("Office", "Tool", "3", "add");
        assertEquals(4, officeDao.findByName("Office").getAmount("Tool"));
    }

    @Test
    public void toolsCanBeUsedThroughTheService() throws Exception {
        service.addToolToOffice("Office", "Tool", "4");
        service.updateToolAmountInOffice("Office", "Tool", "3", "use");
        assertEquals(1, officeDao.findByName("Office").getAmount("Tool"));
    }

    @Test
    public void amountOfToolsUsedCannotExeedAmountInOffice() throws Exception {
        service.addToolToOffice("Office", "Tool", "3");
        assertFalse(service.updateToolAmountInOffice("Office", "Tool", "5", "use"));
    }

    @Test
    public void toolsCanBeTransferredBetweenOffices() throws Exception {
        service.addToolToOffice("Office", "Tool", "3");
        service.transferToolsBetweenOffices("Office", "Hq", "Tool", "2");
        assertTrue(officeDao.findByName("Hq").containsTool("Tool"));
    }

    @Test
    public void cannotTransferMoreThanOfficeContains() {
        service.addToolToOffice("Hq", "Tool", "4");
        assertFalse(service.transferToolsBetweenOffices("Hq", "Office", "Tool", "7"));
    }

    @Test
    public void amountInReceivingOfficeCannotExeedLimitViaTransfer() {
        service.addToolToOffice("Office", "Tool", "4");
        service.addToolToOffice("Hq", "Tool", "998");
        assertFalse(service.transferToolsBetweenOffices("Office", "Hq", "Tool", "3"));
    }

    @Test
    public void allToolsCanBeRetrievedThroughTheService() throws Exception {
        assertEquals(3, service.getItemList("tools").size());
    }

    @Test
    public void allOfficesCanBeRetrievedThroughTheService() throws Exception {
        assertEquals(2, service.getItemList("offices").size());
    }

    @Test
    public void ServiceReturnsOfficesNotContainingTool1() throws Exception {
        service.addToolToOffice("Office", "Tool", "3");
        assertTrue(service.findOfficesWithoutTool("Tool").contains("Hq"));
    }

    @Test
    public void ServiceReturnsOfficesNotContainingTool2() throws Exception {
        service.addToolToOffice("Office", "Tool", "3");
        assertFalse(service.findOfficesWithoutTool("Tool").contains("Office"));
    }

    @Test
    public void ServiceReturnsOfficesContainingTool1() throws Exception {
        service.addToolToOffice("Office", "Tool", "3");
        assertTrue(service.findOfficesContainingTool("Tool").containsKey("Office"));
    }

    @Test
    public void ServiceReturnsOfficesContainingTool2() throws Exception {
        service.addToolToOffice("Office", "Tool", "3");
        assertFalse(service.findOfficesContainingTool("Tool").containsKey("Hq"));
    }

    @Test
    public void ServiceReturnsToolsMissingFromAnOffice() throws Exception {
        service.addToolToOffice("Office", "Tool", "3");
        assertEquals(2, service.findToolsNotInOffice("Office").size());
    }

    @Test
    public void ServiceReturnsToolsMissingFromAnOffice2() throws Exception {
        service.addToolToOffice("Office", "Tool", "3");
        assertFalse(service.findToolsNotInOffice("Office").contains("Tool"));
    }

    @Test
    public void ServiceReturnsToolsInOffice() throws Exception {
        service.addToolToOffice("Office", "Tool", "3");
        assertTrue(service.findToolsInOffice("Office").containsKey("Tool"));
    }

    @Test
    public void ServiceReturnsToolsInOffice2() throws Exception {
        service.addToolToOffice("Office", "Tool", "3");
        assertEquals(1, service.findToolsInOffice("Office").size());
    }

    @Test
    public void toolConsumabilityCanBeCheckedThroughTheService() {
        assertFalse(service.getToolConsumability("Tool"));
    }

    @Test
    public void toolConsumabilityCanBeCheckedThroughTheService2() {
        assertTrue(service.getToolConsumability("Another"));
    }

    @Test
    public void amountOfToolInOfficeCanBeCheckedThroughTheService() {
        service.addToolToOffice("Hq", "Tool", "3");
        assertTrue(service.getAmountOfToolInOffice("Hq", "Tool").equals(3));
    }

    @Test
    public void ServiceReturnsToolInfoFromOtherOffices() {
        service.addToolToOffice("Hq", "Tool", "3");
        assertTrue(service.getToolDataFromOtherOffices("Hq", "Tool").containsKey("Office"));
    }

    @Test
    public void ServiceReturnsToolInfoFromOtherOffices2() {
        service.addToolToOffice("Hq", "Tool", "3");
        assertFalse(service.getToolDataFromOtherOffices("Hq", "Tool").containsKey("Hq"));
    }
}
