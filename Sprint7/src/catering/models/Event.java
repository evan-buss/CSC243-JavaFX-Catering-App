/*
    This class holds all the data for each individual event and provides menus
    to setup each event.
 */

package catering.models;

import javafx.scene.control.TextArea;

import java.io.PrintStream;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Event extends Customer {
    public static final int minGuests = 25;
    public static final int maxJavaJoint = 50;
    public static final int maxShortys = 150;
    public static final int maxStokesay = 300;
    private static int count = 0;
    private int eventID;
    //    private int id;
    private int numGuests;
    private int guestPrice;
    private int eventCategory;      // 1="Wedding" 2="Birthday" 3="Corporate"
    private int menuCategory;       // 1="Dinner" 2="Brunch" 3="Buffet"
    private int location;           // 1="Java Joint" 2="Shorty's" 3="Stokesay Castle"
    private LocalDate eventDate;
    private Menu eventMenu;         //private menu that each event will have

    public Event(int numGuests, int eventCategory, int location,
                 int menuCategory, String eventDate, Scanner keyboard) {
        setNumGuests(numGuests);
        setEventCategory(eventCategory);
        setMenuCategory(menuCategory, keyboard);
        setEventDate(eventDate);
        setLocation(location);
        count++;
//        id = count;
    }

    public Event() {
        count++;
//        id = count;
    }


    public void setupMenu(Scanner keyboard) {
        greeting();
        System.out.println("What date is your event? (mm/dd/YYYY) ");
        setEventDate(keyboard.nextLine());
        System.out.println("How many guests are you expecting? (Min " + minGuests + ")");
        setNumGuests(Integer.parseInt(keyboard.nextLine()));

        System.out.println("Which location would you like to use?" +
                "\n\t(Java Joint = 1, Shorty's = 2, or Stokesay Castle = 3)" +
                "\n\t   (Max 50)       (Max 150)          (Max 300)");
        setLocation(Integer.parseInt(keyboard.nextLine()));

        System.out.println("How would you classify the event?" +
                "\n\t(Wedding = 1, Birthday = 2, or Corporate = 3)");
        setEventCategory(Integer.parseInt(keyboard.nextLine()));

        System.out.println("What type of menu will this event have?" +
                "\n\t(Dinner = 1, Brunch = 2, or Buffet = 3)");
        setMenuCategory(Integer.parseInt(keyboard.nextLine()), keyboard);

        modifyEvent(keyboard);
    }

    public void greeting() {
        System.out.println("We will now run you through the creation of a new event!");
        System.out.println("Please enter information about your event as " +
                "prompted.");
        System.out.println("At the end, you will be able to modify any options " +
                "\n\tas well as view all details!");
        System.out.println("==================================================\n");
    }

    public void modifyEvent(Scanner keyboard) {
        boolean run = true;
        while (run) {
            System.out.println("Enter the corresponding number to select an option.");
            System.out.print("\t0) Finalize event and exit.\n" +
                    "\t1) Change event date.\n" +
                    "\t2) Change expected guest number.\n" +
                    "\t3) Change event location.\n" +
                    "\t4) Change event classification.\n" +
                    "\t5) Change menu classification.\n" +
                    "\t6) Change menu items.\n" +
                    "\t7) View event details.\n\n");
            int answer = keyboard.nextInt();
            keyboard.nextLine();
            switch (answer) {
                case 0:
                    System.out.println("\nYou can now add another event, view your events," +
                            " or finish up!");
                    run = false;
                    break;
                case 1:
                    System.out.println("What date is your event? (mm/dd/YYYY) ");
                    setEventDate(keyboard.nextLine());
                    break;
                case 2:
                    System.out.println("How many guests are you expecting? ");
                    setNumGuests(Integer.parseInt(keyboard.nextLine()));
                    break;
                case 3:
                    System.out.println("Which location would you like to use?" +
                            "\n\t(Java Joint = 1, Shorty's = 2, or Stokesay Castle = 3) ");
                    setLocation(Integer.parseInt(keyboard.nextLine()));
                    break;
                case 4:
                    System.out.println("How would you classify the event?" +
                            "\n\t(Wedding = 1, Birthday = 2, or Corporate = 3) ");
                    setEventCategory(Integer.parseInt(keyboard.nextLine()));
                    break;
                case 5:
                    System.out.println("What type of menu will this event have?" +
                            "\n\t(Dinner = 1, Brunch = 2, or Buffet = 3) ");
                    setMenuCategory(Integer.parseInt(keyboard.nextLine()), keyboard);
                    break;
                case 6:
                    eventMenu.setupMenu(keyboard);
                    break;
                case 7:
                    displayDetails(System.out);
                    break;
                default:
                    System.out.println("\nNot a valid option please try again...");
                    break;
            }
        }
    }

    public void displayDetails(PrintStream printStream) {
        printStream.println("\nEvent Details:");
        printStream.println("==================================================");
        printStream.println("\tID: " + getEventID());
        printStream.println("\tDate: " + getEventDateString());
        printStream.println("\tGuests: " + getNumGuests());
        printStream.println("\tEvent Type: " + getEventCategory());
        printStream.println("\tMenu Type: " + getMenuCategoryStr());
        printStream.println("\tLocation: " + getLocation());
        printStream.println("\tPrice Per Guest: " + getGuestPrice());
        printStream.println("\tTotal Price: $" + getTotalPrice());
        printStream.println("==================================================");
        eventMenu.displayDetails(printStream);
    }

    public void displayDetails(TextArea textBox) {

        String textString = textBox.getText() +
                "\nEvent Details:" +
                "\n\tID: " + getEventID() +
                "\n\tDate: " + getEventDateString() +
                "\n\tGuests: " + getNumGuests() +
                "\n\tEvent Type: " + getEventCategory() +
                "\n\tMenu Type: " + getMenuCategoryStr() +
                "\n\tLocation: " + getLocation() +
                "\n\tPrice Per Guest: " + getGuestPrice() +
                "\n\tTotal Price: $" + getTotalPrice() +
                "\n================================================";
        textBox.setText(textString);
        eventMenu.displayDetails(textBox);
    }

    public int getEventID() {
        return eventID;
    }

    public void eventToDB(int customerID) {
        String sql = "INSERT INTO Events " +
                "(CustomerID, Date, GuestCount, Type, MenuType, Location, TotalCost) " +
                "VALUES ('" +
                customerID +
                "', '" +
                getEventDateString() +
//                getEventDate() +
                "', '" +
                getNumGuests() +
                "', '" +
                getEventCategory() +
                "', '" +
                getMenuCategoryStr() +
                "', '" +
                getLocation() +
                "', '" +
                getTotalPrice() +
                "');";
        DBConn.query(sql);

        //Get the CustomerID of the customer that was just inserted.
        eventID = DBConn.getLastID("EventID", "Events");
    }

    public int getNumGuests() {
        return numGuests;
    }

    public void setNumGuests(int numGuests) {
        if (numGuests >= minGuests) {
            if (numGuests <= 50) {
                guestPrice = 35;
            } else if (numGuests > 300) {
                System.err.println("Sorry. None of our locations can accommodate" +
                        " more than 300 people");
                return;
            } else {
                guestPrice = 32;
            }
            this.numGuests = numGuests;
        } else {
            System.err.println("There is a " + minGuests + " guest minimum for all locations.");
        }

    }

    public int getGuestPrice() {
        return guestPrice;
    }

    public double getTotalPrice() {
        return numGuests * getGuestPrice();
    }

    public String getEventCategory() {
        switch (eventCategory) {
            case 1:
                return "Wedding";
            case 2:
                return "Birthday";
            case 3:
                return "Corporate";
            default:
                return "";
        }
    }

    public void setEventCategory(int eventCategory) {
        if (eventCategory >= 1 && eventCategory <= 3) {
            this.eventCategory = eventCategory;
        } else {
            System.err.println("Invalid Event Category Choice...");
        }
    }

    public String getMenuCategoryStr() {
        switch (menuCategory) {
            case 1:
                return "Dinner";
            case 2:
                return "Brunch";
            case 3:
                return "Buffet";
            default:
                return "error";
        }
    }

    public void setMenuCategory(int menuCategory, Scanner keyboard) {
        if (menuCategory >= 1 && menuCategory <= 3) {
            this.menuCategory = menuCategory;
            eventMenu = new Menu(menuCategory);
            eventMenu.setupMenu(keyboard);
        } else {
            System.err.println("Invalid Menu Category Choice...");
        }
    }

    public void setMenuCategory(int menuCategory) {
        if (menuCategory >= 1 && menuCategory <= 3) {
            this.menuCategory = menuCategory;
        } else {
            System.err.println("Invalid Menu Category Choice...");
        }
    }

    //you have to set the menu category before calling this method
    public void createMenu(int menuCategory) {
        if (menuCategory >= 1 && menuCategory <= 3) {
            eventMenu = new Menu(menuCategory);
            setMenuCategory(menuCategory);
        }
    }

    public Menu getEventMenu() {
        return eventMenu;
    }


    public String getEventDateString() {
        return eventDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy (eeee)"));
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        try {
            this.eventDate = LocalDate.parse(eventDate,
                    DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } catch (DateTimeException ex) {
            System.err.println("Invalid date format, defaulting to current day.");
            this.eventDate = LocalDate.now();
        }

    }

    public void setEventDate(LocalDate date) {
        this.eventDate = date;
    }

    public String getLocation() {
        switch (location) {
            case 1:
                return "Java Joint";
            case 2:
                return "Shorty''s";
            case 3:
                return "Stokesay''s Castle";
            default:
                return "";
        }
    }

    public void setLocation(int location) {
        if (numGuests >= minGuests) {
            if (location == 1 && (numGuests <= maxJavaJoint)) {
                this.location = location;
            } else if (location == 2 && (numGuests <= maxShortys)) {
                this.location = location;
            } else if (location == 3 && (numGuests <= maxStokesay)) {
                this.location = location;
            } else {
                System.err.println("Invalid location or beyond location capacity...\n");
            }
        } else {
            System.err.println("There is a 25 guest minimum.");
        }
    }
}