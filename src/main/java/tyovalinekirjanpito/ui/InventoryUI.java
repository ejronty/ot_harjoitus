
package tyovalinekirjanpito.ui;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
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

        ChoiceBox toolOrOffice = this.getChoiceBox();

        Button displayButton = new Button("Näytä");
        displayButton.setMaxWidth(Double.MAX_VALUE);
        displayButton.setOnAction(e -> {
            Object selection = this.listNode.getSelectionModel().getSelectedItem();
            if (selection == null) {
                redrawContent(selectionMessage());
                return;
            }
            if (this.getTableSelection().equals("tools")) {
                redrawContent(showOfficesWithToolView(selection.toString()));
            } else {
                redrawContent(showToolsInOfficeView(selection.toString()));
            }
        });

        Button addButton = this.getCreateNewButton();

        Button editButton = this.getEditButton();

        Button deleteButton = this.getDeleteButton();

        Button joinButton = new Button("Liitä");
        joinButton.setMaxWidth(Double.MAX_VALUE);
        joinButton.setOnAction(e -> {
            Object selection = this.listNode.getSelectionModel().getSelectedItem();
            if (selection == null) {
                redrawContent(selectionMessage());
                return;
            } 
            if (this.getTableSelection().equals("offices")) {
                redrawContent(joinOfficeAndToolView(selection.toString()));
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

    private ChoiceBox getChoiceBox() {
        ChoiceBox toolOrOffice = new ChoiceBox();
        toolOrOffice.getItems().addAll("Työvälineet", "Toimipisteet");
        toolOrOffice.setValue("Työvälineet");
        toolOrOffice.setMinWidth(120.0);
        toolOrOffice.setOnAction(e -> {
            redrawList();
            redrawContent(new VBox());
        });
        return toolOrOffice;
    }

    private Button getCreateNewButton() {
        Button createNewButton = new Button("Luo uusi");
        createNewButton.setMaxWidth(Double.MAX_VALUE);
        createNewButton.setOnAction(e -> {
            if ("tools".equals(this.getTableSelection())) {
                redrawContent(createNewToolView());
            } else {
                redrawContent(createNewOfficeView());
            }
        });
        return createNewButton;
    }

    private Button getEditButton() {
        Button editButton = new Button("Muokkaa");
        editButton.setMaxWidth(Double.MAX_VALUE);
        editButton.setOnAction(e -> {
            VBox content;
            Object selection = this.listNode.getSelectionModel().getSelectedItem();
            if (selection == null) {
                content = selectionMessage();
            } else {
                if ("tools".equals(this.getTableSelection())) {
                    content = editToolView(selection.toString());
                } else {
                    content = renameOfficeView(selection.toString());
                }
            }
            redrawContent(content);
        });
        return editButton;
    }

    private Button getDeleteButton() {
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
        return deleteButton;
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

    private VBox createNewToolView() {
        VBox vbox = new VBox();
        vbox.setSpacing(5);

        Label guide = new Label("Työvälineen nimi:");
        TextField nameField = new TextField();
        nameField.setTooltip(getTooltip());

        CheckBox checkbox = new CheckBox("Kuluva työväline");

        Button createButton = new Button("Luo");
        createButton.setOnAction(e -> {
            if (this.service.createTool(nameField.getText(), checkbox.isSelected())) {
                redrawList();
                nameField.setText("");
            } else {
                redrawContent(detailedErrorMessage());
            }
        });

        vbox.getChildren().addAll(guide, nameField, checkbox,
                new Label(""), createButton);

        return vbox;
    }

    private VBox createNewOfficeView() {
        VBox vbox = new VBox();
        vbox.setSpacing(5);

        Label guide = new Label("Toimipisteen nimi:");
        TextField nameField = new TextField();
        nameField.setTooltip(getTooltip());
        Button createButton = new Button("Luo");
        createButton.setOnAction(e -> {
            if (this.service.createOffice(nameField.getText())) {
                redrawList();
                nameField.setText("");
            } else {
                redrawContent(detailedErrorMessage());
            }
        });

        vbox.getChildren().addAll(guide, nameField, new Label(), createButton);
        return vbox;
    }

    private VBox editToolView(String name) {
        VBox node = new VBox();
        node.setSpacing(5);

        Label text = new Label("Muutetaan työvälinettä");
        Label oldName = new Label(name);

        Label guide = new Label("Anna uusi nimi:");
        TextField nameField = new TextField();
        nameField.setTooltip(getTooltip());
        nameField.setText(name);

        CheckBox checkbox = new CheckBox("Kuluva työväline");
        checkbox.setSelected(this.service.getToolConsumability(name));

        Button submitButton = new Button("Muuta");
        submitButton.setOnAction(e -> {
            if (this.service.editTool(name, nameField.getText(), checkbox.isSelected())) {
                redrawList();
                redrawContent(new VBox());
            } else {
                redrawContent(detailedErrorMessage());
            }
        });

        node.getChildren().addAll(text, oldName, new Label(""), 
                guide, nameField, checkbox,
                new Label(""), submitButton);
        return node;
    }

    private VBox renameOfficeView(String name) {
        VBox node = new VBox();
        node.setSpacing(5);

        Label text = new Label("Muutetaan toimipistettä");
        Label oldName = new Label(name);

        Label guide = new Label("Anna uusi nimi");
        TextField nameField = new TextField();
        nameField.setTooltip(getTooltip());
        nameField.setText(name);

        Button submitButton = new Button("Muuta");
        submitButton.setOnAction(e -> {
            if (this.service.renameOffice(name, nameField.getText())) {
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
        list.setMaxWidth(180);
        list.setMaxHeight(200);
        list.getItems().addAll(this.service.findOfficesWithoutTool(name));

        Label amount = new Label("Montako?");
        TextField amountField = this.getNumberInputField();

        Button submitButton = new Button("Valitse");
        submitButton.setOnAction(e -> {
            String officeName = list.getSelectionModel().getSelectedItem();
            if (officeName != null) {
                if (this.service.addToolToOffice(officeName, name, amountField.getText())) {
                    redrawContent(showOfficesWithToolView(name));
                } else {
                    redrawContent(detailedErrorMessage());
                }
            } else {
                redrawContent(selectionMessage());
            }
        });

        vbox.getChildren().addAll(q_part_1, q_part_2, itemName, 
                q_part_3, list, amount, amountField, submitButton);
        return vbox;
    }

    private VBox joinOfficeAndToolView(String name) {
        VBox vbox = new VBox();
        vbox.setSpacing(5);

        Label msg1 = new Label("Mikä työväline");
        Label msg2 = new Label("toimipisteeseen");
        Label msg3 = new Label(name);
        Label msg4 = new Label("liitetään?");

        ListView<String> list = new ListView();
        list.setMaxWidth(180);
        list.getItems().addAll(this.service.findToolsNotInOffice(name));

        Button submitButton = new Button("Valitse");
        submitButton.setOnAction(e -> {
            String itemName = list.getSelectionModel().getSelectedItem();
            if (itemName != null) {
                if (this.service.addToolToOffice(name, itemName, "1")) {
                    redrawContent(showToolsInOfficeView(name));
                } else {
                    redrawContent(detailedErrorMessage());
                }
            } else {
                redrawContent(selectionMessage());
            }
        });

        vbox.getChildren().addAll(msg1, msg2, msg3, msg4, 
                list, submitButton);
        return vbox;
    }

    private VBox showToolsInOfficeView(String name) {
        VBox wrapper = new VBox();
        Label msg1 = new Label("Toimipisteessä");
        Label msg2 = new Label(name + ":");

        Map<String, Integer> data = this.service.findToolsInOffice(name);

        TableView table = new TableView<>();
        table.setMaxHeight(200);
        table.setMaxWidth(180);
        table.setEditable(false);

        table.getItems().addAll(data.keySet());

        TableColumn<String, String> toolColumn = new TableColumn<>("Työväline");
        toolColumn.setMinWidth(116);
        toolColumn.setMaxWidth(130);
        toolColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()));

        TableColumn<String, String> amountColumn = new TableColumn<>("kpl");
        amountColumn.setMaxWidth(45);
        amountColumn.setCellValueFactory(cd -> new SimpleStringProperty(data.get(cd.getValue()).toString()));

        table.getColumns().addAll(toolColumn, amountColumn);

        Button removeButton = new Button("Poista valittu");
        removeButton.setOnAction(e -> {
            String selection = table.getSelectionModel().getSelectedItem().toString();
            if (selection == null) {
                redrawContent(selectionMessage());
                return;
            }
            if (this.service.removeToolFromOffice(name, selection)) {
                redrawContent(showToolsInOfficeView(name));
            } else {
                redrawContent(basicErrorMessage());
            }
        });

        wrapper.getChildren().addAll(msg1, msg2, new Label(), 
                table, new Label(""), removeButton);
        return wrapper;
    }

    private VBox showOfficesWithToolView(String name) {
        VBox wrapper = new VBox();
        Label msg1 = new Label("Työväline");
        Label msg2 = new Label(name);
        Label msg3 = new Label("löytyy seuraavista");
        Label msg4 = new Label("toimipisteistä:");

        ListView<String> list = new ListView<>();
        list.setMaxWidth(180);
        list.setMaxHeight(200);
        list.getItems().addAll(this.service.findOfficesContainingTool(name));

        Button removeButton = new Button("Poista valittu");
        removeButton.setOnAction(e -> {
            String selection = list.getSelectionModel().getSelectedItem();
            if (selection == null) {
                redrawContent(selectionMessage());
                return;
            }
            if (this.service.removeToolFromOffice(selection, name)) {
                redrawContent(showOfficesWithToolView(name));
            } else {
                redrawContent(basicErrorMessage());
            }
        });

        wrapper.getChildren().addAll(msg1, msg2, msg3, msg4,
                new Label(""), list, new Label(""), removeButton);

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

    private TextField getNumberInputField() {
        TextField input = new TextField();

        TextFormatter<Integer> formatter = new TextFormatter<>(
            new IntegerStringConverter(),
            1,
            c -> Pattern.matches("\\d*", c.getText()) ? c : null);

        input.setTextFormatter(formatter);
        input.setMaxWidth(60);
        input.setTooltip(new Tooltip("1-999"));
        return input;
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
                                + "    name text NOT NULL UNIQUE, \n"
                                + "    consumable text NOT NULL \n"
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
