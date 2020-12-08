
package tyovalinekirjanpito.dao;

import java.util.Collection;
import java.util.TreeMap;
import tyovalinekirjanpito.domain.Tool;

/**
 * Muistissa toimiva dao-implementaatio testausta varten.
 */

public class TestToolDao implements ToolDao {
    
    private TreeMap<Integer, Tool> tools;
    private int counter;
    
    public TestToolDao() {
        this.tools = new TreeMap<>();
        this.counter = 1;
    }

    @Override
    public Collection<Tool> getAll() throws Exception {
        return this.tools.values();
    }

    @Override
    public Tool findByName(String name) throws Exception {
        return this.getAll()
                .stream()
                .filter(tool -> tool.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(String table, String name) throws Exception {
        if (this.findByName(name) == null) {
            Tool tool = new Tool(name, this.counter);
            this.counter++;
            this.tools.put(tool.getId(), tool);
        }
    }

    @Override
    public void rename(String table, int id, String newName) throws Exception {
        this.tools.get(id).setName(newName);
    }

    @Override
    public void delete(String table, int id) throws Exception {
        this.tools.remove(id);
    }

    
    
}
