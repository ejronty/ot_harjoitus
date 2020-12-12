
package tyovalinekirjanpito.dao;

import java.util.Collection;
import tyovalinekirjanpito.domain.Office;


public interface OfficeDao extends ThingDao {

    void create(String name) throws Exception;

    void rename(int id, String newName) throws Exception;

    Collection<Office> getAll() throws Exception;

    Office findByName(String name) throws Exception;

    void updateToolList(Office office) throws Exception;
}
