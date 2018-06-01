package catering.controllers;

import catering.Main;
import catering.models.Customer;
import catering.models.Event;
import catering.models.Menu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuInfoController {
    private Stage primaryStage = Main.stage;
    private Customer customer = Main.customer;
    private String[] menuCategories = {"Dinner", "Brunch", "Social"};
    private Event event = customer.getLatestEvent();
    private Menu menu;

    private List<CheckBox> unselected1 = new ArrayList<>();
    private List<CheckBox> unselected2 = new ArrayList<>();
    private List<CheckBox> unselected3 = new ArrayList<>();
    private List<CheckBox> selected1 = new ArrayList<>();
    private List<CheckBox> selected2 = new ArrayList<>();
    private List<CheckBox> selected3 = new ArrayList<>();

    @FXML
    ChoiceBox<String> menuChoiceBox;
    @FXML
    private VBox vbox1, vbox2, vbox3;
    @FXML
    private Label label1, label2, label3;

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
                            showSocialOptions();
                            break;
                    }
                });
        menuChoiceBox.setValue("Dinner");
    }

    private void showDinnerOptions() {
        clearVboxes();
        setLabels(1);
        List<CheckBox> checkBoxes1 = new ArrayList<>();
        String[] entreeOptions = menu.getDinnerEntreeOptions();
        checkBoxArrayBuilder(vbox1, checkBoxes1, entreeOptions,
                menu.getCustomerEntreeChoices().length, selected1, unselected1);

        List<CheckBox> checkBoxes2 = new ArrayList<>();
        String[] sideOptions = menu.getDinnerSideOptions();
        checkBoxArrayBuilder(vbox2, checkBoxes2, sideOptions,
                menu.getCustomerSideChoices().length, selected2, unselected2);

        List<CheckBox> checkBoxes3 = new ArrayList<>();
        String[] dessertOptions = menu.getDinnerDessertOptions();
        checkBoxArrayBuilder(vbox3, checkBoxes3, dessertOptions,
                menu.getCustomerDessertChoices().length, selected3, unselected3);
    }

    public void showBrunchOptions() {
        clearVboxes();
        setLabels(2);
        List<CheckBox> checkBoxes1 = new ArrayList<>();
        String[] entreeOptions = menu.getBrunchEntreeOptions();
        checkBoxArrayBuilder(vbox1, checkBoxes1, entreeOptions, menu.getCustomerEntreeChoices().length,
                selected1, unselected1);

        List<CheckBox> checkBoxes2 = new ArrayList<>();
        String[] meatOptions = menu.getBrunchMeatOptions();
        checkBoxArrayBuilder(vbox2, checkBoxes2, meatOptions, menu.getCustomerMeatChoices().length,
                selected2, unselected2);
    }

    public void showSocialOptions() {
        clearVboxes();
        setLabels(3);
        List<CheckBox> checkBoxes1 = new ArrayList<>();
        String[] fancyOptions = menu.getSocialFancyOptions();
        checkBoxArrayBuilder(vbox1, checkBoxes1, fancyOptions, menu.getCustomerFancyChoices().length,
                selected1, unselected1);
    }

    private void clearVboxes() {
        vbox1.getChildren().clear();
        vbox2.getChildren().clear();
        vbox3.getChildren().clear();
    }

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
                label1.setText("Hors d'oeuvre: ");
                label2.setText("");
                label3.setText("");
                break;
        }
    }

    private void checkBoxArrayBuilder(VBox targetVBox, List<CheckBox> checkBoxList,
                                      String[] labels, int maxSel, List<CheckBox> selected,
                                      List<CheckBox> unselected) {
        //populates new ArrayList of checkboxes with labels and set up their listeners
        selected.clear();
        unselected.clear();
//        System.out.println("Max: " + maxSel);
        for (String label : labels) {
            CheckBox checkBox = new CheckBox(label);
            setCheckBoxListener(checkBox, maxSel, selected, unselected);
            checkBoxList.add(checkBox);
        }
        //default all checkboxes into the unselected list
        unselected.addAll(checkBoxList);
        targetVBox.getChildren().addAll(checkBoxList);
    }

    public void setCheckBoxListener(CheckBox checkBox, int maxSel,
                                    List<CheckBox> selected, List<CheckBox> unselected) {
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (checkBox.isSelected()) {
                selected.add(checkBox);
                unselected.remove(checkBox);
            } else if (!checkBox.isSelected()) {
                unselected.add(checkBox);
                selected.remove(checkBox);
            }
            if (selected.size() >= maxSel) {
                for (CheckBox cb : unselected) {
                    cb.setDisable(true);
                }
            } else {
                for (CheckBox cb : unselected) {
                    cb.setDisable(false);
                }
            }
        });
    }

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

    private void setCustomerChoices(List<CheckBox> selected, String[] choices) {
        for (int i = 0; i < selected.size(); i++) {
            if (selected.get(i).isSelected()) {
                choices[i] = selected.get(i).getText();
            }
        }
    }

    @FXML
    public void handleBackButton() throws IOException {
        Parent root = FXMLLoader.load(getClass()
                .getResource("/catering/views/locationInfoScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    @FXML
    public void handleDoneButton() throws IOException {
        getValues();
        Parent root = FXMLLoader.load(getClass()
                .getResource("/catering/views/customerEventsScene.fxml"));
        primaryStage.setScene(new Scene(root));
    }
}

