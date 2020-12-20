
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

    /**
     * Toimipisteen työvälineet määrineen.
     * 
     * @return Palauttaa luettelon toimipisteen työvälinistä ja niiden
     * määristä.
     */
    public TreeMap<String, Integer> getToolsWithAmounts() {
        return this.tools;
    }

    /**
     * Työvälineen määrä tässä toimipisteessä.
     * 
     * @param toolName Sen työvälineen nimi, jonka määrä halutaan tietää.
     * 
     * @return Palauttaa työvälineen määrän. Palauttaa 0, jos työvälinettä
     * ei löydy.
     */
    public int getAmount(String toolName) {
        return this.tools.getOrDefault(toolName, 0);
    }

    /**
     * Sisältääkö toimipiste tietyn työvälineen.
     * 
     * @param toolName Etsittävän työvälineen nimi.
     * 
     * @return Palauttaa true, jos työväline löytyy.
     */
    public boolean containsTool(String toolName) {
        return this.tools.containsKey(toolName);
    }

    /**
     * Metodi työvälineen lisäämiseksi toimipisteelle. Tarkistaa, ettei
     * työväline ole jo ennestään listalla.
     * 
     * @param toolName Lisättävän työvälineen nimi.
     * @param amount Montako kappaletta välinettä lisätään.
     * 
     * @return Palauttaa true, jos työvälineen lisääminen onnistui.
     */
    public boolean addTool(String toolName, int amount) {
        if (amount <= 0 || amount > 999) {
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
     * 
     * @return Palauttaa true, jos määrän muutos onnistui.
     */
    public boolean updateAmount(String toolName, int newAmount) {
        if (newAmount < 0 || newAmount > 999) {
            return false;
        } else if (newAmount == 0) {
            return this.removeTool(toolName);
        }
        if (!this.containsTool(toolName)) {
            return this.addTool(toolName, newAmount);
        } else if (this.tools.replace(toolName, newAmount) == null) {
            return false;
        }
        return true;
    }

    /**
     * Metodi työvälineen poistamiseksi toimipisteestä.
     * 
     * @param toolName Poistettavan työvälineen nimi.
     * 
     * @return Palauttaa true, jos poisto onnistui.
     */
    public boolean removeTool(String toolName) {
        if (this.tools.containsKey(toolName)) {
            this.tools.remove(toolName);
            return true;
        }
        return false;
    }

    /**
     * Metodi toimipisteiden vertaamiseen muihin olioihin.
     * Kaksi toimipistettä katsotaan samaksi, jos niillä on sama nimi.
     * 
     * @param obj Olio, johon toimipistettä verrataan.
     * 
     * @return Palauttaa true, jos toimipisteet ovat samat.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Office)) {
            return false;
        }
        Office other = (Office) obj;
        return this.name.equalsIgnoreCase(other.getName());
    }
}
