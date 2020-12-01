
package tyovalinekirjanpito.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import tyovalinekirjanpito.domain.Office;
import tyovalinekirjanpito.domain.Tool;


public class SqlOfficeDao extends SqlThingDao implements OfficeDao {

    public SqlOfficeDao(Connection connection) {
        super(connection);
    }

    @Override
    public Collection<Office> getAll() throws Exception {

        String sql = "SELECT * FROM offices;";
        Statement statement = this.dbConnection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        ArrayList<Office> list = new ArrayList<>();

        while (result.next()) {
            Office office = new Office(result.getString("name"), result.getInt("id"));
            for (String name : result.getString("tools").split(":")) {
                office.addTool(new Tool(name));
            }
            list.add(office);
        }
        return list;
    }

    @Override
    public Office findByName(String name) throws Exception {

        String sql = "SELECT * FROM offices WHERE name = ?;";
        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, name);

        ResultSet result = pstmt.executeQuery();
        Office office = new Office(result.getString("name"), result.getInt("id"));
        for (String tool : result.getString("tools").split(":")) {
            office.addTool(new Tool(tool));
        }
        return office;
    }    

    @Override
    public void updateToolList(Office office) throws Exception {

        String sql = "UPDATE offices SET tools = ?"
                    + "WHERE id = ?;";

        String tools = "";
        tools = office.getTools()
                    .stream()
                    .map(tool -> tool.getName() + ":")
                    .reduce(tools, String::concat);

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, tools);
        pstmt.setInt(2, office.getId());

        pstmt.executeUpdate();
    }

}
