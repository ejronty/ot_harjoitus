
package tyovalinekirjanpito.domain;

/**
 * Yksittäistä työvälinettä kuvaava luokka.
 */

public class Tool extends Thing {
    
    private boolean consumable;

    /**
     * Luo uuden työvälineen.
     * 
     * @param name Työvälineen nimi.
     * @param id Työvälineen id-tunnus.
     * @param consumable Onko kyseessä kuluva työväline?
     * 
     */
    public Tool(String name, int id, boolean consumable) {
        super(name, id);
        this.consumable = consumable;
    }

    public boolean isConsumable() {
        return consumable;
    }

    // Tätä tarvitaan lähinnä testauksessa.
    public void setConsumable(boolean consumable) {
        this.consumable = consumable;
    }

    /**
     * Metodi työvälineiden vertaamiseen muihin olioihin.
     * Kaksi työvälinettä katsotaan samaksi, jos niillä on sama nimi.
     * 
     * @param obj Olio, johon työvälinettä verrataan.
     * 
     * @return Ovatko oliot samat.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tool)) {
            return false;
        }
        
        Tool other = (Tool) obj;
        return this.name.equalsIgnoreCase(other.getName());
    }

}
