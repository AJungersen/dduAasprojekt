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
<<<<<<< Updated upstream:dduAarsprojekt/src/main/java/org/openjfx/dduaarsprojekt/TestClasses/TaskSet.java
    ArrayList<Task> tasks;

    public TaskSet(int taskSet_ID, int user_ID, String name, ArrayList<Task> taks) {
=======
    private String nameOfTheFiller;
    ArrayList<Task> taks;

    public TaskSet(int taskSet_ID, int assignment_ID, int user_ID, String name, String nameOfTheFiller, ArrayList<Task> taks) {
>>>>>>> Stashed changes:dduAarsprojekt/src/main/java/org/openjfx/dduaarsprojekt/TestClasses/TaksSet.java
        this.taskSet_ID = taskSet_ID;
        this.assignment_ID = assignment_ID;
        this.user_ID = user_ID;
        this.name = name;
<<<<<<< Updated upstream:dduAarsprojekt/src/main/java/org/openjfx/dduaarsprojekt/TestClasses/TaskSet.java
        this.tasks = taks;
    }
    
    public TaskSet(int user_ID, String name, ArrayList<Task> taks) {
        this.user_ID = user_ID;
        this.name = name;
        this.tasks = taks;
=======
        this.nameOfTheFiller = nameOfTheFiller;
        this.taks = taks;
    }
    
    public TaskSet(int assignment_ID, int user_ID, String name, String nameOfTheFiller, ArrayList<Task> taks) {
        this.assignment_ID = assignment_ID;
        this.user_ID = user_ID;
        this.name = name;
        this.nameOfTheFiller = nameOfTheFiller;
        this.taks = taks;
>>>>>>> Stashed changes:dduAarsprojekt/src/main/java/org/openjfx/dduaarsprojekt/TestClasses/TaksSet.java
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

<<<<<<< Updated upstream:dduAarsprojekt/src/main/java/org/openjfx/dduaarsprojekt/TestClasses/TaskSet.java
    public ArrayList<Task> getTask() {
        return tasks;
=======
    public String getNameOfTheFiller() {
        return nameOfTheFiller;
    }

    public void setNameOfTheFiller(String nameOfTheFiller) {
        this.nameOfTheFiller = nameOfTheFiller;
    }
    
    public ArrayList<Task> getTaks() {
        return taks;
>>>>>>> Stashed changes:dduAarsprojekt/src/main/java/org/openjfx/dduaarsprojekt/TestClasses/TaksSet.java
    }

    public void setTask(ArrayList<Task> taks) {
        this.tasks = taks;
    }

    public void setDescription(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
