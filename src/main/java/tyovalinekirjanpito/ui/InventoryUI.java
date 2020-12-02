
package tyovalinekirjanpito.ui;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tyovalinekirjanpito.dao.OfficeDao;
import tyovalinekirjanpito.dao.SqlOfficeDao;
import tyovalinekirjanpito.dao.SqlToolDao;
import tyovalinekirjanpito.dao.ToolDao;
import tyovalinekirjanpito.domain.InventoryService;


public class InventoryUI extends Application{

    private InventoryService service;

    private VBox menuNode;
    private ListView listNode;
    private VBox contentNode;

    @Override
    public void init() throws Exception {

        Connection connection = this.setUpDB();
        ToolDao toolDao = new SqlToolDao(connection);
        OfficeDao officeDao = new SqlOfficeDao(connection);
        service = new InventoryService(toolDao, officeDao);
    }

    @Override
    public void start(Stage window) {
        window.setTitle("Työvälinekirjanpito");
        
        HBox layout = new HBox();
        layout.setSpacing(10);
        
        menuNode = createMainMenu();
        listNode = drawListedItems();
        contentNode = new VBox();
        contentNode.setSpacing(5);
        contentNode.setPadding(new Insets(5));
        
        layout.getChildren().add(menuNode);
        layout.getChildren().add(listNode);
        layout.getChildren().add(contentNode);
        
        Scene scene = new Scene(layout, 600, 400);
        
        window.setScene(scene);
        window.show();
    }  

    private VBox createMainMenu() {
        VBox mainMenu = new VBox();
        mainMenu.setSpacing(5);
        mainMenu.setPadding(new Insets(5));
        
        ChoiceBox toolOrOffice = new ChoiceBox();
        toolOrOffice.getItems().addAll("Työvälineet", "Toimipisteet");
        toolOrOffice.setValue("Työvälineet");
        toolOrOffice.setMinWidth(120.0);
        toolOrOffice.setOnAction(e -> {
            redrawList();
            redrawContent(new VBox());
        });
        
        Button displayButton = new Button("Näytä");
        displayButton.setMaxWidth(Double.MAX_VALUE);
        displayButton.setOnAction(e -> {
            Object selection = this.listNode.getSelectionModel().getSelectedItem();
            if (selection == null) {
                redrawContent(selectionMessage());
                return;
            }
            if (this.getTableSelection().equals("tools")) {
                redrawContent(notYetReadyMessage());
            } else {
                redrawContent(showToolsInOfficeView(selection.toString()));
            }
        });
        
        Button addButton = new Button("Luo uusi");
        addButton.setMaxWidth(Double.MAX_VALUE);
        addButton.setOnAction(e -> {
            VBox content = createNewItemView();
            redrawContent(content);
        });
        
        Button editButton = new Button("Muokkaa");
        editButton.setMaxWidth(Double.MAX_VALUE);
        editButton.setOnAction(e -> {
            VBox content;
            Object selection = this.listNode.getSelectionModel().getSelectedItem();
            if (selection == null) {
                content = selectionMessage();
            } else {
                content = editItemView(selection.toString());
            }
            redrawContent(content);
        });
        
        Button deleteButton = new Button("Poista");
        deleteButton.setMaxWidth(Double.MAX_VALUE);
        deleteButton.setOnAction(e -> {
           VBox content;
            Object selection = this.listNode.getSelectionModel().getSelectedItem();
            if (selection == null) {
                content = selectionMessage();
            } else {
                content = deleteItemView(selection.toString());
            }
            redrawContent(content);
        });
        
        Button joinButton = new Button("Liitä");
        joinButton.setMaxWidth(Double.MAX_VALUE);
        joinButton.setOnAction(e -> {
            Object selection = this.listNode.getSelectionModel().getSelectedItem();
            if (selection == null) {
                redrawContent(selectionMessage());
                return;
            } 
            if (this.getTableSelection().equals("offices")) {
                redrawContent(notYetReadyMessage());
            } else {
                redrawContent(addToolToOfficeView(selection.toString()));
            }
        });
        
        Button quitButton = new Button("Lopeta");
        quitButton.setMaxWidth(Double.MAX_VALUE);
        quitButton.setOnAction(e -> {
            this.stop();
        });
        
        mainMenu.getChildren().add(toolOrOffice);
        mainMenu.getChildren().add(new Label(""));
        mainMenu.getChildren().add(displayButton);
        mainMenu.getChildren().add(addButton);
        mainMenu.getChildren().add(editButton);
        mainMenu.getChildren().add(deleteButton);
        mainMenu.getChildren().add(joinButton);
        mainMenu.getChildren().add(new Label(""));
        mainMenu.getChildren().add(new Label(""));
        mainMenu.getChildren().add(new Label(""));
        mainMenu.getChildren().add(new Label(""));
        mainMenu.getChildren().add(new Label(""));
        mainMenu.getChildren().add(new Label(""));
        mainMenu.getChildren().add(new Label(""));
        mainMenu.getChildren().add(quitButton);
        
        return mainMenu;
    }

    private ListView drawListedItems() {
        ListView<String> list = new ListView<>();
        list.getItems().addAll(this.service.getItemList(this.getTableSelection()));
        
        return list;
    }
    
    private void redrawList() {
        this.listNode.getItems().clear();
        this.listNode.getItems().addAll(this.service.getItemList(this.getTableSelection()));
    }
    
    private void redrawContent(VBox newContent) {
        this.contentNode.getChildren().clear();
        this.contentNode.getChildren().addAll(newContent.getChildren());
    }
    
    private VBox createNewItemView() {
        VBox vbox = new VBox();
        vbox.setSpacing(5);

        Label guide = new Label("Anna nimi uudelle kohteelle");
        TextField nameField = new TextField();
        nameField.setTooltip(getTooltip());
        Button createButton = new Button("Luo");
        createButton.setOnAction(e -> {
            if (this.service.create(this.getTableSelection(), nameField.getText())) {
                redrawList();
                nameField.setText("");
            } else {
                redrawContent(detailedErrorMessage());
            }
        });

        vbox.getChildren().addAll(guide, nameField, createButton);
        return vbox;
    }
    
    private VBox editItemView(String name) {
        VBox node = new VBox();
        node.setSpacing(5);
        
        Label text = new Label("Muutetaan kohdetta");
        Label oldName = new Label(name);
        Label guide = new Label("Anna uusi nimi");
        
        TextField nameField = new TextField();
        nameField.setTooltip(getTooltip());
        Button submitButton = new Button("Muuta");
        submitButton.setOnAction(e -> {
            if (this.service.rename(this.getTableSelection(), name, nameField.getText())) {
                redrawList();
                redrawContent(new VBox());
            } else {
                redrawContent(detailedErrorMessage());
            }
        });

        node.getChildren().addAll(text, oldName, new Label(""), 
                guide, nameField, submitButton);
        return node;
    }
    
    private VBox deleteItemView(String name) {
        VBox vbox = new VBox();
        vbox.setSpacing(5);

        Label question = new Label("Poistetaanko kohde:");
        Label itemName = new Label(name);
        Button submitButton = new Button("Poista");
        submitButton.setOnAction(e -> {
            if (this.service.delete(this.getTableSelection(), name)) {
                redrawList();
                redrawContent(new VBox());
            } else {
                redrawContent(basicErrorMessage());
            }
        });

        vbox.getChildren().addAll(question, itemName, 
                new Label(""), submitButton);

        return vbox;
    }
    
    private VBox addToolToOfficeView(String name) {
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        
        Label q_part_1 = new Label("Mihin toimipisteeseen");
        Label q_part_2 = new Label("väline");
        Label itemName = new Label(name);
        Label q_part_3 = new Label("liitetään?");
        
        ListView<String> list = new ListView();
        list.getItems().addAll(this.service.findOfficesWithoutTool(name));
        
        Button submitButton = new Button("Valitse");
        submitButton.setOnAction(e -> {
            String officeName = list.getSelectionModel().getSelectedItem();
            if (officeName != null) {
                if (this.service.addToolToOffice(officeName, name)) {
                    redrawContent(new VBox());
                } else {
                    redrawContent(basicErrorMessage());
                }
            } else {
                redrawContent(selectionMessage());
            }
        });
        
        vbox.getChildren().addAll(q_part_1, q_part_2, itemName, 
                q_part_3, list, submitButton);
        return vbox;
    }
    
    private VBox showToolsInOfficeView(String name) {
        VBox wrapper = new VBox();
        Label msg1 = new Label("Toimipisteeseen");
        Label msg2 = new Label(name);
        Label msg3 = new Label("liitetyt välineet:");

        ListView<String> list = new ListView<>();
        list.getItems().addAll(this.service.findToolsInOffice(name));
 
        wrapper.getChildren().addAll(msg1, msg2, msg3, new Label(), list);
        return wrapper;
    }

    private VBox selectionMessage() {
        VBox wrapper = new VBox();
        Label instruction = new Label("Valitse ensin \n"
                + "toiminnon kohde.");

        wrapper.getChildren().add(instruction);
        return wrapper;
    }

    private VBox basicErrorMessage() {
        VBox wrapper = new VBox();
        Label msg = new Label("Jotain meni vikaan.");

        wrapper.getChildren().add(msg);
        return wrapper;
    }

    private VBox detailedErrorMessage() {
        VBox wrapper = new VBox();
        Label msg = new Label("Jotain meni vikaan.");
        Label instruction = new Label("Varmista, että\n"
                + "antamasi syöte\n"
                + "täyttää vaatimukset.");

        wrapper.getChildren().addAll(msg, new Label(), instruction);
        return wrapper;
    }
    
    private VBox notYetReadyMessage() {
        VBox wrapper = new VBox();
        Label msg = new Label("Toiminto ei ole");
        Label ant = new Label("vielä valmis.");

        wrapper.getChildren().addAll(msg, ant);
        return wrapper;
    }

    private Tooltip getTooltip() {
        return new Tooltip("[a-ö], [0-9],  , -, _");
    }

    private String getTableSelection() {
        ChoiceBox selector = new ChoiceBox();

        for (Node node : this.menuNode.getChildren()) {
            if (node instanceof ChoiceBox) {
                selector = (ChoiceBox) node;
            }
        }

        String selection = selector.getValue().toString();

        if ("Työvälineet".equals(selection)) {
            return "tools";
        } else {
            return "offices";
        }
    }

    @Override
    public void stop() {
        //Tietokannan sulku vielä?
        Platform.exit();
    }
    
    public static void main(String[] args) {
        launch(InventoryUI.class);
    }
    
    private Connection setUpDB() {

        Properties properties = new Properties();
        Connection dbConnection;

        try {
            properties.load(new FileInputStream("config.properties"));
            String url = "jdbc:sqlite:" + properties.getProperty("dbPath");
            dbConnection = DriverManager.getConnection(url);

            String createTools = "CREATE TABLE IF NOT EXISTS tools (\n"
                                + "    id integer PRIMARY KEY, \n"
                                + "    name text NOT NULL UNIQUE \n"
                                + ");";

            String createOffices = "CREATE TABLE IF NOT EXISTS offices (\n"
                                + "    id integer PRIMARY KEY, \n"
                                + "    name text NOT NULL UNIQUE, \n"
                                + "    tools text DEFAULT '' \n"
                                + ");";

            Statement statement = dbConnection.createStatement();
            statement.execute(createTools);
            statement.execute(createOffices);

            return dbConnection;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
