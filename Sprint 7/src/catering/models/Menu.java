/*
    This class holds all information about the event menus.
    The food choice master array, the limits for each type of menu, and the customer's
    choices stored in an array.
 */

package catering.models;

import javafx.scene.control.TextArea;

import java.io.PrintStream;
import java.util.Scanner;

public class Menu extends Customer {
    private int menuCategory;

    // Stores all possible food options
    private String[] dinnerEntreeOptions = {"Chicken", "Fish", "Beef",
            "Pork", "Pasta", "Vegan"};
    private String[] dinnerSideOptions = {"Soup", "Salad", "Green Beans",
            "Baked Potato", "Pasta", "Applesauce"};
    private String[] dinnerDessertOptions = {"Chocolate Mousse", "Cheesecake", "Sherbert",
            "Ice Cream", "Lava Cake", "Carrot Cake"};

    private String[] brunchEntreeOptions = {"Pancakes", "French Toast",
            "Eggs Benedict with Ham", "Scrambled Eggs"};
    private String[] brunchMeatOptions = {"Bacon", "Sausage", "Ham", "Pork Roll"};

    private String[] buffetOptions = {"Pigs in a Blanket", "Smoked Salmon",
            "Shrimp Cocktail", "Veggie Wraps", "Stuffed Mushrooms", "Brie and Crackers"};

    //Only initialize the ones that are used.
    private String[] customerEntreeChoices;
    private String[] customerSideChoices;
    private String[] customerDessertChoices;
    private String[] customerMeatChoices;
    private String[] customerFancyChoices;

    public Menu(int category) {
        this.menuCategory = category;
        setLimits();
    }

    public int getMenuCategory() {
        return menuCategory;
    }

    // 1="Dinner" 2="Brunch" 3="Buffet"
    public void setLimits() {
        switch (menuCategory) {
            case 1:
                customerEntreeChoices = new String[3];
                customerSideChoices = new String[2];
                customerDessertChoices = new String[3];
                break;
            case 2:
                customerEntreeChoices = new String[2];
                customerMeatChoices = new String[2];
                break;
            case 3:
                customerFancyChoices = new String[6];
                break;
            default:
                System.err.println("Set menu limit error. This should never happen.");
        }
    }

    /**
     * Takes each String of user selected menu choices and sends them to the database to
     * saved.
     *
     * @param eventID The id of the event that the menu items belong to
     */
    public void menuToDB(int eventID) {
        switch (menuCategory) {
            case 1:
                arrayToDB(eventID, customerEntreeChoices, "Entree");
                arrayToDB(eventID, customerSideChoices, "Side");
                arrayToDB(eventID, customerDessertChoices, "Dessert");
                break;
            case 2:
                arrayToDB(eventID, customerEntreeChoices, "Entree");
                arrayToDB(eventID, customerMeatChoices, "Meat");
                break;
            case 3:
                arrayToDB(eventID, customerFancyChoices, "Buffet");
                break;
            default:
                System.err.println("Menu to DB menuCategory error.");
        }
    }

    /**
     * Takes an array of strings and sends each string to the MenuItems database
     *
     * @param eventID      The event that the menu items belong to
     * @param array        Array of strings that contains individual menu items
     * @param itemCategory String that contains the items menu classification (Entree,Side,etc)
     */
    private void arrayToDB(int eventID, String[] array, String itemCategory) {
        for (String item : array) {
            if (item != null) {
                String sql = "INSERT INTO MenuItems (EventID, ItemCategory, Item) VALUES ('" +
                        eventID +
                        "', '" +
                        itemCategory +
                        "', '" +
                        item +
                        "');";
                DBConn.query(sql);
            }
        }
    }

    //interactive Menu
    public void setupMenu(Scanner keyboard) {

        switch (menuCategory) {
            case 1:
                System.out.println("\nYou will now choose the items for your dinner menu!\n");

                System.out.println("First, choose a maximum of " + customerEntreeChoices.length +
                        (customerEntreeChoices.length == 1 ? " entree..." : " entrees..."));
                enterChoices(dinnerEntreeOptions, customerEntreeChoices, keyboard);

                System.out.println("\nNext, choose a maximum of " + customerSideChoices.length +
                        (customerSideChoices.length == 1 ? " side..." : " sides..."));
                enterChoices(dinnerSideOptions, customerSideChoices, keyboard);

                System.out.println("\nLastly, choose a maximum of " + customerDessertChoices.length +
                        (customerDessertChoices.length == 1 ? " desert..." : " desserts..."));
                enterChoices(dinnerDessertOptions, customerDessertChoices, keyboard);
                break;
            case 2:
                System.out.println("\nYou will now choose the items for your brunch menu!\n");

                System.out.println("First, choose a maximum of " + customerEntreeChoices.length +
                        (customerEntreeChoices.length == 1 ? " entree..." : " entrees..."));
                enterChoices(brunchEntreeOptions, customerEntreeChoices, keyboard);

                //if they selected eggs benedict, only allow 1 other meat choice
                if (customerEntreeChoices[0].equals(brunchEntreeOptions[2]) ||
                        customerEntreeChoices[1].equals(brunchEntreeOptions[2])) {
                    customerMeatChoices = new String[1];
                }
                System.out.println(customerMeatChoices.length);

                System.out.println("Finally, choose a maximum of " + customerMeatChoices.length +
                        (customerMeatChoices.length == 1 ? " meat..." : " meats..."));
                enterChoices(brunchMeatOptions, customerMeatChoices, keyboard);
                break;
            case 3:
                System.out.println("\nYou will now choose the items for your buffet menu!\n");

                System.out.println("Choose a maximum of " + customerFancyChoices.length +
                        (customerFancyChoices.length == 1 ? " hors d'oeuvres..." : " hors d'oeuvres..."));
                enterChoices(buffetOptions, customerFancyChoices, keyboard);
                break;
            default:
                break;
        }
    }

    public void enterChoices(String[] optionList, String[] choiceList, Scanner keyboard) {
        System.out.println("Your options are: ");
        displayArray(optionList, true, System.out);
        int totalChoices = 0;
        while (choiceList.length > totalChoices) {
            int input = keyboard.nextInt();
            if (input == 0) {
                totalChoices = choiceList.length;
            } else {
                try {
                    choiceList[totalChoices] = optionList[input - 1];
                    totalChoices++;
                    System.out.println("...OK");
                } catch (Exception ex) {
                    System.err.println("Not a valid input...");
                }
            }
            keyboard.nextLine();
        }
    }

    public void displayArray(String[] array, boolean numbered, PrintStream printStream) {
        if (numbered) {
            for (int i = 0; i < array.length; i++) {
                if (i == 0) {
                    System.out.println("\t0) Done choosing items.");
                }
                printStream.println("\t" + (i + 1) + ") " + array[i]);
            }
        } else {
            String output = "";
            for (int i = 0; i < array.length; i++) {
                //if the user didn't want all item slots
                if (array[i] == null) {
                    output = output.substring(0, output.lastIndexOf(","));
                    break;
                } else {
                    output = output + array[i] + (i == array.length - 1 ? "" : ", ");
                }
            }
            printStream.print(output);
        }
    }

    public String displayArray(String[] array) {
        String output = "";
        int i = 0;
        for (String item : array) {
            if (item != null) {
                output += item + (i == array.length - 1 ? "" : ", ");
                i++;
            } else {
                output = output.substring(0, output.lastIndexOf(","));
                break;
            }
        }
        return output;
    }

    public void displayDetails(TextArea textBox) {
        String textString = textBox.getText() +
                "\nMenu Choices: ";
        switch (menuCategory) {
            case 1:
                textString += "\n\tEntrees: " + displayArray(customerEntreeChoices);
                textString += "\n\tSides: " + displayArray(customerSideChoices);
                textString += "\n\tDesserts: " + displayArray(customerDessertChoices);
                break;
            case 2:
                textString += "\n\tEntrees: " + displayArray(customerEntreeChoices);
                textString += "\n\tMeats: " + displayArray(customerMeatChoices);
                break;
            case 3:
                textString += "\n\tBuffet: " + displayArray(customerFancyChoices);
                break;
        }
        textString += "\n================================================";
        textBox.setText(textString);
    }

    public void displayDetails(PrintStream printStream) {
        System.out.println(customerFancyChoices.toString());
        printStream.println(" Menu Choices:");
        switch (menuCategory) {
            case 1:
                printStream.print("\tEntrees: ");
                displayArray(customerEntreeChoices, false, printStream);
                printStream.print("\n\tSides: ");
                displayArray(customerSideChoices, false, printStream);
                printStream.print("\n\tDesserts: ");
                displayArray(customerDessertChoices, false, printStream);
                break;
            case 2:
                printStream.print("\tEntrees: ");
                displayArray(customerEntreeChoices, false, printStream);
                printStream.print("\n\tMeat: ");
                displayArray(customerMeatChoices, false, printStream);
                break;
            case 3:
                printStream.print("\tHors d'Oeuvres: " +
                        "\n\t\t");
                displayArray(customerFancyChoices, false, printStream);
                break;
        }
        printStream.println("\n==================================================");
    }

    public String[] getDinnerEntreeOptions() {
        return dinnerEntreeOptions;
    }

    public String[] getDinnerSideOptions() {
        return dinnerSideOptions;
    }

    public String[] getDinnerDessertOptions() {
        return dinnerDessertOptions;
    }

    public String[] getBrunchEntreeOptions() {
        return brunchEntreeOptions;
    }

    public String[] getBrunchMeatOptions() {
        return brunchMeatOptions;
    }

    public String[] getBuffetOptions() {
        return buffetOptions;
    }

    public String[] getCustomerEntreeChoices() {
        return customerEntreeChoices;
    }

    public void setCustomerEntreeChoices(String[] customerEntreeChoices) {
        this.customerEntreeChoices = customerEntreeChoices;
    }

    public String[] getCustomerSideChoices() {
        return customerSideChoices;
    }

    public void setCustomerSideChoices(String[] customerSideChoices) {
        this.customerSideChoices = customerSideChoices;
    }

    public String[] getCustomerDessertChoices() {
        return customerDessertChoices;
    }

    public void setCustomerDessertChoices(String[] customerDessertChoices) {
        this.customerDessertChoices = customerDessertChoices;
    }

    public String[] getCustomerMeatChoices() {
        return customerMeatChoices;
    }

    public void setCustomerMeatChoices(String[] customerMeatChoices) {
        this.customerMeatChoices = customerMeatChoices;
    }

    public String[] getCustomerFancyChoices() {
        return customerFancyChoices;
    }

    public void setCustomerFancyChoices(String[] customerFancyChoices) {
        this.customerFancyChoices = customerFancyChoices;
    }
}