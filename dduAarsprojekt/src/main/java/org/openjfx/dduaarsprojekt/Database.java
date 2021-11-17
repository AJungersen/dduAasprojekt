/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author danie
 */
public class Database {

    private final String connectionString = "jdbc:sqlite:src/Database.db";
   
    public boolean login(String username, String password){
        return true;
    }
}
