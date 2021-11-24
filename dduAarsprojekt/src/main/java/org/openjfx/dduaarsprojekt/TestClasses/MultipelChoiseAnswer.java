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
public class MultipelChoiseAnswer {

    private int answer_ID;
    private String answer;
    private boolean correct;

    public MultipelChoiseAnswer(int answer_ID, String answer, boolean correct) {
        this.answer_ID = answer_ID;
        this.answer = answer;
        this.correct = correct;
    }

    public MultipelChoiseAnswer(String answer, boolean correct) {
        this.answer = answer;
        this.correct = correct;
    }

    public int getAnswer_ID() {
        return answer_ID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean getCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
