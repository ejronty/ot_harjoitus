
package tyovalinekirjanpito.dao;


import java.util.Collection;
import tyovalinekirjanpito.domain.Tool;


public interface ToolDao {
    
    Tool create(Tool tool) throws Exception;
    
    Collection<Tool> getAll();
    
    Tool findByName(String name) throws Exception;
    
    boolean exists(String name);
    
    void rename(String oldName, String newName) throws Exception;
    
    void delete(Tool tool) throws Exception;
}
