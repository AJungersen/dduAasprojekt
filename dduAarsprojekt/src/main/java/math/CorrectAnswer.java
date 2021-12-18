/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import java.util.ArrayList;
import org.openjfx.dduaarsprojekt.TestClasses.Task;
import org.openjfx.dduaarsprojekt.TestClasses.TaskSet;
import org.openjfx.dduaarsprojekt.databaseRepository.TestDatabaseMethods;

/**
 *
 * @author danie
 */
public class CorrectAnswer {
    static public boolean testAnswer(String guess, int taskID) throws Exception{
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        ArrayList<TaskSet> tasksets = new ArrayList();
        Task myTask = new Task();
        tasksets.addAll(tdb.getUsersIndividualTasksSets());
        
    }
}
