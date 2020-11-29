
package tyovalinekirjanpito.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.TreeMap;
import tyovalinekirjanpito.domain.Tool;


public class SqlToolDao implements ToolDao {
    
    private Connection dbConnection;
    private TreeMap<String, Tool> tools;
    
    public SqlToolDao(Connection connection) throws Exception {
        this.dbConnection = connection;
        this.tools = new TreeMap<>();
        
        String getTools = "SELECT name FROM tools;";
        
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet results = statement.executeQuery(getTools);
            
            while (results.next()) {
                String name = results.getString("name");
                this.tools.put(name, new Tool(name));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Tool create(Tool tool) throws Exception {
        String createTool = "INSERT INTO tools(name) "
                            + "VALUES (\"" + tool.getName() + "\");";

        Statement statement = dbConnection.createStatement();
        statement.execute(createTool);

        this.tools.put(tool.getName(), tool);
        
        return tool;
    }

    @Override
    public Collection<Tool> getAll() {
        return this.tools.values();
    }

    @Override
    public Tool findByName(String name) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean exists(String name) {
        return this.tools.containsKey(name);
    }

    @Override
    public void rename(String oldName, String newName) throws Exception {
        
        String sql = "UPDATE tools SET name = ? "
                    + "WHERE name = ?;";
        
        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, newName);
        pstmt.setString(2, oldName);
        
        pstmt.executeUpdate();
    }
    
    public void delete(Tool tool) throws Exception {

        String sql = "DELETE FROM tools WHERE name = ?";

        PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
        pstmt.setString(1, tool.getName());

        pstmt.executeUpdate();
        
    }
    
}
