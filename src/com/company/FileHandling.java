package com.company;

//Java.io.* is used because every library under .io is used here
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.Scanner;

public class FileHandling {
    //Stores the name of the currently loaded quiz
    static String loadedQuizName;

    //Related to Quizzes
    public static void readQuizFromFile(){
        String tempQuestion, tempOptions, tempAnswer, fileLine;
        int tempQuestionID, quizChoice = 1;
        boolean quizChoiceLoop = true;

        //Removes all the stored questions. loadedQuizName will be overwritten later.
        QuestionManagement.questionStore.clear();

        //Search the current directory for files created by this program.
        //Could be changed to a folder in C:\Program Files in the future.
        File currentDir = new File(".");
        File[] matches = currentDir.listFiles((dir, name) -> name.startsWith("QUIZ_") && name.endsWith(".txt"));

        //Display and select a quiz file
        while (quizChoiceLoop){
            if (matches == null || matches.length == 0) {
                return;
            } else {
                System.out.println("Please enter the number that matches the name of the quiz you'd like to take.");
                for (int i = 0; i < matches.length; i++) {
                    System.out.println((i+1) + ") " + matches[i]);
                }
            }
            quizChoice = InputTools.IntInput("");

            if (quizChoice <= 0 || quizChoice > (matches.length + 1)){
                System.out.println("Please enter a valid value.");
            }
            else {
                quizChoiceLoop = false;
            }
        }
        //Use the choice to read the file using its file name
        File filePath = matches[quizChoice - 1];
        loadedQuizName = filePath.getName();

        File fileObject = new File(filePath.toString());

        try (Scanner fileReader = new Scanner(fileObject)) {
            fileLine = fileReader.nextLine();
            UserResults.quizDurationTimer = Integer.parseInt(fileLine);

            while (fileReader.hasNextLine()) {
                fileLine = fileReader.nextLine();

                String[] lineParts = fileLine.split("`");
                tempQuestion = lineParts[0];
                tempOptions = lineParts[1];
                tempAnswer = lineParts[2];
                tempQuestionID = QuestionManagement.numSavedQuestions() + 1;

                Question readQuestion = new Question(tempQuestionID, tempQuestion, tempOptions, tempAnswer);
                QuestionManagement.questionStore.add(readQuestion);
            }
        }
        catch (FileNotFoundException ex) {
            //If we failed to read from the file, this prevents the program crashing
            System.out.println("The file does not exist yet.");
        }
    }
    public static void saveQuizToFile(){
        try {
            String pathName, newQuizName;
            int quizLength;

            newQuizName = InputTools.StringInput("What would you like to name the quiz?");

            //Create the objects needed to write to a file with the user's input
            pathName = "QUIZ_" + newQuizName + ".txt";
            File fileObject = new File(pathName);
            FileWriter writeToFile = new FileWriter(pathName);
            if (fileObject.createNewFile()) System.out.println("Created a new file.");

            quizLength = (InputTools.IntInput("How long should the quiz be, in seconds?")*1000);

            //Saves values like the quiz timer and each question's data
            writeToFile.write(quizLength + "\n");
            for(int i=1; i <= QuestionManagement.numSavedQuestions(); i++){
                writeToFile.write((QuestionManagement.questionStore.get(i - 1).questionContent) + "`" + (QuestionManagement.questionStore.get(i - 1).questionOptions) + "`" + (QuestionManagement.questionStore.get(i - 1).questionAnswer) + "\n");
            }
            writeToFile.close();
            System.out.println("The questions were saved successfully!");

        }
        catch (IOException ex){
            System.out.println("We could not create a file for the Quiz data.\nPlease move this executable to a different directory and try again.");
        }
    }

    //Related to the user's score
    public static void nameScoreFile(){
        String fileName, filePath;
        boolean saveScore;

        saveScore = InputTools.YNInput("Would you like to save your score to a file?");
        if (saveScore) {
            fileName = InputTools.StringInput("What would you like to name the file?");

            //Finds the user's Desktop and sets the paths to that folder
            File desktopDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
            filePath = desktopDirectory.getAbsolutePath() + "\\" + fileName + ".txt";
            File fileObject = new File(filePath);

            //
            try {
                if (fileObject.createNewFile()) {
                    saveUserScore(filePath);
                } else if (InputTools.YNInput("This file always exists! Would you like to overwrite it?"))
                    saveUserScore(filePath);
            }
            catch (IOException ex){
                //We can give the user an alternative here if their permissions forbid access to the desktop.
                boolean saveToLocalDir = InputTools.YNInput("We couldn't create the file on your desktop.\\nWould you like to save to the same folder as this program?");
                if (saveToLocalDir){
                    filePath = fileName + ".txt";
                    saveUserScore(filePath);
                }
            }
        }
    }
    public static void saveUserScore(String filePath){
        //Tries to write data to the path specified. Is its own function as it is called in 3 scenarios.
        try {
            FileWriter writeToFile = new FileWriter(filePath);

            writeToFile.write("################ NOVAN'S QUIZ PROGRAM RESULTS!!! ################\n");
            if (loadedQuizName != null){
                writeToFile.write("QUIZ NAME: " + loadedQuizName + "\n");
            }
            writeToFile.write("YOU GOT " + UserResults.scoreData[1] + "%!\n");
            writeToFile.write("FINAL SCORE: " + UserResults.scoreData[0] + "!\n");

            writeToFile.close();
            System.out.println("Saved successfully to your Desktop!");
        }
        catch (IOException ex){
            System.out.println("As we couldn't write to this file, the data was not saved.");
        }
    }
}
