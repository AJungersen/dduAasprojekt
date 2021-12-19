/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AssistantClasses;

/**
 *
 * @author danie
 */
public class AssistantMyTeamsForTeamStaffController {
    String teamName;
    int tests;
    int studentCount;

    public AssistantMyTeamsForTeamStaffController(String teamName, int tests, int studentCount) {
        this.teamName = teamName;
        this.tests = tests;
        this.studentCount = studentCount;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTests() {
        return tests;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTests(int tests) {
        this.tests = tests;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }
    
    
}
