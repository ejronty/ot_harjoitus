
package tyovalinekirjanpito.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import tyovalinekirjanpito.domain.Tool;

/**
 * Työvälineiden tietokantatoiminnoista vastaava luokka.
 */

public class SqlToolDao extends SqlThingDao implements ToolDao {

    public SqlToolDao(Connection connection) {
        super(connection);
    }

    /**
     * Hakee tietokannasta kaikki työvälineet.
     */
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

    /**
     * Etsii tietokannasta työvälineen annetulla nimellä.
     * 
     * @param name annetun työvälineen nimi.
     */
    @Override
    public Tool findByName(String name) throws Exception {

        String sql = "SELECT * FROM tools WHERE name = ?;";
        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, name);

        ResultSet result = pstmt.executeQuery();
        return new Tool(result.getString("name"), result.getInt("id"));
    }

}
