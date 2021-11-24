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
public class TaksSet {
    private int taskSet_ID;
    private int user_ID;
    private String name;
    ArrayList<Task> taks;

    public TaksSet(int taskSet_ID, int user_ID, String name, ArrayList<Task> taks) {
        this.taskSet_ID = taskSet_ID;
        this.user_ID = user_ID;
        this.name = name;
        this.taks = taks;
    }
    
    public TaksSet(int user_ID, String name, ArrayList<Task> taks) {
        this.user_ID = user_ID;
        this.name = name;
        this.taks = taks;
    }
    
    public int getTaskSet_ID() {
        return taskSet_ID;
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

    public ArrayList<Task> getTaks() {
        return taks;
    }

    public void setTaks(ArrayList<Task> taks) {
        this.taks = taks;
    }
}
