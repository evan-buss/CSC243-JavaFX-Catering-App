/*
    This class initializes the program to different end user types.
 */

import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Scanner keyboard = new Scanner(System.in);
        Customer customer = new Customer(keyboard);
    }
}

