package com.company;

//The class used for creating question objects. That's it really. Everything else is handled by the program.
public class Question {
    int questionID;
    String questionContent;
    String questionOptions;
    String questionAnswer;

    public Question(int _questionID, String _questionContent, String _questionOptions, String _questionAnswer){
        questionID = _questionID;
        questionContent = _questionContent;
        questionOptions = _questionOptions;
        questionAnswer = _questionAnswer;
    }

}
