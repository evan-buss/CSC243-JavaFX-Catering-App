
package catering.controllers;

import catering.Main;
import catering.models.Customer;
import javafx.css.Style;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerInfoController {
    private Stage primaryStage = Main.stage;
    private Customer customer = Main.customer;
    Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);

    @FXML
    TextField nameTF;
    @FXML
    TextField phoneTF;
    @FXML
    TextField emailTF;
    @FXML
    public void initialize() {
    }

    @FXML
    public void handleMenuButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().
                getResource("/catering/views/titleScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    @FXML
    public void handleEventButton() throws IOException {
        if (!validateFields()) {
            return;
        }
        Parent root = FXMLLoader.load(getClass().
                getResource("/catering/views/eventInfoScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    private boolean validateFields() {
        if (nameTF.getText().equals("")) {
            alert.setContentText("Please enter your name!");
            alert.show();
            return false;
        } else {
            customer.setName(nameTF.getText());
        }
        if (phoneTF.getText().equals("")) {
            alert.setContentText("Please enter your phone number!");
            alert.show();
            return false;
        } else {
            customer.setPhoneNumber(phoneTF.getText());
        }
        if (emailTF.getText().equals("")) {
            alert.setContentText("Please enter your email address!");
            alert.show();
            return false;
        } else {
            customer.setEmail(emailTF.getText());
        }
        return true;
    }
}
