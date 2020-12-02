
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
        tool = new Tool("hammer", 1);

        try {
            officeDao.create("test", "warehouse");
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
        officeDao.create("test", "warehouse");
        officeDao.create("test", "warehouse");
        assertEquals(1, officeDao.getAll().size());
    }
    
    @Test
    public void officesCanBeAccessedByName() throws Exception{
        assertEquals(office, officeDao.findByName("warehouse"));
    }
    
    
}
