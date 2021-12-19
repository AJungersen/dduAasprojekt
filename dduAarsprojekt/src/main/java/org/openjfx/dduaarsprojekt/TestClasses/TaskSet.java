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
    private String description;
    private String nameOfTheFiller;
    private boolean handedIn = false;
    ArrayList<Task> task;

    public TaskSet(int taskSet_ID, int assignment_ID, int user_ID, String name, String description, String nameOfTheFiller, boolean handedIn, ArrayList<Task> taks) {
        this.taskSet_ID = taskSet_ID;
        this.assignment_ID = assignment_ID;
        this.user_ID = user_ID;
        this.name = name;
        this.description = description;
        this.nameOfTheFiller = nameOfTheFiller;
        this.handedIn = handedIn;
        this.task = taks;
    }
    
    public TaskSet(int assignment_ID, int user_ID, String name, String description, String nameOfTheFiller, ArrayList<Task> taks) {
        this.assignment_ID = assignment_ID;
        this.user_ID = user_ID;
        this.name = name;
        this.description = description;
        this.nameOfTheFiller = nameOfTheFiller;
        this.task = taks;
    }

    public TaskSet() {
    }
    
    public int getTaskSet_ID() {
        return taskSet_ID;
    }

    public int getAssignment_ID() {
        return assignment_ID;
    }

    public void setAssignment_ID(int assignment_ID) {
        this.assignment_ID = assignment_ID;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getNameOfTheFiller() {
        return nameOfTheFiller;
    }

    public void setNameOfTheFiller(String nameOfTheFiller) {
        this.nameOfTheFiller = nameOfTheFiller;
    }
    
    public boolean getHandedIn() {
        return handedIn;
    }
    
    public void setHandedIn() {
        this.handedIn = handedIn;
    }
    
    public ArrayList<Task> getTasks() {
        return task;
    }

    public void setTasks(ArrayList<Task> taks) {
        this.task = taks;
    }
}
