
package tyovalinekirjanpito.dao;

import java.util.Collection;
import java.util.TreeMap;
import tyovalinekirjanpito.domain.Tool;


public class TestToolDao implements ToolDao {
    
    private TreeMap<String, Tool> tools;
    
    public TestToolDao() {
        this.tools = new TreeMap<>();
    }

    @Override
    public Tool create(Tool tool) throws Exception {
        this.tools.put(tool.getName(), tool);
        return tool;
    }

    @Override
    public Collection<Tool> getAll() {
        return this.tools.values();
    }

    @Override
    public Tool findByName(String name) throws Exception {
        return this.tools.get(name);
    }
    
    @Override
    public boolean exists(String name) {
        return this.tools.containsKey(name);
    }

    @Override
    public void rename(String oldName, String newName) throws Exception {
        Tool tool = this.tools.get(oldName);
        tool.setName(newName);
        
        this.tools.remove(oldName);
        this.tools.put(newName, tool);
    }
    
}
