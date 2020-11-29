
package tyovalinekirjanpito.domain;

/*
  Yksittäistä työvälinettä kuvaava luokka.
*/

public class Tool extends Thing {

    public Tool(String name) {
        super(name);
    }

    public Tool(String name, int id) {
        super(name, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tool)) {
            return false;
        }
        
        Tool other = (Tool) obj;
        return this.name.equalsIgnoreCase(other.getName());
    }

}
