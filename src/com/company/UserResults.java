package com.company;

public class UserResults {
    //First value is an "out of" score, second a percentage
    public static String[] scoreData = new String[2];
    //This is modified by the user or when loading a quiz. This is a default value for compatibility.
    public static int quizDurationTimer = 50000;

    //This is how the users interact with the quiz
    public static void takeTheQuiz(){
        int scoreCounter = 0;
        boolean ranOutOfTime = false, showTimeRemaining;

        //First, check if there's any questions in memory
        if (QuestionManagement.numSavedQuestions() == 0) {
            System.out.println("Sorry, but there are no Quizzes available. Please come another time!");
        }
        else {
            InputTools.clearConsole();
            showTimeRemaining = InputTools.YNInput("Would you like to be able to see the time remaining on the quiz?");

            //Shuffle the question store
            QuestionManagement.questionShuffle(true);

            //Declared here for increased accuracy
            long currentTime = System.currentTimeMillis();
            long endTime = currentTime + quizDurationTimer;

            //Display questions as long as the timer is greater than 0
            for (int i = 1; i <= QuestionManagement.numSavedQuestions(); i++) {
                if (System.currentTimeMillis() < endTime) {
                    InputTools.clearConsole();
                    if (showTimeRemaining){
                        System.out.println("\n" + timeRemaining(endTime) + " left"); //remaining is more scary
                    }

                    if (i == QuestionManagement.numSavedQuestions()){
                        System.out.println("Last Question!");
                    }
                    else {
                        System.out.println("Question " + i + ":");
                    }

                    System.out.println(QuestionManagement.questionStore.get(i - 1).questionContent);
                    String userAnswer = InputTools.StringInput("Options: " + (QuestionManagement.questionStore.get(i - 1).questionOptions));
                    if (userAnswer.equals(QuestionManagement.questionStore.get(i - 1).questionAnswer)) {
                        scoreCounter += 1;
                    }
                } else {
                    //Stops looping after the user presses enter and the timer is done
                    ranOutOfTime = true;
                    break;
                }
                InputTools.clearConsole();
            }

            //Display the results
            if (ranOutOfTime) {
                System.out.println("\nYou ran out of time!");
            } else {
                System.out.println("\nYou finished the quiz with " + timeRemaining(endTime) + " left!");
            }
            calcQuizScore(scoreCounter, QuestionManagement.numSavedQuestions());
            System.out.println("You scored " + scoreData[1] + "%, or " + scoreData[0] + "!");

            //Restore the normal order of the question store
            QuestionManagement.questionShuffle(false);

            //Offer to save the score before returning to main
            FileHandling.nameScoreFile();
        }
    }
    public static void calcQuizScore(int scoreCounter, int numbOfQuestions){
        //Updates the scoreData array, which used to be 2 separate variables.
        scoreData[0] = (scoreCounter + "/" + numbOfQuestions);
        scoreData[1] = (String.valueOf((scoreCounter/numbOfQuestions) * 100));
    }
    public static String timeRemaining(long endTime){
        //Returns the remaining time when taking a quiz in seconds or minutes.
        long timeRemaining = (endTime - System.currentTimeMillis()) / 1000;
        int timeRemainingRemainder;

        if (timeRemaining > 60){
            timeRemainingRemainder = Math.floorMod(timeRemaining, 60);
            timeRemaining /= 60;

            return timeRemaining + " minutes and " + timeRemainingRemainder + " seconds";
        }
        return timeRemaining + " seconds";
    }
}
