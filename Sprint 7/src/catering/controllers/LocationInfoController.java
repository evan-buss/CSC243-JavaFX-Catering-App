package catering.controllers;

import catering.Main;
import catering.models.Customer;
import catering.models.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class LocationInfoController {
    @FXML
    TextField numTF;
    @FXML
    RadioButton javaRB, shortyRB, stokesayRB;
    @FXML
    ImageView javaIMG, shortyIMG, stokesayIMG;
    private Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);

    private Stage primaryStage = Main.stage;
    private ToggleGroup group = new ToggleGroup();
    private Customer customer = Main.customer;
    private Event event = customer.getEvent(customer.getEventCounter() - 1);

    @FXML
    public void initialize() {
        alert.setHeaderText("Error!");

        javaRB.setToggleGroup(group);
        shortyRB.setToggleGroup(group);
        stokesayRB.setToggleGroup(group);

        //check the new value when the textfield loses focus
        numTF.textProperty().addListener((observable, oldValue, newValue) -> {

            if (validateCheck()) {
                numTF.setStyle("-jfx-unfocus-color: green; " +
                        "-jfx-focus-color: green;");
            } else {
                numTF.setStyle("-jfx-unfocus-color: red; " +
                        "-jfx-focus-color: red;");
            }
        });

        numTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // we only care about losing focus
                int numGuests = event.minGuests;
                try {
                    numGuests = (Integer.parseInt(numTF.getText()));
                } catch (Exception ex) {
                    System.out.println("Could not parse number");
                }
                if (numGuests >= event.maxStokesay) {
                    disable(true, true, true);
                } else if (numGuests > event.maxShortys) {
                    disable(true, true, false);
                } else if (numGuests > event.maxJavaJoint) {
                    disable(true, false, false);
                } else if (numGuests < event.minGuests) {
                    disable(true, true, true);
                } else {
                    disable(false, false, false);
                }
            }
        });
    }

    private boolean validateCheck() {
        String regex = "^\\d{0,9}$";
        return numTF.getText().matches(regex);
    }

    private void disable(boolean rb1, boolean rb2, boolean rb3) {
        javaRB.setDisable(rb1);
        shortyRB.setDisable(rb2);
        stokesayRB.setDisable(rb3);
        if (group.getSelectedToggle() != null) {
            group.getSelectedToggle().setSelected(false);
        }
        disableImage(rb1, javaIMG);
        disableImage(rb2, shortyIMG);
        disableImage(rb3, stokesayIMG);
    }

    public void disableImage(boolean bool, ImageView img) {
        ColorAdjust grayscale = new ColorAdjust();
        grayscale.setSaturation(-1);
        if (bool) {
            img.setEffect(grayscale);
        } else {
            img.setEffect(null);
        }
    }

    @FXML
    public void handleNextButton() throws IOException {
        //check to make sure that a radiobutton is selected
        if (numTF.getText().equals("")) {
            alert.setContentText("Number of guests not entered!");
            alert.show();
            return;
        } else if (validateCheck()) {
            event.setNumGuests(Integer.parseInt(numTF.getText()));
        } else {
            alert.setContentText("Invalid input!");
            alert.show();
            return;
        }

        //check to make sure the user entered an guest amount
        if (group.getSelectedToggle() == null) {
            alert.setContentText("No location selected!");
            alert.show();
            return;
        } else {
            event.setLocation(Integer.parseInt(group.getSelectedToggle()
                    .getUserData().toString()));
        }
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
