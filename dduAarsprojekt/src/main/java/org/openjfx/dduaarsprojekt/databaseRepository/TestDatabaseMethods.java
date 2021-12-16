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

    private final String connectionString = "jdbc:sqlite:Database.db";

    //--------------------------------
    //---------- load user -----------
    //--------------------------------
    private User loadUser(Connection conn, int _userID) throws SQLException, Exception {
        User loadedUser = new User();

        Class.forName("org.sqlite.JDBC");

        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM users WHERE user_ID=('" + _userID + "')");

            loadedUser = new User(_userID, rs.getInt("userType_ID"), rs.getString("username"), rs.getString("password"));

        } catch (SQLException e) {
            System.out.println("\n Database error (load user (load info): " + e.getMessage() + "\n");
        }

        return loadedUser;
    }

    //-----------------------------------
    //---------- load students ----------
    //-----------------------------------
    private ArrayList<Student> loadStudents(Connection conn, ResultSet rs) throws SQLException, Exception {
        ArrayList<Student> loadedeStudents = new ArrayList<>();

        Class.forName("org.sqlite.JDBC");

        Statement stat = conn.createStatement();

        try {
            while (rs.next()) {
                loadedeStudents.add(new Student(rs.getString("schoolClass"), rs.getString("name"), 0, rs.getInt("student_ID"), null, null));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (load students (load student info): " + e.getMessage() + "\n");
        }
        try {

            for (int i = 0; i < loadedeStudents.size(); i++) {
                rs = stat.executeQuery("SELECT user_ID FROM Users WHERE userType_ID = "
                        + "('" + loadedeStudents.get(i).getUserType_ID() + "') "
                        + "AND type = ('" + "student" + "')");

                User loadeUser = loadUser(conn, rs.getInt("user_ID"));

                loadedeStudents.get(i).setUser_ID(loadeUser.getUser_ID());
                loadedeStudents.get(i).setUsername(loadeUser.getUsername());
                loadedeStudents.get(i).setPassword(loadeUser.getPassword());
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (load students (get user id): " + e.getMessage() + "\n");
        }

        return loadedeStudents;
    }

    //----------------------------------
    //---------- load teacher ----------
    //----------------------------------
    //-----------------------------------
    //---------- load task set ----------
    //-----------------------------------
    private ArrayList<TaskSet> loadTaskSets(Connection conn, ResultSet rs) throws SQLException, Exception {
        ArrayList<TaskSet> loadedTaskSets = new ArrayList<>();

        Class.forName("org.sqlite.JDBC");

        try {
            Statement stat = conn.createStatement();

            while (rs.next()) {
                loadedTaskSets.add(new TaskSet(rs.getInt("taskSet_ID"), rs.getInt("assignment_ID"), rs.getInt("user_ID"), rs.getString("taskSetName"), rs.getString("description"), rs.getString("nameOfTheFiller"), (rs.getInt("handedIn") == 1 ? true : false), null));
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
    private ArrayList<Team> loadTeams(Connection conn, ResultSet rs, int _userID) throws SQLException, Exception {
        ArrayList<Team> loadedTeams = new ArrayList<>();

        Class.forName("org.sqlite.JDBC");

        Statement stat = conn.createStatement();

        try {
            while (rs.next()) {
                loadedTeams.add(new Team(rs.getInt("team_ID"), rs.getInt("teacher_ID"), rs.getString("teamName"), null, null));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (load teams (load team info): " + e.getMessage() + "\n");
        }

        try {
            for (int i = 0; i < loadedTeams.size(); i++) {
                rs = stat.executeQuery("SELECT * FROM Students WHERE student_ID IN"
                        + "(SELECT student_ID FROM TeamsAndStudents WHERE team_ID = ('" + loadedTeams.get(i).getTeam_ID() + "'))");

                loadedTeams.get(i).setStudents(loadStudents(conn, rs));

                rs = stat.executeQuery("SELECT * taskSets WHERE assignment_ID IN"
                        + "(SELECT assignmetn_ID FROM teamasAndAssignments WHERE team_Id=('" + loadedTeams.get(i).getTeam_ID() + "'))"
                        + "AND user_ID = ('" + _userID + "')");

                loadedTeams.get(i).setTaskSet(loadTaskSets(conn, rs));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (load teams (load students): " + e.getMessage() + "\n");
        }

        return loadedTeams;
    }

    //--------------------------------------------------------------------
    //--------------------------------------------------------------------
    //------------------------------ public ------------------------------
    //--------------------------------------------------------------------
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

            loadTaskSets(conn, rs);

            rs.close();

        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get all task set answers (get data): " + e.getMessage() + "\n");
        }

        conn.close();

        return taskSets;
    }

    //----------------------------------------------------
    //---------- get users individual task sets ----------
    //----------------------------------------------------
    public ArrayList<TaskSet> getUsersIndividualTasksSets(int _user_ID) throws SQLException, Exception {
        ArrayList<TaskSet> usersIndividualTasksSets = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get users individual task (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM TaskSets WHERE user_ID= ('" + _user_ID + "')");

            usersIndividualTasksSets = loadTaskSets(conn, rs);

            rs.close();
        } catch (SQLException e) {
            System.out.println("\n Database error (get users individual task (get info): " + e.getMessage() + "\n");
        }

        conn.close();

        return usersIndividualTasksSets;
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

        conn.close();
    }

    //-----------------------------------
    //---------- edit task set ---------- needs work
    //-----------------------------------
    public void editTaskSet(TaskSet _taskSet) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (edit task set (connection): " + e.getMessage() + "\n");
        }
        
        conn.close();
    }

    //-------------------------------------
    //---------- delete task set ---------- needs work
    //-------------------------------------
    public void deleteTaskSet(int _taskSet_ID) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (delete task set (connection): " + e.getMessage() + "\n");
        }
        
        conn.close();
    }

    //------------------------------------------------
    //---------- get students teams ------------------
    //------------------------------------------------
    public ArrayList<Team> getStudentsTeams(int _studentID) throws SQLException, Exception {
        ArrayList<Team> studentsTeams = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get students teams (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            //hent user id
            ResultSet rs = stat.executeQuery("SELECT user_ID FROM Users WHERE userType_ID = "
                    + "('" + _studentID + "') "
                    + "AND type = ('" + "student" + "')");

            int user_ID = rs.getInt("user_ID");

            //hent result set of teams
            rs = stat.executeQuery("SELECT * FROM Teams WHERE team_ID IN"
                    + "(SELECT team_ID FROM teamsAndStudents WHERE student_ID =('" + _studentID + "'))");

            studentsTeams = loadTeams(conn, rs, user_ID);

        } catch (SQLException e) {
            System.out.println("\n Database error (get students teams (get info): " + e.getMessage() + "\n");
        }

        conn.close();

        return studentsTeams;
    }

    //----------------------------------------
    //---------- get teachers teams ----------
    //----------------------------------------
    public ArrayList<Team> getTeachersTeams(int _teacherID) throws SQLException, Exception {
        ArrayList<Team> teachersTeams = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get teachers teams (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            //hent user id
            ResultSet rs = stat.executeQuery("SELECT user_ID FROM Users WHERE userType_ID = "
                    + "('" + _teacherID + "') "
                    + "AND type = ('" + "teacher" + "')");

            int user_ID = rs.getInt("user_ID");

            //hent result set of teams
            rs = stat.executeQuery("SELECT * FROM Teams WHERE teacher_ID = ('" + _teacherID + "'))");

            teachersTeams = loadTeams(conn, rs, user_ID);

        } catch (SQLException e) {
            System.out.println("\n Database error (get teachers teams (get info): " + e.getMessage() + "\n");
        }

        conn.close();

        return teachersTeams;
    }

    //---------------------------------
    //---------- create team ----------
    //---------------------------------
    public void createTeam(Team _team) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (create task set (connection): " + e.getMessage() + "\n");
        }

        String sql = "INSERT INTO Teams(teacher_ID, teamName) "
                + "VALUES ('" + _team.getTeacher_ID() + "', '" + _team.getTeamName() + "')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (create team (insert info)" + e.getMessage());
        }
        
        conn.close();
    }

    //------------------------------------------
    //---------- get schools students ---------- needs work
    //------------------------------------------
    public ArrayList<Student> getSchoolsStudents(int _schoolID) throws SQLException, Exception {
        ArrayList<Student> students = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (create task set (connection): " + e.getMessage() + "\n");
        }
        
        conn.close();
        
        return students;
    }

    //------------------------------------
    //---------- assign to team ---------- needs work
    //------------------------------------
    public void assignToTeam() throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (assign to team (connection): " + e.getMessage() + "\n");
        }
        
        conn.close();
    }

    //---------------------------------------------------
    //---------- assign individual to task set ---------- needs work
    //---------------------------------------------------
    public void assignIndividualToTaskSet() throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (assign individual to task set (connection): " + e.getMessage() + "\n");
        }
        
        conn.close();
    }

    public static ArrayList<TaskSet> getAllTaskSets() {
        ArrayList alltasksets = new ArrayList();
        //placeholder
        // lav rigtig funktion senere
        return alltasksets;
    }
}
