/*
    This class controls the menuInfo scene, at the end it writes all of the event and menu
    information to a database file.
 */

package catering.controllers;

import catering.Main;
import catering.models.Customer;
import catering.models.Event;
import catering.models.Menu;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuInfoController {
    @FXML
    ChoiceBox<String> menuChoiceBox;
    private Stage primaryStage = Main.stage;
    private Customer customer = Main.customer;
    private String[] menuCategories = {"Dinner", "Brunch", "Buffet"};
    private Event event = customer.getLatestEvent();
    private Menu menu;

    private List<JFXCheckBox> unselected1 = new ArrayList<>();
    private List<JFXCheckBox> unselected2 = new ArrayList<>();
    private List<JFXCheckBox> unselected3 = new ArrayList<>();
    private List<JFXCheckBox> selected1 = new ArrayList<>();
    private List<JFXCheckBox> selected2 = new ArrayList<>();
    private List<JFXCheckBox> selected3 = new ArrayList<>();

    @FXML
    private VBox vbox1, vbox2, vbox3;
    @FXML
    private Label label1, label2, label3;

    /**
     * Sets up the Menu Category choice box. Adds an event listener to listen for when
     * the user switches the menu category. Then calls the appropriate functions for each
     * category.
     */
    @FXML
    public void initialize() {
        //set choice box options default at dinner
        menuChoiceBox.getItems().addAll(menuCategories);
        //Add listener to choice box
        menuChoiceBox.getSelectionModel()
                .selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    switch ((Integer) newValue) {
                        case 0:
                            event.createMenu(1);
                            menu = event.getEventMenu();
                            showDinnerOptions();
                            break;
                        case 1:
                            event.createMenu(2);
                            menu = event.getEventMenu();
                            showBrunchOptions();
                            break;
                        case 2:
                            event.createMenu(3);
                            menu = event.getEventMenu();
                            showBuffetOptions();
                            break;
                    }
                });
        menuChoiceBox.setValue("Dinner");
    }

    /**
     * Creates new field of checkboxes with proper labels and values for Dinner
     */
    private void showDinnerOptions() {
        clearVboxes();
        setLabels(1);
        List<JFXCheckBox> JFXCheckBoxes1 = new ArrayList<>();
        String[] entreeOptions = menu.getDinnerEntreeOptions();
        JFXCheckBoxArrayBuilder(vbox1, JFXCheckBoxes1, entreeOptions,
                menu.getCustomerEntreeChoices().length, selected1, unselected1);

        List<JFXCheckBox> JFXCheckBoxes2 = new ArrayList<>();
        String[] sideOptions = menu.getDinnerSideOptions();
        JFXCheckBoxArrayBuilder(vbox2, JFXCheckBoxes2, sideOptions,
                menu.getCustomerSideChoices().length, selected2, unselected2);

        List<JFXCheckBox> JFXCheckBoxes3 = new ArrayList<>();
        String[] dessertOptions = menu.getDinnerDessertOptions();
        JFXCheckBoxArrayBuilder(vbox3, JFXCheckBoxes3, dessertOptions,
                menu.getCustomerDessertChoices().length, selected3, unselected3);
    }

    /**
     * Creates new field of checkboxes with proper labels and values for Brunch
     */
    private void showBrunchOptions() {
        clearVboxes();
        setLabels(2);
        List<JFXCheckBox> JFXCheckBoxes1 = new ArrayList<>();
        String[] entreeOptions = menu.getBrunchEntreeOptions();
        JFXCheckBoxArrayBuilder(vbox1, JFXCheckBoxes1, entreeOptions, menu.getCustomerEntreeChoices().length,
                selected1, unselected1);

        List<JFXCheckBox> JFXCheckBoxes2 = new ArrayList<>();
        String[] meatOptions = menu.getBrunchMeatOptions();
        JFXCheckBoxArrayBuilder(vbox2, JFXCheckBoxes2, meatOptions, menu.getCustomerMeatChoices().length,
                selected2, unselected2);
    }

    /**
     * Creates new field of checkboxes with proper labels and values for Buffet
     */
    private void showBuffetOptions() {
        clearVboxes();
        setLabels(3);
        List<JFXCheckBox> JFXCheckBoxes1 = new ArrayList<>();
        String[] fancyOptions = menu.getBuffetOptions();
        JFXCheckBoxArrayBuilder(vbox1, JFXCheckBoxes1, fancyOptions, menu.getCustomerFancyChoices().length,
                selected1, unselected1);
    }

    /**
     * Clears each VBox of all nodes
     */
    private void clearVboxes() {
        vbox1.getChildren().clear();
        vbox2.getChildren().clear();
        vbox3.getChildren().clear();
    }

    /**
     * Depending on the type of menu, it changes the text of the label to correspond
     *
     * @param menuCategory Integer that represents menuCategory
     */
    private void setLabels(int menuCategory) {
        switch (menuCategory) {
            case 1:
                label1.setText("Entrees: ");
                label2.setText("Sides: ");
                label3.setText("Desserts: ");
                break;
            case 2:
                label1.setText("Entrees: ");
                label2.setText("Meats: ");
                label3.setText("");
                break;
            case 3:
                label1.setText("Buffet Items: ");
                label2.setText("");
                label3.setText("");
                break;
        }
    }

    /**
     * Builds an array of checkboxes that each have an event listener and a maximum number
     * of available boxes to be checked at a time
     *
     * @param targetVBox      VBox that the checkboxes should be put into
     * @param JFXCheckBoxList Empty ArrayList of checkboxes
     * @param labels          String array of labels that correspond with each checkbox
     * @param maxSel          Maximum number of checkboxes to be checked at a time
     * @param selected        List of all of the currently checked checkboxes
     * @param unselected      List of all of the currently unchecked checkboxes
     */
    private void JFXCheckBoxArrayBuilder(VBox targetVBox, List<JFXCheckBox> JFXCheckBoxList,
                                         String[] labels, int maxSel, List<JFXCheckBox> selected,
                                         List<JFXCheckBox> unselected) {
        //populates new ArrayList of JFXCheckBoxes with labels and set up their listeners
        selected.clear();
        unselected.clear();
        for (String label : labels) {
            JFXCheckBox JFXCheckBox = new JFXCheckBox(label);
            setJFXCheckBoxListener(JFXCheckBox, maxSel, selected, unselected);
            JFXCheckBoxList.add(JFXCheckBox);
        }
        //default all JFXCheckBoxes into the unselected list
        unselected.addAll(JFXCheckBoxList);
        targetVBox.getChildren().addAll(JFXCheckBoxList);
    }

    /**
     * Sets a listener on a checkbox that listens for when the maximum number of allowed boxes
     * is checked at the same time.
     *
     * @param JFXCheckBox Checkbox to add a listener to
     * @param maxSel      Max number of boxes allowed to be checked at once
     * @param selected    List of selected checkboxes
     * @param unselected  List of unselected checkboxes
     */
    public void setJFXCheckBoxListener(JFXCheckBox JFXCheckBox, int maxSel,
                                       List<JFXCheckBox> selected, List<JFXCheckBox> unselected) {
        JFXCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (JFXCheckBox.isSelected()) {
                selected.add(JFXCheckBox);
                unselected.remove(JFXCheckBox);
            } else if (!JFXCheckBox.isSelected()) {
                unselected.add(JFXCheckBox);
                selected.remove(JFXCheckBox);
            }
            if (selected.size() >= maxSel) {
                for (JFXCheckBox cb : unselected) {
                    cb.setDisable(true);
                }
            } else {
                for (JFXCheckBox cb : unselected) {
                    cb.setDisable(false);
                }
            }
        });
    }

    /**
     * Called to get the values of all of the currently selected checkboxes, adds
     * these values to String arrays
     */
    public void getValues() {
        int category = menu.getMenuCategory();
        switch (category) {
            case 1:
                String[] entreeChoices = new String[menu.getCustomerEntreeChoices().length];
                setCustomerChoices(selected1, entreeChoices);
                menu.setCustomerEntreeChoices(entreeChoices);

                String[] sideChoices = new String[menu.getCustomerSideChoices().length];
                setCustomerChoices(selected2, sideChoices);
                menu.setCustomerSideChoices(sideChoices);

                String[] dessertChoices = new String[menu.getCustomerDessertChoices().length];
                setCustomerChoices(selected3, dessertChoices);
                menu.setCustomerDessertChoices(dessertChoices);
                break;
            case 2:
                String[] brunchEntreeChoices = new String[menu.getCustomerEntreeChoices().length];
                setCustomerChoices(selected1, brunchEntreeChoices);
                menu.setCustomerEntreeChoices(brunchEntreeChoices);

                String[] brunchMeatChoices = new String[menu.getCustomerMeatChoices().length];
                setCustomerChoices(selected2, brunchMeatChoices);
                menu.setCustomerMeatChoices(brunchMeatChoices);
                break;
            case 3:
                String[] fancyChoices = new String[menu.getCustomerFancyChoices().length];
                setCustomerChoices(selected1, fancyChoices);
                menu.setCustomerFancyChoices(fancyChoices);
                break;
            default:
                System.out.println("Error");
        }
    }

    /**
     * Takes a List of checkboxes, finds the value of the checkbox, and puts it into
     * a String array
     *
     * @param selected List of selected checkboxes
     * @param choices  String array of the values parsed from the checkboxes
     */
    private void setCustomerChoices(List<JFXCheckBox> selected, String[] choices) {
        for (int i = 0; i < selected.size(); i++) {
            if (selected.get(i).isSelected()) {
                choices[i] = selected.get(i).getText();
            }
        }
    }

    /**
     * Goes back the previous scene which is the location info scene
     *
     * @throws IOException
     */
    @FXML
    public void handleBackButton() throws IOException {
        Parent root = FXMLLoader.load(getClass()
                .getResource("/catering/views/locationInfoScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    /**
     * Finalizes all of the users event and menu choices, writes the event to the Events
     * database,then writes the menu items to the MenuItems database
     *
     * @throws IOException
     */
    @FXML
    public void handleDoneButton() throws IOException {

        getValues();
        event.eventToDB(customer.getCustomerID());
        menu.menuToDB(event.getEventID());

        System.out.println("EventID: " + event.getEventID());
        Parent root = FXMLLoader.load(getClass()
                .getResource("/catering/views/customerEventsScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }
}

