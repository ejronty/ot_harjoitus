
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

    /**
     * Tieto työvälineen kuluvuudesta.
     * 
     * @return Palauttaa true, jos työväline on kuluva. False muuten. 
     */
    public boolean isConsumable() {
        return consumable;
    }

    /**
     * Metodi kuluvuuden asettamiseen. Lähinnä testauskäyttöön. Tätä ei 
     * tarvita ohjelman normaalissa toiminnassa.
     * 
     * @param consumable Uusi arvo kuluvuudelle.
     */
    public void setConsumable(boolean consumable) {
        this.consumable = consumable;
    }

    /**
     * Metodi työvälineiden vertaamiseen muihin olioihin.
     * Kaksi työvälinettä katsotaan samaksi, jos niillä on sama nimi.
     * 
     * @param obj Olio, johon työvälinettä verrataan.
     * 
     * @return Palauttaa true, jos työvälineet ovat samat.
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
