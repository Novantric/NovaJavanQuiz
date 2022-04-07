/*
STUDENT ID: 2103983
ELEMENT 010 TEAM NAME: Pseudo Byte

The included .jar file is a compiled version of this program that you can run from the command line.
This is because the clear console subroutine works when the program is run this way.

*/

package com.company;

//We use printline a lot here so this simplifies it a tad.
import static java.lang.System.*;

public class Main {
    //Main is simply the main menu, and calls all the other functions. It loops until the user chooses to exit.
    //It's worth noting this was used as testing until I finished every other function/sub
    public static void main(String[] args) {
        boolean menuLoop = true, firstOpen = true;
        int menuChoice;

        while (menuLoop){
            out.println("################ NOVAN'S QUIZ PROGRAM ################");

            //Little easter egg of a for loop shows a unique message when the program loads.
            if (firstOpen){
                out.println("Select an option, and lets get started!\n");
                firstOpen = false;
            }
            else {
                out.println("Please select an option!\n");
            }

            out.println("1 - Take a quiz!");
            out.println("2 - Make a quiz!");
            out.println("3 - Show me the content of loaded quizzes!");
            out.println("0 - Exit");
            menuChoice = InputTools.IntInput("");
            InputTools.clearConsole();

            //Switch is better than if here. Calls each function that matches the user's choice.
            switch (menuChoice){
                case 1:
                    if (QuestionManagement.numSavedQuestions() != 0){
                        if (FileHandling.loadedQuizName != null){
                            out.println("Loaded Quiz name: " + FileHandling.loadedQuizName);
                        }
                        boolean loadNewQuestions = InputTools.YNInput("There's currently a quiz already loaded.\nWould you like to:\ny - load a new quiz\nn - load the saved quiz");
                        if (loadNewQuestions) {
                            FileHandling.readQuizFromFile();
                        }
                    }
                    else FileHandling.readQuizFromFile();
                    UserResults.takeTheQuiz();
                    break;
                case 2:
                    QuestionManagement.addQuestion();
                    break;
                case 3:
                    QuestionManagement.displayStoredData();
                    break;
                case 0:
                    out.println("'Thank you for using this program! <3' - Novan 2022");
                    menuLoop = false;
                    break;
                default:
                    //Some extra input validation here, in case it's not properly handled by InputTools
                    out.println("Please enter a valid menu option!");
            }
        }
    }
}
