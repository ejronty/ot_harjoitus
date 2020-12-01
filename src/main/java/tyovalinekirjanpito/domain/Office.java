
package tyovalinekirjanpito.domain;

import java.util.ArrayList;

/*
  Toimipistettä kuvaava luokka.
*/

public class Office extends Thing {

    private ArrayList<Tool> tools;

    public Office(String name, int id) {
        super(name, id);
        this.tools = new ArrayList<>();
    }

    public Office(String name) {
        super(name);
        this.tools = new ArrayList<>();
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
    
    public boolean removeTool(Tool tool) {
        if (this.tools.contains(tool)) {
            this.tools.remove(tool);
            return true;
        }
        return false;
    }
}
