package catering.controllers;

import catering.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuInfoController {
    private Stage primaryStage = Main.stage;
    //TODO Maybe animate opacity of labels when user changes menu type
    @FXML
    ChoiceBox<String> menuChoiceBox;
    @FXML
    private GridPane gridPane;
    @FXML
    private VBox vbox1, vbox2, vbox3;
    @FXML
    private Label label1, label2, label3;

    private String[] menuCategories = {"Dinner", "Brunch", "Social"};

    public void initialize() {
        //Add listener to choice box
        menuChoiceBox.getSelectionModel()
                .selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
            switch ((Integer) newValue) {
                case 0:
                    System.out.println(menuCategories[0]);
                    showDinnerOptions();
                    break;
                case 1:
                    System.out.println(menuCategories[1]);
                    showBrunchOptions();
                    break;
                case 2:
                    System.out.println(menuCategories[2]);
                    showSocialOptions();
                    break;
            }
        });
        //set choice box options default at dinner
        menuChoiceBox.setValue("Dinner");
        menuChoiceBox.getItems().addAll(menuCategories);
    }

    private void showDinnerOptions() {
        vbox1.getChildren().clear();
        vbox2.getChildren().clear();
        vbox3.getChildren().clear();
        setLabel(1);
        List<CheckBox> checkBoxes1 = new ArrayList<>();
        String[] labels1 = {"array1", "array1", "array1", "array1"};
        checkBoxArrayBuilder(vbox1, checkBoxes1, labels1);

        List<CheckBox> checkBoxes2 = new ArrayList<>();
        String[] labels2 = {"array2", "array2", "array2", "array2"};
        checkBoxArrayBuilder(vbox2, checkBoxes2, labels2);

        List<CheckBox> checkBoxes3 = new ArrayList<>();
        String[] labels3 = {"array3", "array3", "array3", "array3"};
        checkBoxArrayBuilder(vbox3, checkBoxes3, labels3);
    }

    public void showBrunchOptions() {
        vbox1.getChildren().clear();
        vbox2.getChildren().clear();
        vbox3.getChildren().clear();
        setLabel(2);
        List<CheckBox> checkBoxes1 = new ArrayList<>();
        String[] labels1 = {"brunch1", "brunch1", "brunch1", "brunch1"};
        checkBoxArrayBuilder(vbox1, checkBoxes1, labels1);

        List<CheckBox> checkBoxes2 = new ArrayList<>();
        String[] labels2 = {"brunch2", "brunch2", "brunch2", "brunch2"};
        checkBoxArrayBuilder(vbox2, checkBoxes2, labels2);
    }

    public void showSocialOptions() {
        vbox1.getChildren().clear();
        vbox2.getChildren().clear();
        vbox3.getChildren().clear();
        setLabel(3);
        List<CheckBox> checkBoxes1 = new ArrayList<>();
        String[] labels1 = {"social1", "social1", "social1", "social1"};
        checkBoxArrayBuilder(vbox1, checkBoxes1, labels1);

    }

    private void setLabel(int menuCategory) {
        switch (menuCategory) {
            case 1:
                label1.setText("Entrees: ");
                label2.setText("Sides: ");
                label3.setText("Desserts: ");
                break;
            case 2:
                label1.setText("Entrees: ");
                label2.setText("Meats: ");
                label3.setText("");
                break;
            case 3:
                label1.setText("Hors d'oeuvre: ");
                label2.setText("");
                label3.setText("");
                break;
        }
    }

    private void checkBoxArrayBuilder(VBox targetVBox, List<CheckBox> checkBoxList, String[] labels) {
        for (String label : labels) {
            checkBoxList.add(new CheckBox(label));
        }
        targetVBox.getChildren().addAll(checkBoxList);
    }

    @FXML
    public void handleBackButton() throws IOException {
        Parent root = FXMLLoader.load(getClass()
                .getResource("/catering/views/locationInfoScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    @FXML
    public void handleDoneButton() throws IOException {
        Parent root = FXMLLoader.load(getClass()
                .getResource("/catering/views/customerEventsScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }
}

