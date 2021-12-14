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
public class TaskSet {
    private int taskSet_ID;
    private int assignment_ID;
    private int user_ID;
    private String name;
    private String nameOfTheFiller;
    ArrayList<Task> taks;

    public TaskSet(int taskSet_ID, int assignment_ID, int user_ID, String name, String nameOfTheFiller, ArrayList<Task> taks) {
        this.taskSet_ID = taskSet_ID;
        this.assignment_ID = assignment_ID;
        this.user_ID = user_ID;
        this.name = name;
        this.nameOfTheFiller = nameOfTheFiller;
        this.taks = taks;
    }
    
    public TaskSet(int assignment_ID, int user_ID, String name, String nameOfTheFiller, ArrayList<Task> taks) {
        this.assignment_ID = assignment_ID;
        this.user_ID = user_ID;
        this.name = name;
        this.nameOfTheFiller = nameOfTheFiller;
        this.taks = taks;
    }
    
    public int getTaskSet_ID() {
        return taskSet_ID;
    }

    public int getAssignment_ID() {
        return assignment_ID;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOfTheFiller() {
        return nameOfTheFiller;
    }

    public void setNameOfTheFiller(String nameOfTheFiller) {
        this.nameOfTheFiller = nameOfTheFiller;
    }
    
    public ArrayList<Task> getTaks() {
        return taks;
    }

    public void setTaks(ArrayList<Task> taks) {
        this.taks = taks;
    }
}
