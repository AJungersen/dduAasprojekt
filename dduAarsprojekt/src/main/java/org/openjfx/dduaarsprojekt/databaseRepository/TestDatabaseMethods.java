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
import org.openjfx.dduaarsprojekt.random.*;
import org.openjfx.dduaarsprojekt.TestClasses.*;

/**
 *
 * @author chris
 */
public class TestDatabaseMethods {
    
    public TestDatabaseMethods(){
        
    }
    private final String connectionString = "jdbc:sqlite:Database.db";

    //-------------------------------------------------
    //---------- load user from usertype id -----------
    //-------------------------------------------------
    
    //-----------------------------------
    //---------- load students ----------
    //-----------------------------------
    private ArrayList<Student> loadStudents(ResultSet rs) throws SQLException, Exception {
        ArrayList<Student> loadedeStudents = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (load students (connection): " + e.getMessage() + "\n");
        }

        Statement stat = conn.createStatement();

        try {
            while (rs.next()) {
                loadedeStudents.add(new Student(rs.getString("schoolClass"), rs.getString("name"), 0, rs.getInt("student_ID"), null, null));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (load students (load student info): " + e.getMessage() + "\n");
        }

        for (int i = 0; i < loadedeStudents.size(); i++) {

        }

        return loadedeStudents;
    }

    //----------------------------------
    //---------- load teacher ----------
    //----------------------------------
    
    //-----------------------------------
    //---------- load task set ----------
    //-----------------------------------
    private ArrayList<TaskSet> loadTaskSets(ResultSet rs, int _assignmentID) throws SQLException, Exception {
        ArrayList<TaskSet> loadedTaskSets = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (load task sets (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            while (rs.next()) {
                loadedTaskSets.add(new TaskSet(rs.getInt("taskSet_ID"), _assignmentID, rs.getInt("user_ID"), rs.getString("taskSetName"), rs.getString("description"), rs.getString("nameOfTheFiller"), (rs.getInt("handedIn") == 1 ? true : false), null));
            }

            //lav for loop til at hente alle task
            for (int i = 0; i < loadedTaskSets.size(); i++) {
                rs = stat.executeQuery("SELECT * FROM tasks WHERE taskSet_ID=('" + loadedTaskSets.get(i).getTaskSet_ID() + "')");

                ArrayList<Task> tasks = new ArrayList<>();

                //hent alle spørgsmål til task
                while (rs.next()) {
                    Task task = new Task(rs.getInt("task_ID"), null, rs.getString("answer"), rs.getString("teacherComment"));

                    Question question = new Question(rs.getInt("question_ID"), rs.getString("question"));

                    ResultSet _rs;

                    switch (QuestionsType.valueOf(rs.getString("questionType"))) {
                        case correctAnswerBasedQuestion:
                            _rs = stat.executeQuery("SELECT * FROM CorrectAnswerBasedQuestion WHERE correctAnswerBasedQuestion_ID=('" + question.getQuestion_ID() + "')");

                            task.setQuestion(new CorrectAnswerBasedQuestion(_rs.getString("coorectAnswer"), question.getQuestion_ID(), question.getQuestion()));
                            _rs.close();
                            break;
                        case multipelChoiseQuestion:
                            _rs = stat.executeQuery("SELECT * FROM MultipelChoiseAnswer WHERE MultipelChoiseQuestion_ID=('" + question.getQuestion_ID() + "')");

                            MultipelChoiseQuestion multipelChoiseQuestion = new MultipelChoiseQuestion(null, question.getQuestion_ID(), question.getQuestion());

                            ArrayList<MultipelChoiseAnswer> multipelChoiseAnswers = new ArrayList<>();

                            while (_rs.next()) {
                                multipelChoiseAnswers.add(new MultipelChoiseAnswer(_rs.getInt("multieplChoiseAnswer_ID"), _rs.getString("answer"), (_rs.getInt("correct") == 1 ? true : false)));
                            }

                            multipelChoiseQuestion.setAnswerOptions(multipelChoiseAnswers);

                            task.setQuestion(multipelChoiseQuestion);
                            _rs.close();
                            break;
                        case textAnswerBasedQuestion:

                            task.setQuestion(new textAnswerBasedQuestion(question.getQuestion_ID(), question.getQuestion()));
                            break;
                    }

                    tasks.add(task);
                }
                loadedTaskSets.get(i).setTasks(tasks);
            }

            rs.close();

        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (load task sets (load from resultset): " + e.getMessage() + "\n");
        }

        return loadedTaskSets;
    }

    //-------------------------------
    //---------- load team ----------
    //-------------------------------
    private ArrayList<Team> loadTeams(ResultSet rs) throws SQLException, Exception {
        ArrayList<Team> loadedTeams = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (load teams (connection): " + e.getMessage() + "\n");
        }

        Statement stat = conn.createStatement();

        try {
            while (rs.next()) {
                loadedTeams.add(new Team(rs.getInt("team_ID"), rs.getString("teamName"), null, null));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (load teams (load team info): " + e.getMessage() + "\n");
        }

        try {
            for (int i = 0; i < loadedTeams.size(); i++) {
                rs = stat.executeQuery("SELECT * FROM Students WHERE student_ID IN"
                        + "(SELECT student_ID FROM TeamsAndStudents WHERE team_ID = ('" + loadedTeams.get(i).getTeam_ID() + "'))");

                ArrayList<Student> loadedTeamsStudents = new ArrayList<>();

                loadedTeams.get(i).setStudents(loadedTeamsStudents);
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (load teams (load students): " + e.getMessage() + "\n");
        }

        return loadedTeams;
    }

    //--------------------------------------------------------------------
    //------------------------------ public ------------------------------
    //--------------------------------------------------------------------
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

            loadTaskSets(rs, _assignmentID);

            rs.close();

        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get all task set answers (get data): " + e.getMessage() + "\n");
        }

        return taskSets;
    }

    //-------------------------------------
    //---------- create task set ----------
    //-------------------------------------
    public void createTaskSet(TaskSet _taskSet) throws SQLException, Exception {

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        //Class.forName("org.mysql.JDBC.Driver");
        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (create task set (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            //make assignment
            String sql = "INSERT INTO Assignments (teacher_ID) VALUES('" + App.getLoggedInUser().getUserType_ID() + "')";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (create task set (create assignment)" + e.getMessage());
            }

            //crate assignment ID
            ResultSet rs = stat.executeQuery("SELECT MAX(assignment_ID) FROM Assignments");

            int assignmnet_ID = rs.getInt("assignment_ID");

            //make taskset
            sql = "INSERT INTO TaskSets(assignment_ID, user_ID, nameOfTheFiller, taskSetName, description) VALUES ("
                    + "'" + assignmnet_ID + "', '" + _taskSet.getUser_ID() + "', '" + _taskSet.getNameOfTheFiller() + "', "
                    + "'" + _taskSet.getNameOfTheFiller() + "', '" + _taskSet.getDescription() + "',)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (create task set (create task set)" + e.getMessage());
            }

            rs = stat.executeQuery("SELECT MAX(taskSet_ID) FROM TaskSets");

            int taskSet_ID = rs.getInt("taskSet_ID");

            //create task
            for (int i = 0; i < _taskSet.getTasks().size(); i++) {

                int question_ID = 0;

                //insert questions from tasks
                switch (_taskSet.getTasks().get(i).getQuestion().getType()) {
                    case multipelChoiseQuestion:

                        sql = "INSERT INTO multipelChoiseQuestion";

                        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            pstmt.executeUpdate();
                        } catch (SQLException e) {
                            System.out.println("\n Database error (create multipel choise question (create question)" + e.getMessage());
                        }
                        try {
                            rs = stat.executeQuery("SELECT MAX(multipelChoiseQuestion_ID) FROM multipelChoiseQuestions");

                            question_ID = rs.getInt("multipelChoiseQuestion_ID");

                        } catch (SQLException e) {
                            System.out.println("\n Database error (create multiepl choice questien ( get question_ID): " + e.getMessage() + "\n");
                        }

                        MultipelChoiseQuestion question = _taskSet.getTasks().get(i).getQuestion().asMultipelChoiseQuestion();

                        for (int u = 0; u < question.getAnswerOptions().size(); u++) {
                            sql = "INSERT INTO multipelChoiseAnswers(multipelChoiseQuestion_ID, answer, correct) VALUES"
                                    + "('" + question_ID + "', '" + question.getAnswerOptions().get(i).getAnswer() + "', "
                                    + "'" + (question.getAnswerOptions().get(i).getCorrect() ? 1 : 0) + "')";

                            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                                pstmt.executeUpdate();
                            } catch (SQLException e) {
                                System.out.println("\n Database error (create multipel choise question (create answers)" + e.getMessage());
                            }
                        }
                        break;

                    case correctAnswerBasedQuestion:

                        CorrectAnswerBasedQuestion correctAnswerBasedQuestion = _taskSet.getTasks().get(i).getQuestion().asCorrectAnswerBasedQuestion();

                        sql = "INSERT INTOR CorrectAnswerBasedQuestion(correctAnswer) VALUES ('" + correctAnswerBasedQuestion.getCorrectAnswer() + "')";

                        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            pstmt.executeUpdate();
                        } catch (SQLException e) {
                            System.out.println("\n Database error (create correct answer based question (create question)" + e.getMessage());
                        }

                        try {
                            rs = stat.executeQuery("SELECT MAX(correctAnswerBasedQuestion_ID) FROM CorrectAnswerBasedQuestion");

                            question_ID = rs.getInt("correctAnswerBasedQuestion_ID");

                        } catch (SQLException e) {
                            System.out.println("\n Database error (create correct answer based question (get question_ID): " + e.getMessage() + "\n");
                        }
                        break;

                    case textAnswerBasedQuestion:
                        //this question dosent havent any extra informaton
                        question_ID = 0;
                        break;
                }
                //insert task to questions
                sql = "INSERT INTO tasks (taskSet_ID, question_ID, questionType, question) "
                        + "VALUES ('" + taskSet_ID + "', '" + question_ID + "', "
                        + "'" + _taskSet.getTasks().get(i).getQuestion().getType() + "', "
                        + "'" + _taskSet.getTasks().get(i).getQuestion().getQuestion() + "')";

                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("\n Database error (create task)" + e.getMessage());
                }
            }

        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (create task set (insert): " + e.getMessage() + "\n");
        }
    }

    //-----------------------------------------
    //---------- get users task sets ----------
    //-----------------------------------------
    public ArrayList<Task> getUsersTasksSets() throws SQLException, Exception {
        ArrayList<Task> usersTasksSets = new ArrayList<>();

        return usersTasksSets;
    }

    //-----------------------------------------
    //---------- get teams task sets ----------
    //-----------------------------------------
    public ArrayList<Task> getTeamsTaskSets() throws SQLException, Exception {
        ArrayList<Task> teamTaskSets = new ArrayList<>();

        return teamTaskSets;
    }

    //------------------------------------------------
    //---------- get students teams ------------------
    //------------------------------------------------
    public ArrayList<Team> getStudentsTeams(int _studentID) throws SQLException, Exception {
        ArrayList<Team> studentsTeams = new ArrayList<>();

        return studentsTeams;
    }

    //----------------------------------------
    //---------- get teachers teams ----------
    //----------------------------------------
    public ArrayList<Team> getTeachersTeams(int _teacherID) throws SQLException, Exception {
        ArrayList<Team> teachersTeams = new ArrayList<>();

        return teachersTeams;
    }

    //------------------------------------------
    //---------- get schools students ----------
    //------------------------------------------
    public ArrayList<Student> getSchoolsStudents(int _schoolID) throws SQLException, Exception {
        ArrayList<Student> students = new ArrayList<>();

        return students;
    }

    public static ArrayList<TaskSet> getAllTaskSets() {
        ArrayList alltasksets = new ArrayList();
        //placeholder
        // lav rigtig funktion senere
        return alltasksets;
    }
}
