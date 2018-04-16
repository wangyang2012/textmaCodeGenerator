package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class Main extends Application {

    private TextArea textAreaSortInfo;
    private TextArea textAreaGridDef;
    private TextArea textAreaSideMenu;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Tabs");
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);

        TabPane tabPane = new TabPane();

        BorderPane borderPane = new BorderPane();

        // TOP
        // Textarea
        GridPane top = new GridPane();
        TextArea src = new TextArea();
        HBox.setHgrow(src, Priority.ALWAYS);
        src.setMaxWidth(Double.MAX_VALUE);
        top.add(src, 0, 0);

        // Button
        Button btn = new Button("valider");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        top.add(hbBtn, 1, 0);

        // CENTER
        // Tab - SortInfo
        Tab tabSortInfo = new Tab();
        tabSortInfo.setText("Server side - SortInfo");
        HBox hboxSortInfo = new HBox();
        textAreaSortInfo = new TextArea();
        hboxSortInfo.getChildren().add(textAreaSortInfo);
        HBox.setHgrow(textAreaSortInfo, Priority.ALWAYS);
        tabSortInfo.setContent(hboxSortInfo);
        tabPane.getTabs().add(tabSortInfo);

        // Tab - Grid definition
        Tab tabGridDef= new Tab();
        tabGridDef.setText("Front side - Grid columns");
        HBox hboxGridDef = new HBox();
        textAreaGridDef = new TextArea();
        hboxGridDef.getChildren().add(textAreaGridDef);
        HBox.setHgrow(textAreaGridDef, Priority.ALWAYS);
        tabGridDef.setContent(hboxGridDef);
        tabPane.getTabs().add(tabGridDef);

        // Tab - Side menu
        Tab tabSideMenu= new Tab();
        tabSideMenu.setText("Front side - Side menu");
        HBox hboxSideMenu = new HBox();
        textAreaSideMenu = new TextArea();
        hboxSideMenu.getChildren().add(textAreaSideMenu);
        HBox.setHgrow(textAreaSideMenu, Priority.ALWAYS);
        tabSideMenu.setContent(hboxSideMenu);
        tabPane.getTabs().add(tabSideMenu);

        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);

        root.getChildren().add(borderPane);
        primaryStage.setScene(scene);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String sortInfo = GenerateService.generateSortInfo(src.getText());
                textAreaSortInfo.setText(sortInfo);

                String sideMenu = GenerateService.generateSideMenu(src.getText());
                textAreaSideMenu.setText(sideMenu);

                String gridDef = GenerateService.generateGridDef(src.getText());
                textAreaGridDef.setText(gridDef);
            }
        });
        borderPane.setTop(top);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        }
}