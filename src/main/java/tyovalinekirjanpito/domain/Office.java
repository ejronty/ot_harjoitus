
package tyovalinekirjanpito.domain;

import java.util.ArrayList;

/*
  Toimipistettä kuvaava luokka.
*/

public class Office {
    
    private String name;
    private ArrayList<Tool> tools;

    public Office(String name) {
        this.name = name;
        this.tools = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Tool> getTools() {
        return this.tools;
    }

    public void setTools(ArrayList<Tool> tools) {
        this.tools = tools;
    }
    
    public boolean addTool(Tool tool) {
        if (!(this.tools.contains(tool))) {
            this.tools.add(tool);
            return true;
        }
        
        return false;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
}
