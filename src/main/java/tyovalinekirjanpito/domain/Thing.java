
package tyovalinekirjanpito.domain;

/*
  Yleistä 'asiaa' kuvaava luokka. Tämän on tarkoitus toteuttaa ohjelman
  luokille yhteiset metodit, ja täten vähentää koodin toisteisuutta.
*/
public class Thing {
    
    protected String name;
    protected int id;
    
    public Thing(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    public Thing(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
}
