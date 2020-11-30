
package tyovalinekirjanpito.domain;

import java.util.ArrayList;
import java.util.Collection;
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

    public boolean create(String table, String itemName) {
        String name = this.processInput(itemName);

        try {
            if ("tools".equals(table)) {
                this.toolDao.create(table, name);
            } else if ("offices".equals(table))
                this.officeDao.create(table, name);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean rename(String table, String oldName, String newName) {
        String name = this.processInput(newName);

        try {
            if ("tools".equals(table)) {
                Tool tool = this.toolDao.findByName(oldName);
                this.toolDao.rename(table, tool.getId(), name);
            } else {
                Office office = this.officeDao.findByName(oldName);
                this.officeDao.rename(table, office.getId(), name);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(String table, String name) {

        try {
            if ("tools".equals(table)) {
                Tool tool = this.toolDao.findByName(name);
                this.toolDao.delete(tool.getId());
            } else {
                Office office = this.officeDao.findByName(name);
                this.officeDao.delete(office.getId());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
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

    public Collection<String> getItemList(String table) {

        Collection<Thing> result;
        try {
            if ("tools".equals(table)) {
                result = this.toolDao.getAll(table);
            } else {
                result = this.officeDao.getAll(table);
            }
        } catch (Exception e) {
            result = new ArrayList<>();
        }
        return result.stream()
                .map(t -> t.toString())
                .collect(Collectors.toList());
    }

    private String processInput(String input) {
        String result = input.strip();
        return result.substring(0, 1).toUpperCase() + 
                result.substring(1, result.length()).toLowerCase();
    }
}
