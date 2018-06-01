package catering.controllers;

import catering.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class LocationInfoController {
    private Stage primaryStage = Main.stage;
    private ToggleGroup group = new ToggleGroup();

    @FXML
    RadioButton radioButton1, radioButton2, radioButton3;

    @FXML
    public void initialize() {
        radioButton1.setToggleGroup(group);
        radioButton2.setToggleGroup(group);
        radioButton3.setToggleGroup(group);
    }
    //TODO: Disable/Show effect on unavailable locations based on user's guest count.
    @FXML
    public void handleNextButton2() throws IOException {
        Parent root = FXMLLoader.load(getClass()
                .getResource("/catering/views/menuInfoScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    @FXML
    public void handleBackButton() throws IOException {
        Parent root = FXMLLoader.load(getClass()
                .getResource("/catering/views/eventInfoScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }
}
