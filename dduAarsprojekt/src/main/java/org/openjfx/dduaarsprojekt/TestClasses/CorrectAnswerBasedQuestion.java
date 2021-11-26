/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt.TestClasses;

/**
 *
 * @author chris
 */
public class CorrectAnswerBasedQuestion extends Question{
    public String correctAnswer;

    public CorrectAnswerBasedQuestion(String correctAnswer, int question_ID, String type, String question) {
        super(question_ID, type, question);
        this.correctAnswer = correctAnswer;
    }

    public CorrectAnswerBasedQuestion(String correctAnswer, String type, String question) {
        super(type, question);
        this.correctAnswer = correctAnswer;
    }
    
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
