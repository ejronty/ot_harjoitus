
package tyovalinekirjanpito.dao;


import java.util.Collection;
import tyovalinekirjanpito.domain.Tool;


public interface ToolDao extends ThingDao {

    void create(String name, boolean consumable) throws Exception;

    void edit(int id, String newName, boolean consumable) throws Exception;

    Collection<Tool> getAll() throws Exception;

    Tool findByName(String name) throws Exception;
}
