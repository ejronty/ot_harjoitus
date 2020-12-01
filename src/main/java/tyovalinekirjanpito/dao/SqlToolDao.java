
package tyovalinekirjanpito.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import tyovalinekirjanpito.domain.Tool;


public class SqlToolDao extends SqlThingDao implements ToolDao {

    public SqlToolDao(Connection connection) {
        super(connection);
    }

    @Override
    public Collection<Tool> getAll() throws Exception {

        String sql = "SELECT * FROM tools;";
        Statement statement = this.dbConnection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        ArrayList<Tool> list = new ArrayList<>();

        while (result.next()) {
            list.add(new Tool(result.getString("name"), result.getInt("id")));
        }
        return list;
    }

    @Override
    public Tool findByName(String name) throws Exception {

        String sql = "SELECT * FROM tools WHERE name = ?;";
        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, name);

        ResultSet result = pstmt.executeQuery();
        return new Tool(result.getString("name"), result.getInt("id"));
    }

}
