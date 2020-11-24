
package tyovalinekirjanpito.dao;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tyovalinekirjanpito.domain.Office;
import tyovalinekirjanpito.domain.Tool;

/**
 *
 * @author ejronty
 */
public class TestOfficeDaoTest {
    
    OfficeDao officeDao;
    Office office;
    Tool tool;
    
    @Before
    public void setUp() {
        officeDao = new TestOfficeDao();
        office = new Office("warehouse");
        tool = new Tool("hammer");

        try {
            officeDao.create(office);
        } catch (Exception e) {
            // Do nothing
        }

    }
    
    @Test
    public void theCreatedOfficeIsReturned() throws Exception {
        Office newOffice = new Office("main office");
        assertEquals(newOffice, officeDao.create(newOffice));
    }
    
    @Test
    public void theCreatedOfficeIsRemembered() {
        assertTrue(officeDao.getAll().contains(office));
    }
    
    @Test
    public void theSameOfficeCantBeAddedTwice() throws Exception {
        officeDao.create(office);
        assertEquals(1, officeDao.getAll().size());
    }
    
    @Test
    public void daoReturnsTrueIfOfficeExists() {
        assertTrue(officeDao.exists("warehouse"));
    }
    
    @Test
    public void daoReturnsFalseIfOfficeDoesNotExist() {
        assertFalse(officeDao.exists("main office"));
    }
    
    @Test
    public void officesCanBeAccessedByName() throws Exception{
        assertEquals(office, officeDao.findByName("warehouse"));
    }
    
    
}
