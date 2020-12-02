
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
        tool = new Tool("hammer", 1);

        try {
            toolDao.create("test", "hammer");
        } catch (Exception e) {
            // Do nothing
        }
    }

    @Test
    public void daoRemembersTheCreatedTool() throws Exception{
        assertTrue(toolDao.getAll().contains(tool));
    }
    
    @Test
    public void allToolsCanBeObtainedAtOnce() throws Exception {
        toolDao.create("test", "drill");
        toolDao.create("test", "screwdriver");
        
        assertEquals(3, toolDao.getAll().size());
    }
    
    @Test
    public void theSameToolWillNotBeRememberedTwice() throws Exception {
        toolDao.create("test", "hammer");
        
        assertEquals(1, toolDao.getAll().size());
    }
    
    @Test
    public void toolsCanBeAccessedByName() throws Exception {
        assertEquals(tool, toolDao.findByName("hammer"));
    }
    
    @Test
    public void daoReturnsNullIfToolIsNotFound() throws Exception {
        assertNull(toolDao.findByName("drill"));
    }
    
    @Test public void toolsCanBeRenamed() throws Exception {
        Tool tool = toolDao.findByName("hammer");
        
        toolDao.rename("test", tool.getId(), "drill");
        
        assertEquals("drill", tool.getName());
    }
}
