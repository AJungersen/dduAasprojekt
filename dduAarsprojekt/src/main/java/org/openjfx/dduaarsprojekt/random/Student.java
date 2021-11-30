/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt.random;

/**
 *
 * @author danie
 */
public class Student extends User{
    private String schoolClass;
    private String name;

    public Student(String schoolClass, String name, int user_ID, int userType_ID, String username, String password) {
        super(user_ID, userType_ID, username, password);
        this.schoolClass = schoolClass;
        this.name = name;
        
        setType("student");
    }

    public String getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(String schoolClass) {
        this.schoolClass = schoolClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
