
package tyovalinekirjanpito.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.stream.Collectors;
import tyovalinekirjanpito.domain.Thing;
import tyovalinekirjanpito.domain.Tool;


public class SqlToolDao extends SqlThingDao implements ToolDao {

    public SqlToolDao(Connection connection) {
        super(connection);
    }

    @Override
    public Collection<Tool> getAll() throws Exception {
        return super.getAll("tools")
                .stream()
                .map(t -> new Tool(t.getName(), t.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Tool findByName(String name) throws Exception {
        Thing thing = super.findByName("tools", name);
        return new Tool(thing.getName(), thing.getId());
    }

    @Override
    public void delete(int id) throws Exception {

        String sql = "DELETE FROM inventory WHERE toolId = ?;";

        this.dbConnection.setAutoCommit(false);

        super.delete("tools", id);

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setInt(1, id);

        pstmt.executeUpdate();

        this.dbConnection.commit();
        this.dbConnection.setAutoCommit(true);
    }
}
