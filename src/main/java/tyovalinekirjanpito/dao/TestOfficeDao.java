
package tyovalinekirjanpito.dao;

import java.util.Collection;
import java.util.TreeMap;
import tyovalinekirjanpito.domain.Office;
import tyovalinekirjanpito.domain.Tool;

/*
  Tätä dao-implementaatiota on lopulta tarkoitus käyttää lähinnä
  testauksessa, mutta se saa kelvata sovelluksen käyttöön vielä,
  kun tietokantaa ei ole luotu.
*/

public class TestOfficeDao implements OfficeDao {
    
    private TreeMap<String, Office> offices;
    
    public TestOfficeDao() {
        this.offices = new TreeMap<>();
    }

    @Override
    public Office create(Office office) throws Exception {
        this.offices.put(office.getName(), office);
        return office;
    }

    @Override
    public Collection<Office> getAll() {
        return this.offices.values();
    }

    @Override
    public Office findByName(String name) throws Exception {
        return this.offices.get(name);
    }
    
    @Override
    public boolean exists(String name) {
        return this.offices.containsKey(name);
    }

    @Override
    public void rename(String oldName, String newName) throws Exception {
        Office office = this.offices.get(oldName);
        office.setName(newName);
        
        this.offices.remove(oldName);
        this.offices.put(newName, office);
    }

    // Tämän toteutusta pitää vielä pohtia..
    /*
    @Override
    public void linkTogether(Office office, Tool tool) throws Exception {
        office.addTool(tool);
    }
    */
}
