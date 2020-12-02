
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
            service.create("tools", "Tool");
            service.create("tools", "Another");
            service.create("tools", "Third");
            
            service.create("offices", "Office");
            service.create("offices", "Hq");
        } catch (Exception e) {
            // do nothing
        }
    }
    
    @Test
    public void toolsCanBeCreatedThroughTheService() throws Exception {
        service.create("tools", "Hammer");
        
        assertTrue(toolDao.getAll().contains(new Tool("hammer")));
    }
    
    @Test
    public void officesCanBeCreatedThroughTheService() throws Exception {
        service.create("offices", "Test office");
        
        assertTrue(officeDao.getAll().contains(new Office("Test office")));
    }
    
    @Test
    public void toolNamesCannoContainSpecialCharacters() throws Exception {
        service.create("tools", "/*#");
        
        assertFalse(toolDao.getAll().contains(new Tool("/*#")));
    }
    
    @Test
    public void toolsCanBeRenamedThroughTheService() throws Exception {
        service.rename("tools", "Tool", "Drill");
        
        assertNotNull(toolDao.findByName("Drill"));
    }
    
    @Test
    public void officesCanBeRenamedThroughTheService() throws Exception {
        service.rename("offices", "Office", "Warehouse");
        
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
        service.addToolToOffice("Office", "Tool");
        
        assertTrue(officeDao.findByName("Office").getTools().contains(new Tool("Tool")));
    }
    
    @Test
    public void aToolCanBeRemovedFromAnOfficeThroughTheService() throws Exception {
        service.addToolToOffice("Office", "Tool");
        service.removeToolFromOffice("Office", "Tool");
        
        assertEquals(0, officeDao.findByName("Office").getTools().size());
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
        service.addToolToOffice("Office", "Tool");
        assertTrue(service.findOfficesWithoutTool("Tool").contains("Hq"));
    }
    
    @Test
    public void ServiceReturnsOfficesNotContainingTool2() throws Exception {
        service.addToolToOffice("Office", "Tool");
        assertFalse(service.findOfficesWithoutTool("Tool").contains("Office"));
    }
    
    @Test
    public void ServiceReturnsOfficesContainingTool1() throws Exception {
        service.addToolToOffice("Office", "Tool");
        assertTrue(service.findOfficesContainingTool("Tool").contains("Office"));
    }
    
    @Test
    public void ServiceReturnsOfficesContainingTool2() throws Exception {
        service.addToolToOffice("Office", "Tool");
        assertFalse(service.findOfficesContainingTool("Tool").contains("Hq"));
    }
    
    @Test
    public void ServiceReturnsToolsMissingFromAnOffice() throws Exception {
        service.addToolToOffice("Office", "Tool");
        assertEquals(2, service.findToolsNotInOffice("Office").size());
    }
    
    @Test
    public void ServiceReturnsToolsMissingFromAnOffice2() throws Exception {
        service.addToolToOffice("Office", "Tool");
        assertFalse(service.findToolsNotInOffice("Office").contains("Tool"));
    }
    
    @Test
    public void ServiceReturnsToolsInOffice() throws Exception {
        service.addToolToOffice("Office", "Tool");
        assertTrue(service.findToolsInOffice("Office").contains("Tool"));
    }
    
    @Test
    public void ServiceReturnsToolsInOffice2() throws Exception {
        service.addToolToOffice("Office", "Tool");
        assertEquals(1, service.findToolsInOffice("Office").size());
    }
}
