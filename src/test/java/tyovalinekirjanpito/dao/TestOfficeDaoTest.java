
package tyovalinekirjanpito.dao;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tyovalinekirjanpito.domain.Office;
import tyovalinekirjanpito.domain.Tool;


public class TestOfficeDaoTest {

    OfficeDao officeDao;
    Office office;
    Tool tool;

    @Before
    public void setUp() {
        officeDao = new TestOfficeDao();
        office = new Office("warehouse", 1);
        tool = new Tool("hammer", 1, false);

        try {
            officeDao.create("warehouse");
        } catch (Exception e) {
            // Do nothing
        }
    }

    @Test
    public void theCreatedOfficeIsRemembered() throws Exception {
        assertTrue(officeDao.getAll().contains(office));
    }

    @Test
    public void theSameOfficeCantBeAddedTwice() throws Exception {
        officeDao.create("warehouse");
        officeDao.create("warehouse");
        assertEquals(1, officeDao.getAll().size());
    }

    @Test
    public void officesCanBeAccessedByName() throws Exception{
        assertEquals(office, officeDao.findByName("warehouse"));
    }

    @Test
    public void officeCanBeRenamed() throws Exception {
        Office office = officeDao.findByName("warehouse");

        officeDao.rename(office.getId(), "main office");

        assertEquals("main office", office.getName());
    }

    @Test
    public void officeCanBeDeleted() throws Exception {
        officeDao.delete("test", 1);
        assertNull(officeDao.findByName("warehouse"));
    }
    
    
}
