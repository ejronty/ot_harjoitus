
package tyovalinekirjanpito.dao;

import java.sql.Connection;
import java.util.Collection;
import tyovalinekirjanpito.domain.Office;


public class SqlOfficeDao implements OfficeDao {
    
    private Connection dbConnection;
    
    public SqlOfficeDao(Connection connection) {
        this.dbConnection = connection;
    }

    @Override
    public Office create(Office office) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Office> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Office findByName(String name) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean exists(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rename(String oldName, String newName) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
