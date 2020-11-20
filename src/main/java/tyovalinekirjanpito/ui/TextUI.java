
package tyovalinekirjanpito.ui;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import tyovalinekirjanpito.domain.InventoryService;

/*
  Tästä on tarkoitus tulla pikainen tekstikäyttöliittymä käytettäväksi
  alkuvaiheessa ohjelman testailuun.
*/
public class TextUI {
    
    private Scanner scanner;
    private Map<String, String> options;
    private InventoryService service;
    
    public TextUI(Scanner scanner, InventoryService service) {
        this.scanner = scanner;
        this.service = service;
        
        this.options = new TreeMap<>();
        
        options.put("1", "1 Lisää työväline");
        options.put("2", "2 Näytä työvälineet");
        options.put("3", "3 Lisää toimipiste");
        options.put("4", "4 Näytä toimipisteet");
        options.put("5", "5 Yhdistä työväline toimipisteeseen");
        options.put("x", "x Lopeta");
    }
    
    public void start() {
        System.out.println("Mahtava työvälinekirjanpitosovellus!");
        System.out.println("");
        printOptions();
        while (true) {
            System.out.println("");
            System.out.println("Syötä komento: ");
            String command = scanner.nextLine();
            if (!this.options.containsKey(command)) {
                System.out.println("Epäkelpo komento!");
                printOptions();
            }
            
            if (command.equals("x")) {
                break;
            } else if (command.equals("1")) {
                createTool();
            } else if (command.equals("2")) {
                displayTools();
            } else if (command.equals("3")) {
                createOffice();
            } else if (command.equals("4")) {
                displayOffices();
            } else if (command.equals("5")) {
                addToolToAnOffice();
            }
        }
    }
    
    private void printOptions() {
        for (String option : this.options.values()) {
            System.out.println(option);
        }
    }
    
    private void createTool() {
        System.out.println("Anna nimi lisättävälle työvälineelle: ");
        String name = scanner.nextLine();
        
        if (!service.addTool(name.toLowerCase())) {
            System.out.println("Työväline löytyi jo ennestään.");
        } else {
            System.out.println("Lisätty onnistuneesti.");
        }
    }
    
    private void displayTools() {
        System.out.println("Ohjelmasta löytyy seuraavat työvälineet: ");
        System.out.println(service.displayTools());
    }
    
    private void createOffice() {
        System.out.println("Anna nimi lisättävälle toimipisteelle: ");
        String name = scanner.nextLine();
        
        if (!service.addOffice(name.toLowerCase())) {
            System.out.println("Tällä nimellä löytyi jo toimipiste.");
        } else {
            System.out.println("Lisäys onnistui.");
        }
    }
    
    private void displayOffices() {
        System.out.println("Ohjelmasta löytyy seuraavat toimipisteet "
                + "työvälineineen: ");
        System.out.println(service.displayOffices());
    }
    
    private void addToolToAnOffice() {
        System.out.println("Mihin toimistoon haluat lisätä työvälineen?");
        System.out.println("Anna toimiston nimi: ");
        String officeName = scanner.nextLine();
        System.out.println("");
        System.out.println("Minkä työvälineen haluat lisätä?");
        System.out.println("Anna työvälineen nimi: ");
        String toolName = scanner.nextLine();
        
        if (!service.addToolToOffice(officeName.toLowerCase(), toolName.toLowerCase())) {
            System.out.println("Lisääminen epäonnistui.");
        } else {
            System.out.println("Lisätty onnistuneesti.");
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InventoryService service = new InventoryService();
        
        TextUI textUI = new TextUI(scanner, service);
        textUI.start();
    }
    
}
