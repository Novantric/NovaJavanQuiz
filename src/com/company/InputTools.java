package com.company;
import java.util.Scanner;

//Like in main, simplifies the print statements a tad
import static java.lang.System.out;

//Used throughout the program for handling user input
public class InputTools {
    //The scanner is used often, so is declared here.
    static Scanner inputScanner = new Scanner(System.in);

    //Returns the input as a string
    public static String StringInput(String inputMessage){
        out.print(inputMessage + "\n>>> ");
        return inputScanner.nextLine();
    }

    //Tries to parse the input to an integer, then returns it.
    public static int IntInput(String inputMessage){
        while (true){
            out.print(inputMessage + "\n>>> ");
            try {
                return Integer.parseInt(inputScanner.nextLine());
            }
            catch (NumberFormatException ex){
                out.println("\nPlease enter a valid number!\nThis number should not contain decimals.");
            }
        }
    }

    //Returns true or false, depending on the user's input
    public static boolean YNInput(String inputMessage){
        while (true) {
            out.print("\n" + inputMessage + "\n(y/n)>>> ");
            String rawInput = inputScanner.nextLine();
            switch (rawInput) {
                case "y":
                case "Y":
                case "1":
                    return true;
                case "n":
                case "N":
                case "0":
                    return false;
                default:
                    out.println("Please enter either Y or N.");
            }
        }
    }

    //If the user is running this program in a terminal, then this will act like "cls" when called
    public static void clearConsole() {
        out.print("\033[H\033[2J");
        out.flush();
    }
}
