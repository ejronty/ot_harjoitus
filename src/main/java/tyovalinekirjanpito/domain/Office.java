
package tyovalinekirjanpito.domain;

import java.util.ArrayList;

/**
 * Toimipistettä kuvaava luokka.
 */

public class Office extends Thing {

    private ArrayList<Tool> tools;

    /**
     * Luo uuden toimipisteen. Metodia on tarkoitus käyttää, kun toimipiste
     * luodaan lukemalla tiedot tietokannasta, jolloin mukaan saadaan
     * myös toimipisteen id-tunnus.
     */
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

    /**
     * Metodi työvälineen lisäämiseksi toimipisteelle. Tarkistaa, ettei
     * työväline ole jo ennestään listalla.
     * 
     * @param tool Työväline, joka halutaan lisätä.
     */
    public boolean addTool(Tool tool) {
        if (!(this.tools.contains(tool))) {
            this.tools.add(tool);
            return true;
        }

        return false;
    }
    
    /**
     * Metodi työvälineen poistamiseksi toimipisteestä.
     * 
     * @param tool Työväline, joka halutaan poistaa.
     */
    public boolean removeTool(Tool tool) {
        if (this.tools.contains(tool)) {
            this.tools.remove(tool);
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
