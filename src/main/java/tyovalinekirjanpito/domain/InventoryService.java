
package tyovalinekirjanpito.domain;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import tyovalinekirjanpito.dao.OfficeDao;
import tyovalinekirjanpito.dao.ToolDao;

/*
  Tästä on tarkoitus muotoutua sovelluslogiikasta huolehtiva luokka.
*/
public class InventoryService {
    
    private ToolDao toolDao;
    private OfficeDao officeDao;

    public InventoryService(ToolDao toolDao, OfficeDao officeDao) {
        this.toolDao = toolDao;
        this.officeDao = officeDao;
    }
    
    public boolean createTool(String toolName) {
        String name = this.processInput(toolName);
        if (this.toolDao.exists(name)) {
            return false;
        }
        
        Tool tool = new Tool(name);
        try {
            this.toolDao.create(tool);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean renameTool(String oldName, String newName) {
        String faultyName = this.processInput(oldName);
        String correctName = this.processInput(newName);
        
        if (!this.toolDao.exists(faultyName)) {
            return false;
        }
        
        try {
            this.toolDao.rename(faultyName, correctName);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean deleteTool(String name) {
    // Tähän pitää tehdä muutoksia, kunhan findByName on implementoitu.
        try {
            if (! this.toolDao.exists(name)) {
                return false;
            }
            this.toolDao.delete(new Tool(name));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean createOffice(String officeName) {
        String name = this.processInput(officeName);
        if (this.officeDao.exists(name)) {
            return false;
        }
        
        Office office = new Office(name);
        try {
            this.officeDao.create(office);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean renameOffice(String oldName, String newName) {
        String wrongName = this.processInput(oldName);
        String correctName = this.processInput(newName);
        if (!this.officeDao.exists(wrongName)) {
            return false;
        }
        
        try {
            this.officeDao.rename(wrongName, correctName);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean addToolToOffice(String officeName, String toolName) {
        
        Office office;
        Tool tool;
        
        try {
            office = this.officeDao.findByName(this.processInput(officeName));
            tool = this.toolDao.findByName(this.processInput(toolName));   
        } catch (Exception e) {
            return false;
        }
        
        if ((office == null) || (tool == null)) {
            return false;
        }
        
        office.addTool(tool);
        return true;
            
    }
    
    public String displayTools() {
        String result = "";
        for (Tool tool : this.toolDao.getAll()) {
            result += tool.toString() + "\n";
        }
        return result;
    }
    
    // Tilapäinen testimetodi
    public Collection<String> getToolList() {
        return this.toolDao.getAll()
                .stream()
                .map(t -> t.toString())
                .collect(Collectors.toList());
    }
    
    public String displayOffices() {
        String result = "";
        for (Office office : this.officeDao.getAll()) {
            result += office.toString() + "\n";
        }
        return result;
    }
    
    public String displayOfficesWithTools() {
        String result = "";
        for (Office office : this.officeDao.getAll()) {
            result += office.toString() + ":\n";
            if (office.getTools().isEmpty()) {
                result += "\n";
            } else {
                for (Tool tool : office.getTools()) {
                    result += "    " + tool.toString() + "\n";
                }
            }
        }
        return result;
    }
    
    private String processInput(String input) {
        String result = input.strip();
        return result.substring(0, 1).toUpperCase() + 
                result.substring(1, result.length()).toLowerCase();
    }
}
