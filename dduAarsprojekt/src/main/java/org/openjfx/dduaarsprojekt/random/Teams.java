/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt.random;

import java.util.ArrayList;
import org.openjfx.dduaarsprojekt.TestClasses.TaskSet;

/**
 *
 * @author danie
 */
public class Teams {
    private int team_ID;
    private String teamName;
    private ArrayList<TaskSet> taskSets;
    private ArrayList<Student> students;
    
    Teams(int ID, String tName, ArrayList<TaskSet> tSets, ArrayList<Student> Studs){
        team_ID = ID;
        teamName = tName;
        taskSets = tSets;
        students = Studs;
    }
    
    Teams(String tName){
        teamName = tName;
        taskSets = new ArrayList();
        students = new ArrayList();
    }
    
    private int getTeam_ID(){
        return team_ID;
    }
    private String getTeamName(){
        
    }
}
