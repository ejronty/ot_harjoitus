
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
        tool = new Tool("hammer", 1, false);

        try {
            toolDao.create("hammer", false);
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
        toolDao.create("drill", false);
        toolDao.create("screwdriver", false);

        assertEquals(3, toolDao.getAll().size());
    }

    @Test
    public void theSameToolWillNotBeRememberedTwice() throws Exception {
        toolDao.create("hammer", false);

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

        toolDao.edit(tool.getId(), "drill", false);

        assertEquals("drill", tool.getName());
    }
}
