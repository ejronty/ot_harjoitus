
package tyovalinekirjanpito.dao;

import java.util.Collection;
import java.util.TreeMap;
import tyovalinekirjanpito.domain.Office;

/*
  Tätä dao-implementaatiota on lopulta tarkoitus käyttää lähinnä
  testauksessa, mutta se saa kelvata sovelluksen käyttöön vielä,
  kun tietokantaa ei ole luotu.
*/

public class TestOfficeDao implements OfficeDao {
    
    private TreeMap<Integer, Office> offices;
    private int counter;
    
    public TestOfficeDao() {
        this.offices = new TreeMap<>();
        this.counter = 1;
    }

    @Override
    public Collection<Office> getAll() throws Exception {
        return this.offices.values();
    }

    @Override
    public Office findByName(String name) throws Exception {
        return this.getAll()
                .stream()
                .filter(office -> office.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateToolList(Office office) throws Exception {
        // Method unnecessary in non-database-using implementation.
    }

    @Override
    public void create(String table, String name) throws Exception {
        if (this.findByName(name) == null) {
            Office office = new Office(name, this.counter);
            this.counter++;
            this.offices.put(office.getId(), office);
        }
    }

    @Override
    public void rename(String table, int id, String newName) throws Exception {
        this.offices.get(id).setName(newName);
    }

    @Override
    public void delete(String table, int id) throws Exception {
        this.offices.remove(id);
    }


}
