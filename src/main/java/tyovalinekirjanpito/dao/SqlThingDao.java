
package tyovalinekirjanpito.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Työvälineiden ja toimipisteiden yhteisistä tietokantatoiminnoista
 * vastaava luokka. Tarkoitettu täsmällisempien luokkien perittäväksi.
 */

public class SqlThingDao implements ThingDao {

    protected Connection dbConnection;

    public SqlThingDao(Connection connection) {
        this.dbConnection = connection;
    }

    /**
     * Lisää tietokantaan uuden kohteen tiedot.
     * 
     * @param table Käsiteltävä tietokantataulu.
     * @param name Lisättävän kohteen nimi.
     */
    @Override
    public void create(String table, String name) throws Exception {

        String sql = "INSERT INTO " + table + "(name) VALUES(?);";

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, name);

        pstmt.executeUpdate();
    }

    /**
     * Muuttaa tietokannassa olevan kohteen nimen.
     * 
     * @param table Käsiteltävä tietokantataulu.
     * @param id Muutettavan kohteen tunnus.
     * @param newName Kohteelle annettava uusi nimi.
     */
    @Override
    public void rename(String table, int id, String newName) throws Exception {

        String sql = "UPDATE " + table + " SET name = ?"
                    + "WHERE id = ?;";

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, newName);
        pstmt.setInt(2, id);

        pstmt.executeUpdate();
    }

    /**
     * Poistaa kohteen tietokannasta.
     * 
     * @param table Käsiteltävä tietokantataulu.
     * @param id Poistettavan kohteen tunnus.
     */
    @Override
    public void delete(String table, int id) throws Exception {

        String sql = "DELETE FROM " + table + " WHERE id = ?;";

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setInt(1, id);

        pstmt.executeUpdate();
    }

}
