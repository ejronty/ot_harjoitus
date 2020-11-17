
package tyovalinekirjanpito.domain;

/*
  Yksittäistä työvälinettä kuvaava luokka.
*/

public class Tool {
    
    private String name;
    
    public Tool(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tool)) {
            return false;
        }
        
        Tool other = (Tool) obj;
        return this.name.equalsIgnoreCase(other.getName());
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
}
