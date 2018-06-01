package catering.controllers;

import catering.Main;
import catering.models.Customer;
import catering.models.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TitleController {
    private Stage stage = Main.stage;

    @FXML
    public void initialize() {

    }

    @FXML
    public void handleCustButton() throws IOException {
        Parent root = FXMLLoader.load(getClass()
                .getResource("/catering/views/customerInfoScene.fxml"));
        stage.setScene(new Scene(root));
    }
    @FXML
    public void handleCalendarButton() throws IOException {
        Parent root = FXMLLoader.load(getClass()
                .getResource("/catering/views/allEventsScene.fxml"));
        stage.setScene(new Scene(root));
    }
    @FXML
    public void handleInfoButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().
                getResource("/catering/views/informationScene.fxml"));
        stage.setScene(new Scene(root));
    }
}
