package catering.controllers;

import catering.Main;
import catering.models.Customer;
import catering.models.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class CalendarEventsController {
    //TODO: will have option to export to file and it will load all the found
    // events in from the file
    private Stage primaryStage = Main.stage;
    private Customer customer = Main.customer;

    @FXML
    TextArea textBox;

    @FXML
    public void initialize() {
        for (Event e : customer.getEventsArray()) {
            e.displayDetails(textBox);
        }
    }


    @FXML
    public void handleEventButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().
                getResource("/catering/views/eventInfoScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    @FXML
    public void handleMenuButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().
                getResource("/catering/views/titleScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }
}
