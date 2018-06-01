/*
  Controls the function of the user's event info. Namely the date and event type
 */
package catering.controllers;

import catering.Main;
import catering.models.Customer;
import catering.models.Event;
import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class EventInfoController {
    @FXML
    DatePicker datePicker;
    @FXML
    RadioButton weddingRB;
    @FXML
    RadioButton birthdayRB;
    @FXML
    RadioButton corporateRB;
    private Stage primaryStage = Main.stage;
    private Customer customer = Main.customer;
    private Event event;
    private ToggleGroup group = new ToggleGroup();
//    private DropShadow errorGlow = new DropShadow();
//    private DropShadow correctGlow = new DropShadow();

    /**
     * Groups RadioButtons, creates a new event, and sets a default RadioButton
     */
    @FXML
    public void initialize() {
//        errorGlow.setColor(Color.RED);
//        correctGlow.setColor(Color.GREEN);

        event = customer.addEvent();
        weddingRB.setSelected(true);
        weddingRB.setToggleGroup(group);
        birthdayRB.setToggleGroup(group);
        corporateRB.setToggleGroup(group);

//        datePicker.focusedProperty().addListener((observable, oldValue, newValue) ->{
//            if (!newValue) {
//                if (datePicker.getValue() == null) {
////                    datePicker.setEffect(errorGlow);
//                    datePicker.setStyle("-fx-border-color: red");
//                } else if (datePicker.getValue().compareTo(LocalDate.now())<0) {
////                    datePicker.setEffect(errorGlow);
//                    datePicker.setStyle("-fx-border-color: red");
//                } else {
////                    datePicker.setEffect(correctGlow);
//                    datePicker.setStyle("-fx-border-color: green");
//                }
//            }
//        });

    }

    /**
     * Checks to make sure that entered event information is valid, displays
     * an alert if something is not valid. If valid, updates event class info
     * and opens the locationInfo scene.
     *
     * @throws IOException
     */
    @FXML
    public void handleNextButton() throws IOException {

        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);

        event.setEventCategory(Integer.parseInt(group.getSelectedToggle().getUserData().toString()));
        if (datePicker.getValue() != null) {
            if (datePicker.getValue().compareTo(LocalDate.now()) > 0) {
                event.setEventDate(datePicker.getValue());
            } else {
                alert.setContentText("Event date cannot be in the past...");
                alert.show();
                return;
            }
        } else {
            alert.setContentText("No date chosen!");
            alert.show();
            return;
        }

        Parent root = FXMLLoader.load(getClass().
                getResource("/catering/views/locationInfoScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    /**
     * Back button returns to customerInfoScene, the previous scene.
     *
     * @throws IOException
     */
    @FXML
    public void handleBackButton() throws IOException {
        customer.removeEvent(customer.getEventCounter() - 1);
        Parent root = FXMLLoader.load(getClass().
                getResource("/catering/views/customerInfoScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }
}
