
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
     * Lisää tietokantaan uuden työvälineen tiedot.
     * 
     * @param name Lisättävän kohteen nimi.
     * @param consumable Onko kyseessä kuluva työväline?
     */
    @Override
    public void create(String name, boolean consumable) throws Exception {

        String sql = "INSERT INTO tools(name, consumable) VALUES(?,?);";

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, String.valueOf(consumable));

        pstmt.executeUpdate();
    }

    /**
     * Muuttaa tietokannassa olevan työvälineen tietoja.
     * 
     * @param id Muutettavan kohteen tunnus.
     * @param newName Kohteelle annettava uusi nimi.
     * @param consumable Onko kyseessä kuluva työväline?
     */
    @Override
    public void edit(int id, String newName, boolean consumable) throws Exception {

        String sql = "UPDATE tools SET name = ?,"
                    + "consumable = ?"
                    + "WHERE id = ?;";

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, newName);
        pstmt.setString(2, String.valueOf(consumable));
        pstmt.setInt(3, id);

        pstmt.executeUpdate();
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
            list.add(new Tool(result.getString("name"), result.getInt("id"), Boolean.valueOf(result.getString("consumable"))));
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
        return new Tool(result.getString("name"), result.getInt("id"), Boolean.valueOf(result.getString("consumable")));
    }

}
