
package tyovalinekirjanpito.dao;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tyovalinekirjanpito.domain.Tool;


public class TestToolDaoTest {
    
    ToolDao toolDao;
    Tool tool;
    
    @Before
    public void setUp() {
        toolDao = new TestToolDao();
        tool = new Tool("hammer");
        
        try {
            toolDao.create(tool);
        } catch (Exception e) {
            // Do nothing
        }
    }
    
    @Test
    public void creatingANewToolReturnsTheTool() throws Exception {
        Tool newTool = new Tool("drill");
        assertEquals(newTool, toolDao.create(newTool));
    }
    
    @Test
    public void daoRemembersTheCreatedTool() throws Exception{
        assertTrue(toolDao.getAll().contains(tool));
    }
    
    @Test
    public void allToolsCanBeObtainedAtOnce() throws Exception {
        toolDao.create(new Tool("drill"));
        toolDao.create(new Tool("screwdriver"));
        
        assertEquals(3, toolDao.getAll().size());
    }
    
    @Test
    public void theSameToolWillNotBeRememberedTwice() throws Exception {
        toolDao.create(tool);
        
        assertEquals(1, toolDao.getAll().size());
    }
    
    @Test
    public void daoReturnsTrueIfToolExists() throws Exception {
        assertTrue(toolDao.exists("hammer"));
    }
    
    @Test
    public void daoReturnsFalseIfToolDoesNotExist() throws Exception {
        assertFalse(toolDao.exists("drill"));
    }
    
    @Test
    public void toolsCanBeAccessedByName() throws Exception {
        assertEquals(tool, toolDao.findByName("hammer"));
    }
    
    @Test
    public void daoReturnsNullIfToolIsNotFound() throws Exception {
        assertNull(toolDao.findByName("drill"));
    }
    
    @Test
    public void toolsCanBeRenamed() throws Exception {
        toolDao.rename(tool.getName(), "drill");
        
        assertEquals(tool, toolDao.findByName("drill"));
    }
}
