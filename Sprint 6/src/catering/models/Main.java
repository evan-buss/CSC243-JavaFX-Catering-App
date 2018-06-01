/*
    This class initializes the program to different end user types.
 */

package catering.models;

import catering.models.Customer;

import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Scanner keyboard = new Scanner(System.in);
        Customer customer = new Customer(keyboard);
    }
}

