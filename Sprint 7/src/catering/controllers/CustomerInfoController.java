package catering.controllers;

import catering.Main;
import catering.models.Customer;
import catering.models.DBConn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerInfoController {
    @FXML
    TextField nameTF;
    @FXML
    TextField phoneTF;
    @FXML
    TextField emailTF;
    private Stage primaryStage = Main.stage;
    private Customer customer = Main.customer;
    private Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);

    @FXML
    public void initialize() {

        String regex = "[A-Za-z ]*";
        addValidation(regex, nameTF);

        regex = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        addValidation(regex, phoneTF);

        regex = "\\S+@\\S+\\.\\S+";
        addValidation(regex, emailTF);
    }

    private void addValidation(String regex, TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (validateCheck(regex, textField)) {
                textField.setStyle("-jfx-unfocus-color: green; " +
                        "-jfx-focus-color: green;");
            } else {
                textField.setStyle("-jfx-unfocus-color: red;" +
                        "-jfx-focus-color: red;");
            }
        });
    }

    private boolean validateCheck(String regex, TextField textField) {
        return textField.getText().matches(regex);
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
        } else {
            customer.customerToDB();
            System.out.println("CustomerID: " + customer.getCustomerID());
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
        } else if (validateCheck("[A-Za-z ]*", nameTF)) {
            customer.setName(nameTF.getText());
        } else {
            alert.setContentText("Name text field error!");
            alert.show();
            return false;
        }

        if (phoneTF.getText().equals("")) {
            alert.setContentText("Please enter your phone number!");
            alert.show();
            return false;
        } else if (validateCheck("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", phoneTF)) {
            customer.setPhoneNumber(phoneTF.getText());
        } else {
            alert.setContentText("Phone number text field error!");
            alert.show();
            return false;
        }
        if (emailTF.getText().equals("")) {
            alert.setContentText("Please enter your email address!");
            alert.show();
            return false;
        } else if (validateCheck("\\S+@\\S+\\.\\S+", emailTF)) {
            customer.setEmail(emailTF.getText());
        } else {
            alert.setContentText("Phone number text field error!");
            alert.show();
            return false;
        }
        return true;
    }
}
