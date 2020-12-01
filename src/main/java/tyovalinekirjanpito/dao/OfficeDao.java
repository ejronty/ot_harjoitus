
package tyovalinekirjanpito.dao;

import java.util.Collection;
import tyovalinekirjanpito.domain.Office;


public interface OfficeDao extends ThingDao {

    Collection<Office> getAll() throws Exception;

    Office findByName(String name) throws Exception;

    void updateToolList(Office office) throws Exception;
}
