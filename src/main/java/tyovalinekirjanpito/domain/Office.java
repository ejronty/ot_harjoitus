
package tyovalinekirjanpito.domain;

import java.util.Set;
import java.util.TreeMap;

/**
 * Toimipistettä kuvaava luokka.
 */

public class Office extends Thing {

    private TreeMap<String, Integer> tools;

    /**
     * Luo uuden toimipisteen.
     * 
     * @param name Luotavan toimipisteen nimi.
     * @param id Toimipisteen id-tunnus.
     */
    public Office(String name, int id) {
        super(name, id);
        this.tools = new TreeMap<>();
    }

    /**
     * Metodi toimipisteen työvälineiden listaamiseen välittämättä määristä.
     * 
     * @return Toimipisteen työvälineiden nimet.
     */
    public Set<String> getToolNames() {
        return this.tools.keySet();
    }
    
    public TreeMap<String, Integer> getToolsWithAmounts() {
        return this.tools;
    }
    
    public int getAmount(String toolName) {
        return this.tools.get(toolName);
    }
    
    public boolean containsTool(String toolName) {
        return this.tools.containsKey(toolName);
    }

    /**
     * Metodi työvälineen lisäämiseksi toimipisteelle. Tarkistaa, ettei
     * työväline ole jo ennestään listalla.
     * 
     * @param toolName Lisättävän työvälineen nimi.
     * @param amount Montako kappaletta välinettä lisätään.
     */
    public boolean addTool(String toolName, int amount) {
        if (amount <= 0) {
            return false;
        }
        if (!(this.tools.containsKey(toolName))) {
            this.tools.put(toolName, amount);
            return true;
        }
        return false;
    }
    
    /**
     * Päivittää työvälineen määrän tällä toimipisteellä.
     * 
     * @param toolName Päivitettävän työvälineen nimi.
     * @param newAmount Työvälineen uusi määrä.
     */
    public boolean updateAmount(String toolName, int newAmount) {
        if (newAmount < 0) {
            return false;
        } else if (newAmount == 0) {
            return this.removeTool(toolName);
        }
        if (this.tools.replace(toolName, newAmount) == null) {
            return false;
        }
        return true;
    }
    
    /**
     * Metodi työvälineen poistamiseksi toimipisteestä.
     * 
     * @param toolName Poistettavan työvälineen nimi.
     */
    public boolean removeTool(String toolName) {
        if (this.tools.containsKey(toolName)) {
            this.tools.remove(toolName);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Office)) {
            return false;
        }
        Office other = (Office) obj;
        return this.name.equalsIgnoreCase(other.getName());
    }
}
