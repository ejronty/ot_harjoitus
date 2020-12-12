
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
