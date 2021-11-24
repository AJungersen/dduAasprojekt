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
public class textAnswerBasedQuestion extends Question{
    
    public textAnswerBasedQuestion(int question_ID, String type, String question) {
        super(question_ID, type, question);
    }
    
    public textAnswerBasedQuestion(String type, String question) {
        super(type, question);
    }
}
