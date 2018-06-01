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

public class EventInfoController {

    private Stage primaryStage = Main.stage;

    @FXML
    RadioButton weddingRB;
    @FXML
    RadioButton birthdayRB;
    @FXML
    RadioButton corporateRB;

    private ToggleGroup group = new ToggleGroup();

    @FXML
    public void initialize() {
        weddingRB.setToggleGroup(group);
        birthdayRB.setToggleGroup(group);
        corporateRB.setToggleGroup(group);
    }

    @FXML
    public void handleNextButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/catering/views/locationInfoScene.fxml"));
        primaryStage.setScene(new Scene(root));

        if (group.getSelectedToggle() == null) {
            System.out.println("You didn't choose anything.");
        } else {
            System.out.println(group.getSelectedToggle().toString());
        }
    }

    @FXML
    public void handleBackButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/catering/views/customerInfoScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }


}
