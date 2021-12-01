/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt.TestClasses;

import java.util.ArrayList;

/**
 *
 * @author chris
 */
public class MultipelChoiseQuestion extends Question{
    ArrayList<MultipelChoiseAnswer> answerOptions;

    public MultipelChoiseQuestion(ArrayList<MultipelChoiseAnswer> answerOptions, int question_ID, String question) {
        super(question_ID, question);
        this.answerOptions = answerOptions;
        setType(QuestionsType.multipelChoiseQuestion);
    }

    public MultipelChoiseQuestion(ArrayList<MultipelChoiseAnswer> answerOptions,String question) {
        super(question);
        this.answerOptions = answerOptions;
        setType(QuestionsType.multipelChoiseQuestion);
    }
    
    public ArrayList<MultipelChoiseAnswer> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(ArrayList<MultipelChoiseAnswer> answerOptions) {
        this.answerOptions = answerOptions;
    }
    
    
}
