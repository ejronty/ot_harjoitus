
package tyovalinekirjanpito.dao;


import java.util.Collection;
import tyovalinekirjanpito.domain.Tool;


public interface ToolDao extends ThingDao {

    Collection<Tool> getAll() throws Exception;

    Tool findByName(String name) throws Exception;
}
