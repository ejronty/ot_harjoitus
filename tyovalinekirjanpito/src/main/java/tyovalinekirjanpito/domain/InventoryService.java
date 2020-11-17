
package tyovalinekirjanpito.domain;

import java.util.TreeMap;

/*
  Tästä on tarkoitus muotoutua sovelluslogiikasta huolehtiva luokka.
  Toistaiseksi pitää myös kirjaa luoduista työvälineistä ja toimipisteistä
  kunnes ohjelmaan lisätään tietokanta, ja nämä toiminnallisuudet
  eriytetään.
*/
public class InventoryService {
    
    private TreeMap<String, Tool> tools;
    private TreeMap<String, Office> offices;
    

    public InventoryService() {
        this.tools = new TreeMap<>();
        this.offices = new TreeMap<>();
    }
    
    public boolean addTool(String toolName) {
        if (this.tools.containsKey(toolName)) {
            return false;
        }
        
        this.tools.put(toolName, new Tool(toolName));
        return true;
    }
    
    public boolean addOffice(String officeName) {
        if (this.offices.containsKey(officeName)) {
            return false;
        }
        
        this.offices.put(officeName, new Office(officeName));
        return true;
    }
    
    public boolean addToolToOffice(String office, String tool){
        if (!(this.offices.containsKey(office)) || !(this.tools.containsKey(tool))) {
            return false;
        }
        
        this.offices.get(office).addTool(this.tools.get(tool));
        return true;
            
    }
    
    public String displayTools() {
        String result = "";
        for (Tool tool : this.tools.values()) {
            result += tool.toString() + "\n";
        }
        return result;
    }
    
    public String displayOffices() {
        String result = "";
        for (Office office : this.offices.values()) {
            result += office.toString() + "\n";
        }
        return result;
    }
    
    
    
}
