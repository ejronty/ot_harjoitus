
package tyovalinekirjanpito.domain;

/**
 * Yksittäistä työvälinettä kuvaava luokka.
 */

public class Tool extends Thing {
    
    private boolean consumable;

    /**
     * Työvälineen luomiseen ilman id:tä.
     * 
     * @param name Työvälineen nimi.
     * @param consumable Onko kyseessä kuluva työväline?
     */
    public Tool(String name, boolean consumable) {
        super(name);
        this.consumable = consumable;
    }

    /**
     * Luo uuden työvälineen, jolla on nimen lisäksi myös id-tunnus.
     * Tarkoitettu tilanteeseen, kun työvälineen tiedot luetaan ohjelmaan 
     * tietokannasta, jolloin myös id on saatavilla.
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
