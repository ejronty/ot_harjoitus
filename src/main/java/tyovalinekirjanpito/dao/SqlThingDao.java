
package tyovalinekirjanpito.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import tyovalinekirjanpito.domain.Thing;

/*
  Tämän luokan tarkoitus on hoitaa ne tietokantatoiminnot, jotka olisivat
  muuten hyvin toisteisia toimipisteiden ja työvälineiden välillä.
*/

public class SqlThingDao {
    
    protected Connection dbConnection;
    
    public SqlThingDao(Connection connection) {
        this.dbConnection = connection;
    }

    public void create(String table, String name) throws Exception {

        String sql = "INSERT INTO " + table + "(name) VALUES(?);";

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, name);

        pstmt.executeUpdate();
    }

    public Collection<Thing> getAll(String table) throws Exception {

        String sql = "SELECT * FROM " + table + ";";

        Statement statement = this.dbConnection.createStatement();

        ResultSet result = statement.executeQuery(sql);
        ArrayList<Thing> list = new ArrayList<>();

        while(result.next()) {
            list.add(new Thing(result.getString("name"), result.getInt("id")));
        }
        return list;
    }

    public Thing findByName(String table, String name) throws Exception {

        String sql = "SELECT * FROM " + table 
                    + " WHERE name = ?;";

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, name);

        ResultSet result = pstmt.executeQuery();

        return new Thing(result.getString("name"), result.getInt("id"));
    }

    public boolean exists(String table, String name) throws Exception {
        return (this.findByName(table, name) != null);
    }

    public void rename(String table, int id, String newName) throws Exception {

        String sql = "UPDATE " + table + " SET name = ?"
                    + "WHERE id = ?;";

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, newName);
        pstmt.setInt(2, id);

        pstmt.executeUpdate();
    }

    public void delete(String table, int id) throws Exception {

        String sql = "DELETE FROM " + table + " WHERE id = ?;";

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setInt(1, id);

        pstmt.executeUpdate();
    }

}
