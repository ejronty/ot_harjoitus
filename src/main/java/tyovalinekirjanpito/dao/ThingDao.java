
package tyovalinekirjanpito.dao;


public interface ThingDao {

    void create(String table, String name) throws Exception;

    void rename(String table, int id, String newName) throws Exception;

    void delete(String table, int id) throws Exception;
}
