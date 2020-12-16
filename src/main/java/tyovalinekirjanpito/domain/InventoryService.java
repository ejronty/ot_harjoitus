
package tyovalinekirjanpito.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import tyovalinekirjanpito.dao.OfficeDao;
import tyovalinekirjanpito.dao.ToolDao;

/**
 * Sovelluslogiikasta huolehtiva luokka. Monet luokan metodeista
 * käsittelevät sekä työvälineitä että toimipisteitä, sillä 
 * molemmissa tapauksessa logiikka olisi hyvin samanlainen.
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
     */
    public boolean addToolToOffice(String officeName, String toolName, String amount) {

        try {
            Office office = this.officeDao.findByName(officeName);

            if (office.addTool(toolName, this.validateNumberInput(amount))) {
                this.officeDao.updateToolList(office);
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
     * Kaikki työvälineet tai toimipisteet.
     * 
     * @param type Määrittää, haetaanko työvälineet vai toimipisteet.
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
     */
    public Collection<String> findOfficesWithoutTool(String toolName) {
        try {
            return this.officeDao.getAll()
                    .stream()
                    .filter(office -> !office.containsTool(toolName))
                    .map(office -> office.getName())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Etsii ne toimipisteet, joissa on annettu työväline.
     * 
     * @param toolName Työvälineen nimi.
     */
    public Collection<String> findOfficesContainingTool(String toolName) {
        try {
            return this.officeDao.getAll()
                    .stream()
                    .filter(office -> office.containsTool(toolName))
                    .map(office -> office.getName())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Etsii ne työvälineet, joita ei ole annetussa toimipisteessä.
     * 
     * @param officeName Toimipisteen nimi.
     */
    public Collection<String> findToolsNotInOffice(String officeName) {
        try {
            Office office = this.officeDao.findByName(officeName);
            return this.toolDao.getAll()
                    .stream()
                    .map(tool -> tool.getName())
                    .filter(toolName -> !office.containsTool(toolName))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Annetusta toimipisteestä löytyvät työvälineet.
     * 
     * @param officeName Toimipisteen nimi.
     */
    public Map<String, Integer> findToolsInOffice(String officeName) {
        try {
            Office office = this.officeDao.findByName(officeName);
            return office.getToolsWithAmounts();
        } catch (Exception e) {
            return new TreeMap<>();
        }
    }

    public boolean getToolConsumability(String name) {
        try {
            Tool tool = this.toolDao.findByName(name);
            return tool.isConsumable();
        } catch (Exception e) {
            return false;
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
