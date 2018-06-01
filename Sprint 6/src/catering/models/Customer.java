/*
    This class holds information about the customer, creates the events, and
    guides the customer in creation of event details/menu choices
 */

package catering.models;

import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer {
    private String name;
    private String email;
    private Long phoneNumber;
    private ArrayList<Event> events = new ArrayList<>();
    private int eventCounter = 0;

    public Customer() {
    }

    public Customer(Scanner keyboard) {
        customerMenu(keyboard);
    }

    public ArrayList<Event> getEventsArray() {
        return events;
    }

    public Event getEvent(int index) {
        return events.get(index);
    }

    public Event getLatestEvent() {
        return events.get(events.size() - 1);
    }

    //use this to access the array of customer events
    public Event addEvent() {
        events.add(new Event());
        eventCounter++;
        return events.get(events.size()-1);
    }

    public void removeEvent(int index) {
        events.remove(index);
        eventCounter--;
    }

    public int getEventCounter() {
        return eventCounter;
    }

    public void customerMenu(Scanner keyboard) {
        userInfoMenu(keyboard);
        boolean run = true;
        do {
            System.out.println("\nOptions:");
            System.out.println("\t0) Quit Application and Write Events to File");
            System.out.println("\t1) Add a new event");
            System.out.println("\t2) View total cost of all events");
            System.out.println("\t3) Display events by order created (ascending)");
            System.out.println("\t4) Display events by order created (descending)");
            System.out.println("\t5) Display events by date (ascending)");
            System.out.println("\t6) Display events by date (descending)");
            System.out.println("\t7) Display events by guest count (ascending)");
            System.out.println("\t8) Display events by guest count (descending)");
            System.out.println("\nYou currently have " + eventCounter + " events.\n");

            int input = 0;
            try {
                input = Integer.parseInt(keyboard.nextLine());
            } catch (Exception ex) {
                System.out.println("Input not valid... Try Again.");
                input = 666;      //give a garbage value so 0 case not triggered
            }

            switch (input) {
                case 0:
                    try {
                        File file = new File("schedule.dat");
                        FileOutputStream outputStream = new FileOutputStream(file, true);
                        PrintStream printStream = new PrintStream(outputStream);
                        displayDetails(printStream);
                        sortByDate(events, printStream, true);
                        printStream.println("********************************************************************************");
                    } catch (FileNotFoundException ex) {
                        System.err.println("FileNotFound Exception");
                    }
                    System.out.println("\nYour events:");
                    displayDetails(System.out);
                    sortByDate(events, System.out, true);

                    System.out.println("\nYou can find this schedule in the schedule.dat file!");
                    System.out.println("\nThanks for choosing Juliana's Catering!");
                    System.out.println("Juliana will call within 2 business days to discuss" +
                            "\n\tthe details of your event!");
                    System.out.println("Have a great day!");
                    run = false;
                    break;
                case 1:
                    if (eventCounter < 5) {
                        events.add(eventCounter, new Event());
                        events.get(eventCounter).setupMenu(keyboard);
                        eventCounter++;
                    } else {
                        System.out.println("\nSorry, you have reached the 5 event limit...");
                    }
                    break;
                case 2:
                    int totalPrice = 0;
                    for (Event e : events) {
                        totalPrice += e.getTotalPrice();
                    }
                    System.out.println("\nTotal Cost of your Events: $" + totalPrice);
                    break;
                case 3:
                    System.out.println("\nEvents by order added (ascending): ");
                    displayDetails(System.out);
                    for (Event e : events) {
                        e.displayDetails(System.out);
                    }
                    break;
                case 4:
                    System.out.println("\nEvents by order added (descending):");
                    displayDetails(System.out);
                    for (int i = events.size() - 1; i >= 0; i--) {
                        if (events != null && events.get(i) != null) {
                            events.get(i).displayDetails(System.out);
                        }
                    }
                    break;
                case 5:
                    System.out.println("\nEvents sorted by date (ascending).");
                    displayDetails(System.out);
                    sortByDate(events, System.out, true);
                    break;
                case 6:
                    System.out.println("\nEvents sorted by date (descending).");
                    displayDetails(System.out);
                    sortByDate(events, System.out, false);
                    break;
                case 7:
                    System.out.println("\nEvents sorted by guest count (ascending)");
                    displayDetails(System.out);
                    sortByGuests(events, System.out, true);
                    break;
                case 8:
                    System.out.println("\nEvents sorted by guest count (ascending)");
                    displayDetails(System.out);
                    sortByGuests(events, System.out, false);
                    break;
                //TODO: Make an option to delete an event?
                default:
                    System.err.println("\nNot a valid choice... Try Again");
                    break;
            }
        } while (run);
    }

    public void userInfoMenu(Scanner keyboard) {

        System.out.println("Welcome to Juliana's Catering Service!\n");

        System.out.println("To get started, please enter your full name: ");
        setName(keyboard.nextLine());

        System.out.println("\nGreat! Please enter your email so we can keep in touch: ");
        setEmail(keyboard.nextLine());

        System.out.println("\nLastly, enter your phone number (##########): ");
        setPhoneNumber(keyboard.nextLine());

        System.out.println("\nNext we will take you through the creation of your event!");
    }

    public void displayDetails(PrintStream printStream) {
        printStream.println("\nCustomer Details:");
        printStream.println("==================================================");
        printStream.println("\tName: " + getName());
        printStream.println("\tEmail: " + getEmail());
        printStream.println("\tPhone Number: " + getPhoneNumber());
        printStream.println("==================================================");
    }

    public void displayDetails(TextArea textBox) {
        //always add on to the text that already exists
        String textString =  textBox.getText() +
                            "Customer Details:" +
                            "\n================================================" +
                            "\n\tName: " + getName() +
                            "\n\tPhone Number: " + getPhoneNumber() +
                            "\n\tEmail: " + getEmail() +
                            "\n================================================";
        textBox.setText(textString);
    }

    public void sortByDate(ArrayList<Event> arrayList, PrintStream printStream, boolean isAscending) {
        //local copy of the input array
        ArrayList<Event> localCopy = new ArrayList<>(arrayList);
        //Bubble sort
        Event temp;
        if (isAscending) {     //output ascending order
            for (int i = 1; i < localCopy.size(); i++) {
                for (int j = i; j > 0; j--) {
                    if (localCopy.get(j).getEventDate().isBefore(localCopy.get(j - 1).getEventDate())) {
                        temp = localCopy.get(j);
                        localCopy.set(j, localCopy.get(j - 1));
                        localCopy.set(j - 1, temp);
                    }
                }
            }
        } else {               //output descending order
            for (int i = 1; i < localCopy.size(); i++) {
                for (int j = i; j > 0; j--) {
                    if (localCopy.get(j).getEventDate().isAfter(localCopy.get(j - 1).getEventDate())) {
                        temp = localCopy.get(j);
                        localCopy.set(j, localCopy.get(j - 1));
                        localCopy.set(j - 1, temp);
                    }
                }
            }
        }
        //print new array
        for (Event e : localCopy) {
            if (e != null) {
                e.displayDetails(printStream);
            }
        }
    }

    public void sortByGuests(ArrayList<Event> arrayList, PrintStream printStream, boolean isAscending) {
        //local copy of the input array
        ArrayList<Event> localCopy = new ArrayList<>(arrayList);
        //Bubble sort
        Event temp;
        if (isAscending) {     //output ascending order
            for (int i = 1; i < localCopy.size(); i++) {
                for (int j = i; j > 0; j--) {
                    if (localCopy.get(j).getNumGuests() < localCopy.get(j - 1).getNumGuests()) {
                        temp = localCopy.get(j);
                        localCopy.set(j, localCopy.get(j - 1));
                        localCopy.set(j - 1, temp);
                    }
                }
            }
        } else {               //output descending order
            for (int i = 1; i < localCopy.size(); i++) {
                for (int j = i; j > 0; j--) {
                    if (localCopy.get(j).getNumGuests() > localCopy.get(j - 1).getNumGuests()) {
                        temp = localCopy.get(j);
                        localCopy.set(j, localCopy.get(j - 1));
                        localCopy.set(j - 1, temp);
                    }
                }
            }
        }
        //print new array
        for (Event e : localCopy) {
            if (e != null) {
                e.displayDetails(printStream);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        //TODO: Add logic to check if there is an "@" sign
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        //TODO: add logic to check length and parse integers
        try {
            this.phoneNumber = Long.parseLong(phoneNumber);
        } catch (Exception ex) {
            System.err.println("There is an error in your phone number");
        }
    }
}