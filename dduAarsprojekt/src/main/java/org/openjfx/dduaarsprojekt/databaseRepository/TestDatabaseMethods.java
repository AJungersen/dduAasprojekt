/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt.databaseRepository;

import java.util.ArrayList;
import org.openjfx.dduaarsprojekt.*;
import org.openjfx.dduaarsprojekt.TestClasses.*;

/**
 *
 * @author chris
 */
public class TestDatabaseMethods {
    private final String connectionString = "jdbc:sqlite:Database.db";
    
    public static ArrayList<Test> getAllTestAnswers(){
        ArrayList<Test> test = new ArrayList<>();
        //tests hentes fra database og indsættes i arraylisten test
        return test;
    }
}
