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
    private String comment;

    public Task(int task_ID, Question question, String answer, String comment) {
        this.task_ID = task_ID;
        this.question = question;
        this.answer = answer;
        this.comment = comment;
    }
    
    public Task(Question question, String answer, String comment) {
        this.question = question;
        this.answer = answer;
        this.comment = comment;
    }

    public Task() {
        
    }

    public int getTask_ID() {
        return task_ID;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
}
