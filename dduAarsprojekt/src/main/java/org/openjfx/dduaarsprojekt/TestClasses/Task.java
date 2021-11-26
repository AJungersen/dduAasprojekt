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
public class Task {
    private int task_ID;
    private Question question;
    private String answer;

    public Task(int task_ID, Question question, String answer) {
        this.task_ID = task_ID;
        this.question = question;
        this.answer = answer;
    }
    
    public Task(Question question, String answer) {
        this.question = question;
        this.answer = answer;
    }
    
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
