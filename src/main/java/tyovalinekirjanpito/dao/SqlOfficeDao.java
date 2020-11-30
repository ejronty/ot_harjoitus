
package tyovalinekirjanpito.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.stream.Collectors;
import tyovalinekirjanpito.domain.Office;
import tyovalinekirjanpito.domain.Thing;


public class SqlOfficeDao extends SqlThingDao implements OfficeDao {

    public SqlOfficeDao(Connection connection) {
        super(connection);
    }

    @Override
    public Collection<Office> getAll() throws Exception {
        return super.getAll("offices")
                .stream()
                .map(t -> new Office(t.getName(), t.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Office findByName(String name) throws Exception {
        Thing thing = super.findByName("offices", name);
        return new Office(thing.getName(), thing.getId());
    }    

    @Override
    public void delete(int id) throws Exception {

        String sql = "DELETE FROM inventory WHERE officeId = ?;";

        this.dbConnection.setAutoCommit(false);

        super.delete("offices", id);

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setInt(1, id);

        pstmt.executeUpdate();

        this.dbConnection.commit();
        this.dbConnection.setAutoCommit(true);
    }
}
