package com.company;

//We'll be using a list to handle each question here.
import java.util.ArrayList;
import java.util.Collections;

public class QuestionManagement {

    //Probably the most important part of the program.
    static ArrayList<Question> questionStore = new ArrayList<>();
    //here so it's contents are preserved
    static ArrayList<Question> backupQuestionStore = new ArrayList<>();

    static void addQuestion(){
        String questionQuestionData, questionAnswerData, questionOptions;
        int numbOfQuestions;
        boolean saveShouldSaveQuestions;

        numbOfQuestions = InputTools.IntInput("How many questions would you like to add?");

        //Loops through creating and saving questions
        for (int i = 0; i < numbOfQuestions; i++) {
            InputTools.clearConsole();
            questionQuestionData = InputTools.StringInput("\nPlease enter the next question.");
            questionOptions = InputTools.StringInput("Enter the options the user can choose from.");
            questionAnswerData = InputTools.StringInput("Lastly, enter the correct answer to the question.");

            Question question = new Question(numSavedQuestions()+1, questionQuestionData, questionOptions, questionAnswerData);
            questionStore.add(question);

            //Just improves user experience.
            if (i == 0) {
                System.out.println("You have created 1 question.");
            } else {
                System.out.println("You have created " + numSavedQuestions() + " questions.");
            }
        }

        //Saving the questions
        saveShouldSaveQuestions = InputTools.YNInput("Would you like to save the questions you created?");
        if (saveShouldSaveQuestions){
            FileHandling.saveQuizToFile();
        }
    }
    static int numSavedQuestions(){
        //Returns the number of questions in the list.
        //Used to just change a public int until very late in development until I realised it was wasting resources.
        return questionStore.size();
    }
    static void displayStoredData(){
        //A simple function that displays the data of each question loaded in memory.
        if (numSavedQuestions() == 0) {
            System.out.println("You haven't loaded any Quizzes yet!");
            return;
        }

        System.out.println("\nCurrently loaded Quiz: " + FileHandling.loadedQuizName);
        for(int i = 1; i <= questionStore.size(); i++){
            System.out.println("\nQuestion ID " + (questionStore.get(i - 1).questionID));
            System.out.println((questionStore.get(i - 1).questionContent));
            System.out.println("Options: " + (questionStore.get(i - 1).questionOptions));
            System.out.println("Answer: " + (questionStore.get(i - 1).questionAnswer));
        }
    }
    static void questionShuffle(boolean shuffle){
        //Last function added to the program in order to preserve the order of questions.

        //Shuffle the Question Store
        if (shuffle) {
            backupQuestionStore.addAll(questionStore);
            Collections.shuffle(questionStore);
        } else {
            //Restore the question store's default order
            questionStore.clear();
            questionStore.addAll(backupQuestionStore);
            backupQuestionStore.clear();
        }
    }
}
