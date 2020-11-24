
package tyovalinekirjanpito.ui;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import tyovalinekirjanpito.dao.OfficeDao;
import tyovalinekirjanpito.dao.TestOfficeDao;
import tyovalinekirjanpito.dao.TestToolDao;
import tyovalinekirjanpito.dao.ToolDao;
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
        
        options.put("1", "1 Lisää uusi työväline");
        options.put("2", "2 Näytä työvälineet");
        options.put("3", "3 Muokkaa työvälinettä");
        options.put("4", "4 Lisää uusi toimipiste");
        options.put("5", "5 Näytä toimipisteet");
        options.put("6", "6 Muokkaa toimipistettä");
        options.put("7", "7 Näytä toimipisteet työvälineineen");
        options.put("8", "8 Yhdistä työväline toimipisteeseen");
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
                System.out.println("Epäkelpo komento! \n");
                printOptions();
            }
            
            if (command.equals("x")) {
                break;
            } else {
                respondToCommand(command);
            }
        }
    }
    
    private void respondToCommand(String command) {
        
        switch (command) {
            case "1":
                createTool();
                break;
            case "2":
                displayTools();
                break;
            case "3":
                renameTool();
                break;
            case "4":
                createOffice();
                break;
            case "5":
                displayOffices();
                break;
            case "6":
                renameOffice();
                break;
            case "7":
                displayOfficesWithTools();
                break;
            case "8":
                addToolToAnOffice();
                break;
            default:
                break;
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
        
        if (!service.createTool(name)) {
            System.out.println("Työväline löytyi jo ennestään.");
        } else {
            System.out.println("Lisätty onnistuneesti.");
        }
    }
    
    private void displayTools() {
        System.out.println("Ohjelmasta löytyy seuraavat työvälineet: ");
        System.out.println(service.displayTools());
    }
    
    private void renameTool() {
        this.displayTools();
        System.out.println("Mitä työvälinettä haluat muokata?");
        String oldName = scanner.nextLine();
        System.out.println("Anna työvälineelle uusi nimi: ");
        String newName = scanner.nextLine();
        
        if (service.renameTool(oldName, newName)) {
            System.out.println("Nimi muutettu onnistuneesti. \n");
        } else {
            System.out.println("Nimen muutos epäonnistui.");
        }
    }
    
    private void createOffice() {
        System.out.println("Anna nimi lisättävälle toimipisteelle: ");
        String name = scanner.nextLine();
        
        if (!service.createOffice(name)) {
            System.out.println("Tällä nimellä löytyi jo toimipiste.");
        } else {
            System.out.println("Lisäys onnistui.");
        }
    }
    
    private void displayOffices() {
        System.out.println("Ohjelmasta löytyy seuraavat toimipisteet:");
        System.out.println(service.displayOffices());
    }
    
    private void renameOffice() {
        this.displayOffices();
        System.out.println("Minkä toimipisteen haluat uudelleennimetä?");
        String oldName = scanner.nextLine();
        System.out.println("Anna toimipisteelle uusi nimi: ");
        String newName = scanner.nextLine();
        
        if (service.renameOffice(oldName, newName)) {
            System.out.println("Toimipisteen nimi muutettu.");
        } else {
            System.out.println("Nimen muutos epäonnistui.");
        }
    }
    
    private void displayOfficesWithTools() {
        System.out.println("Ohjelmasta löytyy seuraavat toimipisteet "
                + "työvälineineen: ");
        System.out.println(service.displayOfficesWithTools());
    }
    
    private void addToolToAnOffice() {
        this.displayOffices();
        System.out.println("Mihin toimipisteeseen haluat lisätä työvälineen?");
        String officeName = scanner.nextLine();
        System.out.println("");
        this.displayTools();
        System.out.println("Minkä työvälineen haluat lisätä?");
        String toolName = scanner.nextLine();
        
        if (!service.addToolToOffice(officeName.toLowerCase(), toolName.toLowerCase())) {
            System.out.println("Lisääminen epäonnistui.");
        } else {
            System.out.println("Lisätty onnistuneesti.");
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ToolDao toolDao = new TestToolDao();
        OfficeDao officeDao = new TestOfficeDao();
        InventoryService service = new InventoryService(toolDao, officeDao);
        
        TextUI textUI = new TextUI(scanner, service);
        textUI.start();
    }
    
}
