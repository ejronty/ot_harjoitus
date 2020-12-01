
package tyovalinekirjanpito.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

/*
  Tämän luokan tarkoitus on hoitaa ne tietokantatoiminnot, jotka olisivat
  muuten hyvin toisteisia toimipisteiden ja työvälineiden välillä.
*/

public class SqlThingDao implements ThingDao {

    protected Connection dbConnection;

    public SqlThingDao(Connection connection) {
        this.dbConnection = connection;
    }

    @Override
    public void create(String table, String name) throws Exception {

        String sql = "INSERT INTO " + table + "(name) VALUES(?);";

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, name);

        pstmt.executeUpdate();
    }

    @Override
    public void rename(String table, int id, String newName) throws Exception {

        String sql = "UPDATE " + table + " SET name = ?"
                    + "WHERE id = ?;";

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, newName);
        pstmt.setInt(2, id);

        pstmt.executeUpdate();
    }

    @Override
    public void delete(String table, int id) throws Exception {

        String sql = "DELETE FROM " + table + " WHERE id = ?;";

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setInt(1, id);

        pstmt.executeUpdate();
    }

}
