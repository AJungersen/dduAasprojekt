/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

/**
 *
 * @author danie
 */
public class User {

    private int user_ID;
    private int userType_ID;
    private String username;
    private String password;
    private String type = "";

    public User() {
    }

    public User(int user_ID, int userType_ID, String type) {
        this.user_ID = user_ID;
        this.userType_ID = userType_ID;
        this.type = type;
    }

    public User(int user_ID, int userType_ID, String username, String password) {
        this.user_ID = user_ID;
        this.userType_ID = userType_ID;
        this.username = username;
        this.password = password;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public int getUserType_ID() {
        return userType_ID;
    }

    public void setUserType_ID(int userType_ID) {
        this.userType_ID = userType_ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Teacher asTeacher() {
        return (Teacher) this;
    }

    public Student asStudent() {
        return (Student) this;
    }
}
