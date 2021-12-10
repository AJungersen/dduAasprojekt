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
public class Question {
    private int question_ID;
    private QuestionsType type;
    private String question;


    public Question(int question_ID, String question) {
        this.question_ID = question_ID;
        this.question = question;
    }
    
    public Question(String question) {
        this.question = question;
    }
    
    public int getQuestion_ID() {
        return question_ID;
    }

    public QuestionsType getType() {
        return type;
    }

    public void setType(QuestionsType type) {
        this.type = type;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public CorrectAnswerBasedQuestion asCorrectAnswerBasedQuestion(){
        return (CorrectAnswerBasedQuestion )this; 
    }
    
    public MultipelChoiseQuestion asMultipelChoiseQuestion() {
        return (MultipelChoiseQuestion) this;
    }
}
