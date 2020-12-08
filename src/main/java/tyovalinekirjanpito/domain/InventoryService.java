
package tyovalinekirjanpito.domain;

import java.util.ArrayList;
import java.util.Collection;
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
     * Uuden asian luomiseen käytettävä metodi.
     * 
     * @param table tietokantataulu, johon tietoa halutaan lisätä.
     * Tämä ei tule käyttäjän syötteestä, vaan käyttöliittymä huolehtii
     * lähetettävän arvon valinnasta.
     * @param itemName Lisättävän kohteen nimi.
     */
    public boolean create(String table, String itemName) {
        String name = this.processInput(itemName);

        try {
            if ("tools".equals(table)) {
                this.toolDao.create(table, name);
            } else if ("offices".equals(table)) {
                this.officeDao.create(table, name);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * Kohteen uudelleennimeämiseen käytettävä metodi.
     * 
     * @param table Uudelleennimettävän asian tyyppi, työväline vai 
     * toimipiste. Ei käyttäjän syöte.
     * @param oldName Nimi, jolla muokattava kohde tällä hetkellä
     * esiintyy ohjelmassa.
     * @param newName Käyttäjän syöttämä uusi nimi kohteelle.
     */
    public boolean rename(String table, String oldName, String newName) {
        String name = this.processInput(newName);

        try {
            if ("offices".equals(table)) {
                Office office = this.officeDao.findByName(oldName);
                this.officeDao.rename(table, office.getId(), name);
            } else {
                Tool tool = this.toolDao.findByName(oldName);
                this.toolDao.rename(table, tool.getId(), name);

                this.updateToolInsideOffice(tool, name);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void updateToolInsideOffice(Tool tool, String newName) throws Exception {
        for (Office office : this.officeDao.getAll()) {
            if (office.getTools().contains(tool)) {
                office.removeTool(tool);
                office.addTool(new Tool(newName));
                this.officeDao.updateToolList(office);
            }
        }
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
                
                this.deleteToolFromOffices(tool);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void deleteToolFromOffices(Tool tool) throws Exception {
        for (Office office : this.officeDao.getAll()) {
            if (office.removeTool(tool)) {
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
     */
    public boolean addToolToOffice(String officeName, String toolName) {

        try {
            Office office = this.officeDao.findByName(officeName);
            Tool tool = this.toolDao.findByName(toolName);
            
            if (office.addTool(tool)) {
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
            Tool tool = this.toolDao.findByName(toolName);

            if (office.removeTool(tool)) {
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
                        .collect(Collectors.toList());
            } else {
                return this.officeDao.getAll()
                        .stream()
                        .map(office -> office.getName())
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
            Tool tool = this.toolDao.findByName(toolName);
            return this.officeDao.getAll()
                    .stream()
                    .filter(office -> !office.getTools().contains(tool))
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
            Tool tool = this.toolDao.findByName(toolName);
            return this.officeDao.getAll()
                    .stream()
                    .filter(office -> office.getTools().contains(tool))
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
                    .filter(tool -> !office.getTools().contains(tool))
                    .map(tool -> tool.getName())
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
    public Collection<String> findToolsInOffice(String officeName) {
        try {
            Office office = this.officeDao.findByName(officeName);
            return office.getTools()
                    .stream()
                    .map(tool -> tool.getName())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
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
}
