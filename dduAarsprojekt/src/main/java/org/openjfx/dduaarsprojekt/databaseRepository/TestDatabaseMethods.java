/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt.databaseRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.openjfx.dduaarsprojekt.*;
import org.openjfx.dduaarsprojekt.TestClasses.*;

/**
 *
 * @author chris
 */
public class TestDatabaseMethods {

    private final String connectionString = "jdbc:sqlite:Database.db";
    
    //----------------------------------------------
    //---------- get all task set answers ----------
    //----------------------------------------------
    public ArrayList<TaskSet> getAllTaskSetsAnswers(int _assignmentID) throws SQLException, Exception {
        ArrayList<TaskSet> taskSets = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        //Class.forName("org.mysql.JDBC.Driver");
        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get all task set answers (connection): " + e.getMessage() + "\n");
        }
        try {
            Statement stat = conn.createStatement();

            //hent information til alle task setne
            ResultSet rs = stat.executeQuery("SELECT * FROM taskSets WHERE assignment_ID=('" + _assignmentID + "') AND WHERE NOT user_ID=('" + App.getLoggedInUser().getUser_ID() + "')");

            while (rs.next()) {

                taskSets.add(new TaskSet(rs.getInt("taskSet_ID"), _assignmentID, rs.getInt("user_ID"), rs.getString("taskSetName"), rs.getString("description"), rs.getString("nameOfTheFiller"), null));
            }

            //lav for loop til at hente alle task
            for (int i = 0; i < taskSets.size(); i++) {
                rs = stat.executeQuery("SELECT * FROM tasks WHERE taskSet_ID=('" + taskSets.get(i).getTaskSet_ID() + "')");
                
                ArrayList<Task> tasks = new ArrayList<>();
                
                //hent alle spørgsmål til task
                while (rs.next()) {
                    Task task = new Task(rs.getInt("task_ID"), null, rs.getString("answer"), rs.getString("teacherComment"));
                    
                    Question question = new Question(rs.getInt("question_ID"), rs.getString("question"));
                    
                    ResultSet _rs;
                    
                    switch (QuestionsType.valueOf(rs.getString("questionType"))) {
                        case correctAnswerBasedQuestion:
                            _rs = stat.executeQuery("SELECT * FROM CorrectAnswerBasedQuestion WHERE correctAnswerBasedQuestion_ID=('" + question.getQuestion_ID() +"')");
                            
                            task.setQuestion(new CorrectAnswerBasedQuestion(rs.getString("coorectAnswer"), question.getQuestion_ID(), question.getQuestion()));
                            break;
                        case multipelChoiseQuestion:
                            _rs = stat.executeQuery("SELECT * FROM MultipelChoiseAnswer WHERE MultipelChoiseQuestion_ID=('" + question.getQuestion_ID() + "')");
                            
                            MultipelChoiseQuestion multipelChoiseQuestion = new MultipelChoiseQuestion(null, question.getQuestion_ID(), question.getQuestion());
                            
                            ArrayList<MultipelChoiseAnswer> multipelChoiseAnswers = new ArrayList<>();
                            
                            while(_rs.next()){
                                multipelChoiseAnswers.add(new MultipelChoiseAnswer(_rs.getInt("multieplChoiseAnswer_ID"), rs.getString("answer"), rs.getInt("correct") == 1 ? true: false));
                            }
                            
                            multipelChoiseQuestion.setAnswerOptions(multipelChoiseAnswers);
                            
                            task.setQuestion(multipelChoiseQuestion);
                            break;
                        case textAnswerBasedQuestion:
                            
                            task.setQuestion(new textAnswerBasedQuestion(question.getQuestion_ID(), question.getQuestion()));
                            break;
                    }
                    
                    tasks.add(task);
                }
            }
            rs.close();

            //lav for loop til at hente alle de forskellige spørgsmål til taskne
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get all task set answers (get data): " + e.getMessage() + "\n");
        }

        return taskSets;
    }
    
    //-------------------------------------
    //---------- create task set ----------
    //-------------------------------------
    public void createTaskSet(TaskSet taskSet) throws SQLException, Exception {
        
        
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        //Class.forName("org.mysql.JDBC.Driver");
        //Skab forbindelse til databasen...
        try {
            Statement stat = conn.createStatement();
            
            //make assignment
            ResultSet rs = stat.executeQuery("INSERT INTO ");
            
            //make taskset
            
            //make tasks
            
                //insertquestions from tasks
            
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (create task set (connection): " + e.getMessage() + "\n");
        }
    }
}


