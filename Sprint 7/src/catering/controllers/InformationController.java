package catering.controllers;

import catering.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InformationController {

    private Stage primaryStage = Main.stage;

    @FXML
    public void handleMenuButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().
                getResource("/catering/views/titleScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }
}
