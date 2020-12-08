
package tyovalinekirjanpito.domain;

/**
 * Yksittäistä työvälinettä kuvaava luokka.
 */

public class Tool extends Thing {

    /**
     * Työvälineen luomiseen pelkän nimen perusteella.
     * 
     * @param name Työvälineen nimi
     */
    public Tool(String name) {
        super(name);
    }

    /**
     * Luo uuden työvälineen, jolla on nimen lisäksi myös id-tunnus.
     * Tarkoitettu tilanteeseen, kun työvälineen tiedot luetaan ohjelmaan 
     * tietokannasta, jolloin myös id on saatavilla.
     * 
     * @param name Työvälineen nimi.
     * @param id Työvälineen id-tunnus.
     * 
     */
    public Tool(String name, int id) {
        super(name, id);
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
