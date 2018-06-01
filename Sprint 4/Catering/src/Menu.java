/*
    This class holds all information about the event menus.
    The food choice master array, the limits for each type of menu, and the customer's
    choices stored in an array.
 */

import java.io.PrintStream;
import java.util.Scanner;

public class Menu {
    private int menuCategory;

    // Stores all possible food options
    private String[] dinnerEntreeOptions = {"Chicken", "Fish", "Beef",
            "Pork", "Pasta", "Vegan"};
    private String[] dinnerSideChoices = {"Soup", "Salad", "Green Beans",
            "Baked Potato", "Pasta", "Applesauce"};
    private String[] dinnerDessertChoices = {"Chocolate Mousse", "Cheesecake", "Sherbert",
            "Ice Cream", "Lava Cake", "Carrot Cake"};

    private String[] brunchEntreeOptions = {"Pancakes", "French Toast",
            "Eggs Benedict with Ham", "Scrambled Eggs"};
    private String[] brunchMeatOptions = {"Bacon", "Sausage", "Ham", "Pork Roll"};

    private String[] socialFancyOptions = {"Pigs in a Blanket", "Smoked Salmon",
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

    // 1="Dinner" 2="Brunch" 3="Social"
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
                enterChoices(dinnerSideChoices, customerSideChoices, keyboard);

                System.out.println("\nLastly, choose a maximum of " + customerDessertChoices.length +
                        (customerDessertChoices.length == 1 ? " desert..." : " desserts..."));
                enterChoices(dinnerDessertChoices, customerDessertChoices, keyboard);
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
                System.out.println("\nYou will now choose the items for your social menu!\n");

                System.out.println("Choose a maximum of " + customerFancyChoices.length +
                        (customerFancyChoices.length == 1 ? " hors d'oeuvres..." : " hors d'oeuvres..."));
                enterChoices(socialFancyOptions, customerFancyChoices, keyboard);
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
                    //TODO: Test to make sure that I am not getting the error anymore
                    output = output.substring(0,output.lastIndexOf(","));
                    break;
                } else {
                    output = output + array[i] + (i == array.length - 1 ? "" : ", ");
                }
            }
            printStream.print(output);
        }
    }

    public void displayDetails(PrintStream printStream) {
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

    public String[] getDinnerSideChoices() {
        return dinnerSideChoices;
    }

    public String[] getDinnerDessertChoices() {
        return dinnerDessertChoices;
    }

    public String[] getCustomerEntreeChoices() {
        return customerEntreeChoices;
    }

    public String[] getCustomerSideChoices() {
        return customerSideChoices;
    }

    public String[] getCustomerDessertChoices() {
        return customerDessertChoices;
    }
}