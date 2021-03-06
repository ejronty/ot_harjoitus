
package tyovalinekirjanpito.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import tyovalinekirjanpito.dao.OfficeDao;
import tyovalinekirjanpito.dao.ToolDao;

/**
 * Sovelluslogiikasta huolehtiva luokka. 
 */
public class InventoryService {

    private ToolDao toolDao;
    private OfficeDao officeDao;

    public InventoryService(ToolDao toolDao, OfficeDao officeDao) {
        this.toolDao = toolDao;
        this.officeDao = officeDao;
    }

    /**
     * Luo uuden työvälineen.
     * 
     * @param itemName Lisättävän kohteen nimi.
     * @param consumable Onko kyseessä kuluva työväline?
     * 
     * @return Palauttaa true, jos työvälineen lisäys onnistui.
     */
    public boolean createTool(String itemName, boolean consumable) {
        String name = this.processInput(itemName);

        try {
            this.toolDao.create(name, consumable);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Luo uuden toimipisteen.
     * 
     * @param officeName Luotavan toimipisteen nimi.
     * 
     * @return Palauttaa true, jos toimipisteen lisääminen onnistui.
     */
    public boolean createOffice(String officeName) {
        String name = this.processInput(officeName);

        try {
            this.officeDao.create(name);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Muokkaa työvälineen tietoja.
     * 
     * @param oldName Muokattavan työvälineen tämänhetkinen nimi.
     * @param newName Työvälineen uusi nimi.
     * @param consumable Onko työväline kuluva?
     * 
     * @return Palauttaa true, jos muutos onnistui.
     */
    public boolean editTool(String oldName, String newName, boolean consumable) {
        String name = this.processInput(newName);

        try {
            Tool tool = this.toolDao.findByName(oldName);
            this.toolDao.edit(tool.getId(), name, consumable);
            if (!oldName.equals(name)) {
                this.updateToolInsideOffice(oldName, name);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void updateToolInsideOffice(String oldName, String newName) throws Exception {
        for (Office office : this.officeDao.getAll()) {
            if (office.containsTool(oldName)) {
                int amount = office.getAmount(oldName);
                office.removeTool(oldName);
                office.addTool(newName, amount);
                this.officeDao.updateToolList(office);
            }
        }
    }

    /**
     * Uudelleennimeää toimipisteen.
     * 
     * @param oldName Nimi, jolla muokattava kohde tällä hetkellä
     * esiintyy ohjelmassa.
     * @param newName Käyttäjän syöttämä uusi nimi kohteelle.
     * 
     * @return Palauttaa true, jos muutos onnistui.
     */
    public boolean renameOffice(String oldName, String newName) {
        String name = this.processInput(newName);

        try {
            Office office = this.officeDao.findByName(oldName);
            this.officeDao.rename(office.getId(), name);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Kohteen poistamiseen tarkoitettu metodi.
     * 
     * @param table Poistettavan kohteen tyyppi. Ei käyttäjän syöte.
     * @param name Poistettavan kohteen nimi.
     * 
     * @return Palauttaa true, jos poisto onnistui.
     */
    public boolean delete(String table, String name) {

        try {
            if ("offices".equals(table)) {
                Office office = this.officeDao.findByName(name);
                this.officeDao.delete(table, office.getId());
            } else {
                Tool tool = this.toolDao.findByName(name);
                this.toolDao.delete(table, tool.getId());

                this.deleteToolFromOffices(name);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void deleteToolFromOffices(String toolName) throws Exception {
        for (Office office : this.officeDao.getAll()) {
            if (office.removeTool(toolName)) {
                this.officeDao.updateToolList(office);
            }
        }
    }

    /**
     * Työvälineen lisääminen toimipisteeseen.
     * 
     * @param officeName Sen toimipisteen nimi, johon työväline halutaan
     * lisätä.
     * @param toolName Lisättävän työvälineen nimi.
     * @param amount Montako kappaletta työvälinettä lisätään.
     * 
     * @return Palauttaa true, jos työvälineen liittäminen toimipisteeseen
     * onnistui.
     */
    public boolean addToolToOffice(String officeName, String toolName, String amount) {

        try {
            Office office = this.officeDao.findByName(officeName);

            if (office.addTool(toolName, this.validateNumberInput(amount))) {
                this.officeDao.updateToolList(office);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Työvälineen poistaminen toimipisteestä.
     * 
     * @param officeName Sen toimipisteen nimi, josta työväline
     * halutaan poistaa.
     * @param toolName Poistettavan työvälineen nimi.
     * 
     * @return Palauttaa true, jos työvälineen poistaminen toimipisteestä
     * onnistui.
     */
    public boolean removeToolFromOffice(String officeName, String toolName) {
        try {
            Office office = this.officeDao.findByName(officeName);

            if (office.removeTool(toolName)) {
                this.officeDao.updateToolList(office);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Muuttaa työvälineen määrää toimipisteessä.
     * 
     * @param officeName Sen toimipisteen nimi, jossa olevan työvälineen
     * määrää halutaan muuttaa.
     * @param toolName Sen työvälineen nimi, jonka määrää halutaan muuttaa.
     * @param amount Käyttäjän syöttämä työvälineen määrä / määrän muutos.
     * @param actionType Parametri määrittää, miten käyttäjän syöttämää
     * lukumäärää käytetään.
     * 
     * @return Palauttaa true, jos määrän päivitys onnistui.
     */
    public boolean updateToolAmountInOffice(String officeName, String toolName, String amount, String actionType) {
        try {
            Office office = this.officeDao.findByName(officeName);
            int oldAmount = office.getAmount(toolName);
            int newAmount = this.validateNumberInput(amount);

            if (actionType.equals("add")) {
                newAmount = oldAmount + newAmount;
            } else if (actionType.equals("use")) {
                newAmount = oldAmount - newAmount;
            }

            if (!office.updateAmount(toolName, newAmount)) {
                return false;
            }
            this.officeDao.updateToolList(office);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Siirtää työvälineitä toimipisteestä toiseen.
     * 
     * @param officeFrom Sen toimipisteen nimi. josta työvälineitä siirretään.
     * @param officeTo Sen toimipisteen nimi, johon siirretään.
     * @param tool Sen työvälineen nimi, jota halutaan siirtää.
     * @param amount Siirrettävä määrä.
     * 
     * @return Palauttaa true, jos siirto onnistui.
     */
    public boolean transferToolsBetweenOffices(String officeFrom, String officeTo, String tool, String amount) {
        try {
            Office fromOffice = this.officeDao.findByName(officeFrom);
            Office toOffice = this.officeDao.findByName(officeTo);
            int amountToTransfer = this.validateNumberInput(amount);

            if (fromOffice.updateAmount(tool, fromOffice.getAmount(tool) - amountToTransfer) &&
                    toOffice.updateAmount(tool, toOffice.getAmount(tool) + amountToTransfer)) {
                this.officeDao.updateToolList(fromOffice);
                this.officeDao.updateToolList(toOffice);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Hakee kaikki työvälineet tai toimipisteet.
     * 
     * @param type Määrittää, haetaanko työvälineet vai toimipisteet.
     * 
     * @return Palauttaa aakkosittain järjestetyn listan työvälineistä
     * tai toimipisteistä. Palauttaa tyhjän listan, jos jotain menee vikaan
     * tietokannasta lukiessa.
     */
    public Collection<String> getItemList(String type) {

        try {
            if ("tools".equals(type)) {
                return this.toolDao.getAll()
                        .stream()
                        .map(tool -> tool.getName())
                        .sorted()
                        .collect(Collectors.toList());
            } else {
                return this.officeDao.getAll()
                        .stream()
                        .map(office -> office.getName())
                        .sorted()
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Etsii ne toimipisteet, joissa ei vielä ole annettua työvälinettä.
     * 
     * @param toolName Työvälineen nimi.
     * 
     * @return Palauttaa aakkosittain järjestetyn listan toimipisteistä
     * joissa ei ole parametrina annettua työvälinettä. Palauttaa tyhjän
     * listan, jos jotain menee vikaan tietokannasta lukiessa.
     */
    public Collection<String> findOfficesWithoutTool(String toolName) {
        try {
            return this.officeDao.getAll()
                    .stream()
                    .filter(office -> !office.containsTool(toolName))
                    .map(office -> office.getName())
                    .sorted()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Etsii ne toimipisteet, joissa on annettu työväline.
     * 
     * @param toolName Työvälineen nimi.
     * 
     * @return Palauttaa luettelon toimipisteistä, joista löytyy parametrina
     * annettu työväline, ja työvälineen määristä näissä toimipisteissä.
     * Palauttaa tyhjän luettelon, jos jotain menee vikaan tietokannasta
     * lukiessa.
     */
    public Map<String, Integer> findOfficesContainingTool(String toolName) {
        try {
            return this.officeDao.getAll()
                    .stream()
                    .filter(office -> office.containsTool(toolName))
                    .sorted(Comparator.comparing(Office::getName))
                    .collect(Collectors.toMap(office -> office.getName(), office -> office.getAmount(toolName)));
        } catch (Exception e) {
            return new TreeMap<>();
        }
    }

    /**
     * Etsii ne työvälineet, joita ei ole annetussa toimipisteessä.
     * 
     * @param officeName Toimipisteen nimi.
     * 
     * @return Palauttaa aakkosittain järjestetyn listan työvälineista,
     * joita ei löydy parametrina annetusta toimipisteestä.
     * Palauttaa tyhjän listan, jos jotain menee vikaan tietokannasta
     * lukiessa.
     */
    public Collection<String> findToolsNotInOffice(String officeName) {
        try {
            Office office = this.officeDao.findByName(officeName);
            return this.toolDao.getAll()
                    .stream()
                    .map(tool -> tool.getName())
                    .filter(toolName -> !office.containsTool(toolName))
                    .sorted()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Annetusta toimipisteestä löytyvät työvälineet.
     * 
     * @param officeName Toimipisteen nimi.
     * 
     * @return Palauttaa luettelon parametrina nimetystä toimipisteestä
     * löytyvistä työvälineistä sekä niiden määristä.
     * Palauttaa tyhjän luettelon, jos jotain menee vikaan tietokannasta
     * lukiessa.
     */
    public Map<String, Integer> findToolsInOffice(String officeName) {
        try {
            Office office = this.officeDao.findByName(officeName);
            return office.getToolsWithAmounts();
        } catch (Exception e) {
            return new TreeMap<>();
        }
    }

    /**
     * Hakee tiedon työvälineen kuluvuudesta.
     * 
     * @param name Etsittävän työvälineen nimi.
     * 
     * @return Palauttaa true, jos työväline on kuluva, muuten false.
     * Palauttaa false myös, jos jotain menee vikaan tietokannasta lukiessa.
     */
    public boolean getToolConsumability(String name) {
        try {
            Tool tool = this.toolDao.findByName(name);
            return tool.isConsumable();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Hakee työvälineen määrän tietyssä toimipisteessä.
     * 
     * @param officeName Sen toimipisteen nimi, josta työvälineen määrä
     * halutaan selvittää.
     * @param toolName Sen työvälineen nimi, jonka määrä halutaan selvittää.
     * 
     * @return Palauttaa työvälineen määrän. Palauttaa null, 
     * jos jotain menee vikaan tietokannasta lukiessa.
     */
    public Integer getAmountOfToolInOffice(String officeName, String toolName) {
        try {
            Office office = this.officeDao.findByName(officeName);
            return office.getAmount(toolName);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Hakee työvälineen määrän kaikista muista toimipisteistä, paitsi
     * parametrina annetusta.
     * 
     * @param officeName Sen toimipisteen nimi, jonka tietoja EI haluta.
     * @param toolName Sen työvälineen nimi, jonka tiedot halutaan hakea.
     * 
     * @return Palauttaa luettelon toimipisteistä, ja työvälineen
     * määristä näissä toimipisteissä. Palauttaa tyhjän luettelon,
     * jos jotain menee vikaan tietokannasta lukiessa.
     */
    public Map<String, Integer> getToolDataFromOtherOffices(String officeName, String toolName) {
        try {
            return this.officeDao.getAll()
                    .stream()
                    .filter(office -> !office.getName().equals(officeName))
                    .sorted(Comparator.comparing(Office::getName))
                    .collect(Collectors.toMap(office -> office.getName(), office -> office.getAmount(toolName)));
        } catch (Exception e) {
            return new TreeMap<>();
        }
    }

    private String processInput(String input) {
        String result = input.replaceAll("[^\\w\\säÄöÖåÅ-]", "");
        result = result.strip();
        if (result.length() >= 1) {
            return result.substring(0, 1).toUpperCase() + 
                    result.substring(1, Math.min(25, result.length())).toLowerCase();
        }
        return null;
    }

    private int validateNumberInput(String amount) throws Exception {
        int value = Integer.parseInt(amount);

        if (value <= 0 || value > 999) {
            throw new Exception("Unacceptable input!");
        }
        return value;
    }
}
