
package tyovalinekirjanpito.ui;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tyovalinekirjanpito.domain.InventoryService;


public class InventoryUI extends Application{
    
    private InventoryService service;
    
    @Override
    public void init() {
        service = new InventoryService();
        //Tilapäistä testidataa
        service.addTool("veitsi");
        service.addTool("haarukka");
        service.addTool("kirves");
        service.addTool("vasara");
        service.addOffice("keittiö");
        service.addOffice("vaja");
        service.addOffice("tyhjiö");
        service.addToolToOffice("keittiö", "veitsi");
        service.addToolToOffice("keittiö", "haarukka");
        service.addToolToOffice("vaja", "kirves");
        service.addToolToOffice("vaja", "vasara");
    }
    
    @Override
    public void start(Stage window) {
        window.setTitle("Työvälinekirjanpito");
        
        BorderPane layout = new BorderPane();
        
        VBox mainMenu = new VBox();
        mainMenu.setSpacing(5);
        
        Label text = new Label("Tervetuloa");
        
        ChoiceBox toolOptions = new ChoiceBox(FXCollections.observableArrayList(
            "Näytä työvälineet", "Lisää uusi", "Muokkaa työvälinettä",
                "Liitä toimipisteeseen"
        ));
        
//        toolOptions.getSelectionModel().selectedIndexProperty().addListener(
//                (ObservableValue<? extends Number> ov,
//                    Number old_val, Number new_val) -> {
//                        text.setText("jee" + new_val.toString());
//                    });
        
        Button toolButton = new Button("Työvälineet");
        toolButton.setOnAction(e -> {
            text.setText(service.displayTools());
        });
        
        Button officeButton = new Button("Toimipisteet");
        officeButton.setOnAction(e -> {
            text.setText(service.displayOffices());
        });
        
        Button quitButton = new Button("Lopeta");
        
        mainMenu.getChildren().add(toolOptions);
        mainMenu.getChildren().add(toolButton);
        mainMenu.getChildren().add(officeButton);
        mainMenu.getChildren().add(new Label(""));
        mainMenu.getChildren().add(new Label(""));
        mainMenu.getChildren().add(new Label(""));
        mainMenu.getChildren().add(new Label(""));
        mainMenu.getChildren().add(quitButton);
        
        BorderPane innerLayout = new BorderPane();
        innerLayout.setCenter(text);
        innerLayout.setTop(new Label("top"));
        innerLayout.setBottom(new Label("bottom"));
        innerLayout.setLeft(new Label("left"));
        innerLayout.setRight(new Label("right"));
        
        layout.setLeft(mainMenu);
        layout.setCenter(innerLayout);
        
        Scene scene = new Scene(layout, 400, 250);
        
        window.setScene(scene);
        window.show();
    }
    
    public static void main(String[] args) {
        launch(InventoryUI.class);
    }

}
