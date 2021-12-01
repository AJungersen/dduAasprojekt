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

    public CorrectAnswerBasedQuestion(String correctAnswer, int question_ID, String question) {
        super(question_ID, question);
        this.correctAnswer = correctAnswer;
        setType(QuestionsType.correctAnswerBasedQuestion);
    }

    public CorrectAnswerBasedQuestion(String correctAnswer, String question) {
        super(question);
        this.correctAnswer = correctAnswer;
        setType(QuestionsType.correctAnswerBasedQuestion);
    }
    
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
