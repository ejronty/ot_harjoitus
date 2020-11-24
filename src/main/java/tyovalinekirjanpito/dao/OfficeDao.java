
package tyovalinekirjanpito.dao;

import java.util.Collection;
import tyovalinekirjanpito.domain.Office;
import tyovalinekirjanpito.domain.Tool;


public interface OfficeDao {
    
    Office create(Office office) throws Exception;
    
    Collection<Office> getAll();
    
    Office findByName(String name) throws Exception;
    
    boolean exists(String name);
    
    void rename(String oldName, String newName) throws Exception;
    
    void linkTogether(Office office, Tool tool) throws Exception;
    
}
