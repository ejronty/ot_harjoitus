
package tyovalinekirjanpito.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import tyovalinekirjanpito.domain.Office;

/**
 * Toimipisteiden tietokantatoiminnoista vastaava luokka.
 */

public class SqlOfficeDao extends SqlThingDao implements OfficeDao {

    public SqlOfficeDao(Connection connection) {
        super(connection);
    }

    /**
     * Lisää tietokantaan uuden toimipisteen tiedot.
     * 
     * @param name Lisättävän kohteen nimi.
     * 
     * @throws java.lang.Exception Heittää poikkeuksen, jos jotain menee
     * vikaan.
     */
    @Override
    public void create(String name) throws Exception {

        String sql = "INSERT INTO offices(name) VALUES(?);";

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, name);

        pstmt.executeUpdate();
    }

    /**
     * Muuttaa tietokannassa olevan toimipisteen nimen.
     * 
     * @param id Muutettavan kohteen tunnus.
     * @param newName Kohteelle annettava uusi nimi.
     * 
     * @throws java.lang.Exception Heittää poikkeuksen, jos jotain menee
     * vikaan.
     */
    @Override
    public void rename(int id, String newName) throws Exception {

        String sql = "UPDATE offices SET name = ?"
                    + "WHERE id = ?;";

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, newName);
        pstmt.setInt(2, id);

        pstmt.executeUpdate();
    }

    /**
     * Hakee tietokannasta kaikki toimipisteet työvälineineen.
     * 
     * @throws java.lang.Exception Heittää poikkeuksen, jos jotain menee
     * vikaan.
     * 
     * @return Palauttaa listan kaikista tietokannasta lötyvistä
     * toimipisteistä.
     */
    @Override
    public Collection<Office> getAll() throws Exception {

        String sql = "SELECT * FROM offices;";
        Statement statement = this.dbConnection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        ArrayList<Office> list = new ArrayList<>();

        while (result.next()) {
            Office office = new Office(result.getString("name"), result.getInt("id"));
            for (String info : result.getString("tools").split(":")) {
                if (info.equals("")) {
                    continue;
                }
                String name = info.split(",")[0];
                int amount = Integer.parseInt(info.split(",")[1]);
                office.addTool(name, amount);
            }
            list.add(office);
        }
        return list;
    }

    /**
     * Etsii tietokannasta annetun nimisen toimipisteen.
     * 
     * @param name Etsittävän toimipisteen nimi.
     * 
     * @throws java.lang.Exception Heittää poikkeuksen, jos jotain menee
     * vikaan.
     * 
     * @return Palauttaa toimipiste-olion, jos tiedot löytyvät.
     */
    @Override
    public Office findByName(String name) throws Exception {

        String sql = "SELECT * FROM offices WHERE name = ?;";
        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, name);

        ResultSet result = pstmt.executeQuery();
        Office office = new Office(result.getString("name"), result.getInt("id"));
        for (String toolInfo : result.getString("tools").split(":")) {
            if (toolInfo.equals("")) {
                continue;
            }
            String toolName = toolInfo.split(",")[0];
            int amount = Integer.parseInt(toolInfo.split(",")[1]);
            office.addTool(toolName, amount);
        }
        return office;
    }    

    /**
     * Päivittää annetun toimipisteen työvälineet tietokantaan.
     * 
     * @param office Toimipiste, jonka työvälineista päivitetään.
     * 
     * @throws java.lang.Exception Heittää poikkeuksen, jos jotain menee
     * vikaan.
     */
    @Override
    public void updateToolList(Office office) throws Exception {

        String sql = "UPDATE offices SET tools = ?"
                    + "WHERE id = ?;";

        String tools = "";
        tools = office.getToolNames()
                    .stream()
                    .map(name -> name + "," + office.getAmount(name) + ":")
                    .reduce(tools, String::concat);

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, tools);
        pstmt.setInt(2, office.getId());

        pstmt.executeUpdate();
    }

}
