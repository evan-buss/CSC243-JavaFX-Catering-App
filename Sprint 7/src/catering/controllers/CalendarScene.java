package catering.controllers;

import catering.Main;
import catering.models.DBConn;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;

//FIXME: the connection doesn't seem to be closing properly, need to think of a
//better way to do it.
public class CalendarScene {
    @FXML
    GridPane gridPane;
    @FXML
    TabPane tabPane;
    private Stage primaryStage = Main.stage;

    @FXML
    public void initialize() {
        loopThroughCustomers();
    }

    private void loopThroughCustomers() {
        try {
            ResultSet rs = DBConn.getResultSet("SELECT CustomerID, Name, " +
                    "PhoneNumber, Email  FROM Customers;");


            while (rs.next()) {
                int CustomerID = rs.getInt("CustomerID");
                String name = rs.getString("Name");
                String phone = rs.getString("PhoneNumber");
                String email = rs.getString("Email");


                Tab tab = new Tab(name);
                JFXTextArea textArea = new JFXTextArea();
                textArea.setEditable(false);
                textArea.setPadding(new Insets(10, 10, 10, 10));

                String textString = textArea.getText() +
                        "Customer Details:" +
                        "\n================================================" +
                        "\n\tName: " + name +
                        "\n\tPhone Number: " + phone +
                        "\n\tEmail: " + email +
                        "\n================================================";
                textArea.setText(textString);

                tab.setContent(textArea);
                tabPane.getTabs().add(tab);
                loopThroughEvents(textArea, CustomerID);
            }
        } catch (Exception ex) {
            System.err.println("loop through customers");
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        } finally {
//            System.out.println("Closing customer rs");
            DBConn.closeAll();
        }
    }

    private void loopThroughEvents(JFXTextArea textArea, int CustomerID) {
        try {
            String query = "SELECT * FROM Events WHERE CustomerID='" + CustomerID + "';";
            ResultSet rs = DBConn.getResultSet(query);
            while (rs.next()) {

                int eventID = rs.getInt("EventID");
                int guestCount = rs.getInt("GuestCount");
                int totalCost = rs.getInt("TotalCost");
                String date = rs.getString("Date");
                String eventType = rs.getString("Type");
                String location = rs.getString("Location");
                String menuType = rs.getString("MenuType");

                String textString = textArea.getText() +
                        "\nEvent Details:" +
                        "\n\tID: " + eventID +
                        "\n\tDate: " + date +
                        "\n\tGuests: " + guestCount +
                        "\n\tEvent Type: " + eventType +
                        "\n\tMenu Type: " + menuType +
                        "\n\tLocation: " + location +
                        "\n\tTotal Price: $" + totalCost +
                        "\n================================================";
                textArea.setText(textString);
//                loopThroughMenu(textArea, eventID, menuType);
            }
        } catch (Exception ex) {
            System.err.println("loop through events");
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        } finally {
//            System.out.println("Closing event rs");
            DBConn.closeAll();
        }
    }

    private void loopThroughMenu(JFXTextArea textArea, int eventID, String menuType) {
//        System.out.println("made it to menu loop");
        try {
            String entrees = "Entrees: ";
            String sides = "Sides: ";
            String desserts = "Desserts: ";
            String meats = "Meats: ";
            String buffet = "Buffet Items: ";

            String query = "SELECT ItemCategory, Item FROM MenuItems " +
                    "WHERE EventID='" + eventID + "';";
            ResultSet rs = DBConn.getResultSet(query);
            while (rs.next()) {
                String itemCategory = rs.getString("ItemCategory");
                String item = rs.getString("Item");
                switch (itemCategory) {
                    case "Entree":
                        entrees += item + "  ";
                        break;
                    case "Side":
                        sides += item + "  ";
                        break;
                    case "Dessert":
                        desserts += item + "  ";
                        break;
                    case "Meat":
                        meats += item + "  ";
                        break;
                    case "Buffet":
                        buffet += item + "  ";
                        break;
                }
            }

            String textString = textArea.getText() + "\nMenu Details:";

            switch (menuType) {
                case "Dinner":
                    textString += "\n\t" + entrees +
                            "\n\t" + sides +
                            "\n\t" + desserts;
                    break;
                case "Brunch":
                    textString += "\n\t" + entrees +
                            "\n\t" + meats;
                    break;
                case "Buffet":
                    textString += "\n\t" + buffet;
                    break;
            }
            textString += "\n================================================";
            textArea.setText(textString);

        } catch (Exception ex) {
            System.err.println("loop through menu");
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    @FXML
    public void handleMenuButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().
                getResource("/catering/views/titleScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }
}
