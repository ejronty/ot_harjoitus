
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
            service.createTool("Another", false);
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
    public void toolNamesCannoContainSpecialCharacters() throws Exception {
        service.createTool("/*#", false);

        assertFalse(toolDao.getAll().contains(new Tool("/*#", 1, false)));
    }

    @Test
    public void toolsCanBeEditedThroughTheService() throws Exception {
        service.editTool("Tool", "Drill", false);

        assertNotNull(toolDao.findByName("Drill"));
    }

    @Test
    public void officesCanBeRenamedThroughTheService() throws Exception {
        service.renameOffice("Office", "Warehouse");

        assertNotNull(officeDao.findByName("Warehouse"));
    }

    @Test
    public void toolsCanBeDeletedThroughTheService() throws Exception {
        service.delete("tools", "Tool");
        assertNull(toolDao.findByName("Tool"));
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
    public void aToolCanBeRemovedFromAnOfficeThroughTheService() throws Exception {
        service.addToolToOffice("Office", "Tool", "3");
        service.removeToolFromOffice("Office", "Tool");

        assertEquals(0, officeDao.findByName("Office").getToolNames().size());
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
}
