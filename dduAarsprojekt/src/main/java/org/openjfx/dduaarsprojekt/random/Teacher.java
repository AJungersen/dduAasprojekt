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
public class Teacher extends User {
private String key;

    public Teacher(String key, int user_ID, int userType_ID, String username, String password) {
        super(user_ID, userType_ID, username, password);
        this.key = key;
        
        setType("teacher");
    }

    

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
