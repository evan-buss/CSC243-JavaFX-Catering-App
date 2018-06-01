package catering.controllers;

import catering.Main;
import catering.models.Customer;
import catering.models.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class EventInfoController {

    private Stage primaryStage = Main.stage;
    private Customer customer = Main.customer;
    private Event event;

    @FXML
    DatePicker datePicker;
    @FXML
    RadioButton weddingRB;
    @FXML
    RadioButton birthdayRB;
    @FXML
    RadioButton corporateRB;

    private ToggleGroup group = new ToggleGroup();

    @FXML
    public void initialize() {
        event = customer.addEvent();
        weddingRB.setSelected(true);
        weddingRB.setToggleGroup(group);
        birthdayRB.setToggleGroup(group);
        corporateRB.setToggleGroup(group);

    }

    @FXML
    public void handleNextButton() throws IOException {

        Alert alert = new Alert(Alert.AlertType.ERROR, "No date choosen!", ButtonType.OK);

        event.setEventCategory(Integer.parseInt(group.getSelectedToggle().getUserData().toString()));

        if (datePicker.getValue() != null) {
            event.setEventDate(datePicker.getValue());
        } else {
            alert.show();
            return;
        }

        Parent root = FXMLLoader.load(getClass().
                getResource("/catering/views/locationInfoScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    @FXML
    public void handleBackButton() throws IOException {
        customer.removeEvent(customer.getEventCounter()-1);
        Parent root = FXMLLoader.load(getClass().
                getResource("/catering/views/customerInfoScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }


}
