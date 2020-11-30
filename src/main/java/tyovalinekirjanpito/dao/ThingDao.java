
package tyovalinekirjanpito.dao;

import java.util.Collection;
import tyovalinekirjanpito.domain.Thing;


public interface ThingDao {
        
    void create(String table, String name) throws Exception;
    
    Collection<Thing> getAll(String table) throws Exception;
    
    Thing findByName(String table, String name) throws Exception;
    
    boolean exists(String table, String name) throws Exception;
    
    void rename(String table, int id, String newName) throws Exception;
    
    void delete(String table, int id) throws Exception;
}
