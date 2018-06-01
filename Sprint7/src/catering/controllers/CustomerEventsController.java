package catering.controllers;

import catering.Main;
import catering.models.Customer;
import catering.models.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerEventsController {

    @FXML
    TextArea textBox;
    @FXML
    Label titleLabel;
    private Stage primaryStage = Main.stage;
    private Customer customer = Main.customer;

    @FXML
    public void initialize() {
        titleLabel.setText(customer.getName() + " - Events");
        customer.displayDetails(textBox);
        for (Event e : customer.getEventsArray()) {
            e.displayDetails(textBox);
        }
    }

    @FXML
    public void handleEventButton() throws IOException {
        Parent root = FXMLLoader.load(getClass()
                .getResource("/catering/views/eventInfoScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    @FXML
    public void handleMenuButton() throws IOException {
        Parent root = FXMLLoader.load(getClass()
                .getResource("/catering/views/titleScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }
}
