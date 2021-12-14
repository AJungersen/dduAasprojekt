/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt.random;

import java.util.ArrayList;
import org.openjfx.dduaarsprojekt.TestClasses.*;

/**
 *
 * @author chris
 */
public class Team {
    private int team_ID;
    private String teamName;
    private ArrayList<TaskSet> taskSet;
    private ArrayList<Student> students;

    
    public Team(String teamName){
        this.teamName = teamName;
    }
    
    public Team(int team_ID, String teamName, ArrayList<TaskSet> taskSet, ArrayList<Student> students) {
        this.team_ID = team_ID;
        this.teamName = teamName;
        this.taskSet = taskSet;
        this.students = students;
    }

    public int getTeam_ID() {
        return team_ID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<TaskSet> getTaskSet() {
        return taskSet;
    }

    public void setTaskSet(ArrayList<TaskSet> taskSet) {
        this.taskSet = taskSet;
    }
}
