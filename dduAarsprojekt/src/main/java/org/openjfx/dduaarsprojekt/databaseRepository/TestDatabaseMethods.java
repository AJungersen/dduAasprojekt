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

    public TestDatabaseMethods() {

    }
    private final String connectionString = "jdbc:sqlite:Database.db";

    //------------------------------------------
    //---------- establish connection ----------
    //------------------------------------------
    private Connection establishConnection(String _errorMessages) throws SQLException, Exception {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error ( " + _errorMessages + " (connection): " + e.getMessage() + "\n");
        }

        return conn;
    }

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

    //-------------------------------------
    //---------- insert task set ----------
    //-------------------------------------
    private void inserTaskSet(Connection conn, TaskSet _taskSet) throws SQLException, Exception {

        Statement stat = conn.createStatement();
        ResultSet rs;

        //make taskset
        String sql = "INSERT INTO TaskSets(assignment_ID, user_ID, nameOfTheFiller, taskSetName, description) VALUES ("
                + "'" + _taskSet.getAssignment_ID() + "', '" + _taskSet.getUser_ID() + "', '" + _taskSet.getNameOfTheFiller() + "', "
                + "'" + _taskSet.getName() + "', '" + _taskSet.getDescription() + "',)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (insert task set (insert task set)" + e.getMessage());
        }

        int taskSet_ID = 0;
        try {

            rs = stat.executeQuery("SELECT MAX(taskSet_ID) FROM TaskSets");

            taskSet_ID = rs.getInt("MAX(taskSet_ID)");

        } catch (SQLException e) {
            System.out.println("\n Database error (insert task set (get task set ID)" + e.getMessage());
        }

        //create task
        for (int i = 0; i < _taskSet.getTasks().size(); i++) {

            insertTaskAndQuestion(conn, _taskSet.getTasks().get(i), taskSet_ID);
        }
    }

    //----------------------------------------------
    //---------- insert task and question ----------
    //----------------------------------------------
    private void insertTaskAndQuestion(Connection conn, Task _task, int _taskSet_ID) throws SQLException, Exception {
        int question_ID = 0;
        String sql = "";
        ResultSet rs;
        Statement stat = conn.createStatement();

        //insert questions from tasks
        switch (_task.getQuestion().getType()) {
            case multipelChoiseQuestion:

                sql = "INSERT INTO multipelChoiseQuestion";

                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("\n Database error (insert task set (create multipel choise question (create question)" + e.getMessage());
                }
                try {
                    rs = stat.executeQuery("SELECT MAX(multipelChoiseQuestion_ID) FROM multipelChoiseQuestions");

                    question_ID = rs.getInt("MAX(multipelChoiseQuestion_ID)");

                } catch (SQLException e) {
                    System.out.println("\n Database error (insert task set (create multiepl choice questien ( get question_ID): " + e.getMessage() + "\n");
                }

                MultipelChoiseQuestion question = _task.getQuestion().asMultipelChoiseQuestion();

                for (int u = 0; u < question.getAnswerOptions().size(); u++) {
                    sql = "INSERT INTO multipelChoiseAnswers(multipelChoiseQuestion_ID, answer, correct) VALUES"
                            + "('" + question_ID + "', '" + question.getAnswerOptions().get(u).getAnswer() + "', "
                            + "'" + (question.getAnswerOptions().get(u).getCorrect() ? 1 : 0) + "')";

                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println("\n Database error (insert task set (create multipel choise question (create answers)" + e.getMessage());
                    }
                }
                break;

            case correctAnswerBasedQuestion:

                CorrectAnswerBasedQuestion correctAnswerBasedQuestion = _task.getQuestion().asCorrectAnswerBasedQuestion();

                sql = "INSERT INTOR CorrectAnswerBasedQuestion(correctAnswer) VALUES ('" + correctAnswerBasedQuestion.getCorrectAnswer() + "')";

                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("\n Database error (insert task set (create correct answer based question (create question)" + e.getMessage());
                }

                try {
                    rs = stat.executeQuery("SELECT MAX(correctAnswerBasedQuestion_ID) FROM CorrectAnswerBasedQuestion");

                    question_ID = rs.getInt("MAX(correctAnswerBasedQuestion_ID)");

                } catch (SQLException e) {
                    System.out.println("\n Database error (insert task set (create correct answer based question (get question_ID): " + e.getMessage() + "\n");
                }
                break;

            case textAnswerBasedQuestion:
                //this question dosent havent any extra informaton
                question_ID = 0;
                break;
        }
        //insert task to questions
        sql = "INSERT INTO tasks (taskSet_ID, question_ID, questionType, question) "
                + "VALUES ('" + _taskSet_ID + "', '" + question_ID + "', "
                + "'" + _task.getQuestion().getType().toString() + "', "
                + "'" + _task.getQuestion().getQuestion() + "')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (insert task set (create task)" + e.getMessage());
        }
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

                rs = stat.executeQuery("SELECT * FROM TaskSets WHERE assignment_ID IN"
                        + "(SELECT assignment_ID FROM teamsAndAssignments WHERE team_Id=('" + loadedTeams.get(i).getTeam_ID() + "'))"
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

    //-------------------------------------------------------------
    //---------- get users individual assigned task sets ----------
    //-------------------------------------------------------------
    public ArrayList<TaskSet> getUsersIndividualAssignedTasksSets(int _user_ID) throws SQLException, Exception {
        ArrayList<TaskSet> usersIndividualAssignedTasksSets = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (create task set (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM TaskSets WHERE user_ID= ('" + _user_ID + "')");

            usersIndividualAssignedTasksSets = loadTaskSets(conn, rs);

            rs.close();
        } catch (SQLException e) {
            System.out.println("\n Database error (get users individual task (get info): " + e.getMessage() + "\n");
        }

        conn.close();

        return usersIndividualAssignedTasksSets;
    }

    //----------------------------------------------------------------
    //---------- get students indvidual unassigned task sets ----------
    //----------------------------------------------------------------
    public ArrayList<TaskSet> getStudentsIndividualUnassignedTaskSets(int _student_ID, int _teacherUser_ID) throws SQLException, Exception {
        ArrayList<TaskSet> studentsIndividualUnassignedTaskSets = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get users individual unassigned task sets (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM TaskSets WHERE assignment_ID NOT IN "
                    + "(SELECT assignment_ID FROM studentsAndAssignments WHERE student_ID = ('" + _student_ID + "')) "
                    + "AND user_ID = ('" + _teacherUser_ID + "') ");

            studentsIndividualUnassignedTaskSets = loadTaskSets(conn, rs);

        } catch (SQLException e) {
            System.out.println("\n Database error (get users individual unassigned task sets (get tasksets): " + e.getMessage() + "\n");
        }

        conn.close();

        return studentsIndividualUnassignedTaskSets;
    }

    //--------------------------------------------------------------
    //---------- get students indidual assigned task sets ----------
    //--------------------------------------------------------------
    /*public ArrayList<TaskSet> getStudentsIndividualAssignedTaskSets(int _student_ID, int _teacherUser_ID) throws SQLException, Exception {
        ArrayList<TaskSet> studentsIndividualAssignedTaskSets = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get users individual assigned task sets (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM TaskSets WHERE assignment_ID IN "
                    + "(SELECT assignment_ID FROM studentsAndAssignments WHERE student_ID = ('" + _student_ID + "')) "
                    + "AND user_ID = ('" + _teacherUser_ID + "') ");

            studentsIndividualAssignedTaskSets = loadTaskSets(conn, rs);

        } catch (SQLException e) {
            System.out.println("\n Database error (get users individual assigned task sets (get tasksets): " + e.getMessage() + "\n");
        }

        conn.close();

        return studentsIndividualAssignedTaskSets;
    }*/
    //-------------------------------------
    //---------- create task set ----------
    //-------------------------------------
    public void createTaskSet(TaskSet _taskSet, int _teacher_ID) throws SQLException, Exception {

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
            String sql = "INSERT INTO Assignments (teacher_ID) VALUES('" + _teacher_ID + "')";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (create task set (create assignment)" + e.getMessage());
            }

            //get assignment ID
            ResultSet rs = stat.executeQuery("SELECT MAX(assignment_ID) FROM Assignments");

            int assignmnet_ID = rs.getInt("MAX(assignment_ID)");

            _taskSet.setAssignment_ID(assignmnet_ID);
            _taskSet.setNameOfTheFiller("teacher");

            inserTaskSet(conn, _taskSet);

        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (create task set (insert): " + e.getMessage() + "\n");
        }

        conn.close();
    }

    //-----------------------------------
    //---------- edit task set ----------
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

        Statement stat = conn.createStatement();
        ResultSet rs;

        //update taskset info
        String sql = "UPDATE TaskSets SET taskSetName = '" + _taskSet.getName() + "', description = '" + _taskSet.getDescription() + "' "
                + "WHERE taskSet_ID = ('" + _taskSet.getTaskSet_ID() + "')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (insert task set (insert task set)" + e.getMessage());
        }

        //update task and questions - working
        for (int i = 0; i < _taskSet.getTasks().size(); i++) {
            Task taskToUpdate = _taskSet.getTasks().get(i);

            //check if questien exist
            if (_taskSet.getTasks().get(i).getTask_ID() > -1) {
                //update task info
                sql = "UPDATE task SET question = '" + taskToUpdate.getQuestion() + "' "
                        + "WHERE task_ID = ('" + taskToUpdate.getTask_ID() + "')";

                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("\n Database error (insert task set (insert task)" + e.getMessage());
                }

                // update coresponding questien
                switch (_taskSet.getTasks().get(i).getQuestion().getType()) {
                    case multipelChoiseQuestion:
                        MultipelChoiseQuestion mcq = taskToUpdate.getQuestion().asMultipelChoiseQuestion();

                        ArrayList<Integer> existingAnswer_IDs = new ArrayList<>();

                        try {
                            rs = stat.executeQuery("SELECT multipelChoicesAnswer_ID FROM multipelChoicesAnswers"
                                    + "WHERE multipelChoicesQuestion_ID = ('" + mcq.getQuestion_ID() + "')");

                            while (rs.next()) {
                                existingAnswer_IDs.add(rs.getInt("multipelChoicesAnswers_ID"));
                            }

                        } catch (SQLException e) {
                            System.out.println("\n Database error (insert task set (multipel choice question (get existing answers)" + e.getMessage());
                        }

                        for (int u = 0; u < mcq.getAnswerOptions().size(); u++) {
                            if (existingAnswer_IDs.contains(mcq.getAnswerOptions().get(u).getAnswer_ID())) {

                                sql = "UPDATE multipelChoiceAnswers SET answer = '" + mcq.getAnswerOptions().get(u).getAnswer() + "',"
                                        + "correct = '" + (mcq.getAnswerOptions().get(u).getCorrect() ? 1 : 0) + "' "
                                        + "WHERE multipelChoicesAnswer_ID = ('" + mcq.getAnswerOptions().get(u).getAnswer_ID() + "')";

                                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                                    pstmt.executeUpdate();
                                } catch (SQLException e) {
                                    System.out.println("\n Database error (insert task set (multipel choice question (update answer)" + e.getMessage());
                                }

                            } else {

                                sql = "INSERT INTO multipelChoiseAnswers(multipelChoiseQuestion_ID, answer, correct) VALUES"
                                        + "('" + mcq.getQuestion_ID() + "', '" + mcq.getAnswerOptions().get(u).getAnswer() + "', "
                                        + "'" + (mcq.getAnswerOptions().get(u).getCorrect() ? 1 : 0) + "')";

                                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                                    pstmt.executeUpdate();
                                } catch (SQLException e) {
                                    System.out.println("\n Database error (insert task set (multipel choice question "
                                            + "(create new answer)" + e.getMessage());
                                }
                            }
                        }
                        break;

                    case correctAnswerBasedQuestion:
                        CorrectAnswerBasedQuestion cabq = taskToUpdate.getQuestion().asCorrectAnswerBasedQuestion();

                        sql = "UPDATE correctAnswerBasedQuestions SET correctAnswer = '" + cabq.getCorrectAnswer() + "' "
                                + "WHERE correctAnswerBasedQuestion = ('" + cabq.getQuestion_ID() + "') ";

                        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            pstmt.executeUpdate();
                        } catch (SQLException e) {
                            System.out.println("\n Database error (insert task set (correct answer based question "
                                    + "(update correct answer)" + e.getMessage());
                        }
                        break;

                    case textAnswerBasedQuestion:
                        //there is nothing extra to add
                        break;
                }
            } else {
                //insert new task and question
                insertTaskAndQuestion(conn, taskToUpdate, _taskSet.getTaskSet_ID());
            }
        }
        conn.close();
    }

    //-------------------------------------
    //---------- answer task set ----------
    //-------------------------------------
    public void answerTaskSet(TaskSet _taskSet) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (edit task set (connection): " + e.getMessage() + "\n");
        }

        //update task
        for (int i = 0; i < _taskSet.getTasks().size(); i++) {
            String sql = "UPDATE tasks SET answer = '" + _taskSet.getTasks().get(i).getAnswer() + "' "
                    + "WHERE task_ID = ('" + _taskSet.getTasks().get(i).getTask_ID() + "')";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (answer task set (update answer)" + e.getMessage());
            }
        }
        conn.close();
    }

    //--------------------------------------
    //---------- comment task set ----------
    //--------------------------------------
    public void commentTaskSet(TaskSet _taskSet) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (comment task set (connection): " + e.getMessage() + "\n");
        }

        for (int i = 0; i < _taskSet.getTasks().size(); i++) {
            String sql = "UPDATE tasks SET teacherComment = '" + _taskSet.getTasks().get(i).getComment() + "'"
                    + "WHERE task_ID = ('" + _taskSet.getTasks().get(i).getTask_ID() + "')";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (comment task set (update comment)" + e.getMessage());
            }
        }
        conn.close();
    }

    //-------------------------------------
    //---------- delete task set ----------
    //-------------------------------------
    public void deleteTaskSet(int _taskSet_ID) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get students teams (connection): " + e.getMessage() + "\n");
        }

        String sql = "DELETE FROM TaskSets WHERE taskSet_ID = ('" + _taskSet_ID + "')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (assign taskSet to team (insert assign)" + e.getMessage() + "\n");
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
            rs = stat.executeQuery("SELECT * FROM Teams WHERE teacher_ID = ('" + _teacherID + "')");

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
            System.out.println("\n Database error (create team (connection): " + e.getMessage() + "\n");
        }

        String sql = "INSERT INTO Teams(teacher_ID, teamName) "
                + "VALUES ('" + _team.getTeacher_ID() + "', '" + _team.getTeamName() + "')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (create team (insert info)" + e.getMessage() + "\n");
        }
    }

    //------------------------------------------
    //---------- get schools students ----------
    //------------------------------------------
    public ArrayList<Student> getSchoolsStudents(int _schoolID) throws SQLException, Exception {
        ArrayList<Student> students = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get school students (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM Students WHERE student_ID IN "
                    + "(SELECT userType_ID FROM users WHERE school_ID = ('" + _schoolID + "') AND type = ('" + "student" + "'))");

            students = loadStudents(conn, rs);

            rs.close();

        } catch (SQLException e) {
            System.err.println("\n Database error (get school students (connection): " + e.getMessage() + "\n");
        }

        conn.close();

        return students;
    }

    //--------------------------------------------
    //---------- assign student to team ----------
    //--------------------------------------------
    public void assignStudentToTeam(int _team_ID, Student _student, int _teacher_ID) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (assign to team (connection): " + e.getMessage() + "\n");
        }

        String sql = "INSERT INTO teamsAndstudents(team_ID, student_ID) "
                + "VALUES('" + _team_ID + "', '" + _student.getUserType_ID() + "')";

        try (PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (create team (insert team info)" + e.getMessage() + "\n");
        }

        //get team task sets
        ArrayList<TaskSet> teamsTaskSets = new ArrayList<>();
        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM TaskSets where assignment_ID IN"
                    + "(SELECT assignment_ID from TeamsAndAssignments WHERE team_ID = ('" + _team_ID + "')) "
                    + "AND user_ID = (SELECT user_ID FROM Users WHERE userType_ID = ('" + _teacher_ID + "') "
                    + "AND type = ('" + "teacher" + "'))");

            teamsTaskSets = loadTaskSets(conn, rs);

        } catch (SQLException e) {
            System.out.println("\n Database error (create team (get task sets)" + e.getMessage() + "\n");
        }

        // asign team task sets to student
        for (int i = 0; i < teamsTaskSets.size(); i++) {

            teamsTaskSets.get(i).setUser_ID(_student.getUser_ID());
            teamsTaskSets.get(i).setNameOfTheFiller(_student.getName());

            inserTaskSet(conn, teamsTaskSets.get(i));
        }

        conn.close();
    }

    //---------------------------------------------------
    //---------- assign task set to individual ----------
    //---------------------------------------------------
    public void assignTaskSetToIndividual(TaskSet _taskSet, Student _student, int _teacher_ID) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (assign task set to individual(connection): " + e.getMessage() + "\n");
        }

        //assign the coresponding assignment
        String sql = "INSERT INTO StudentsAndAssignments(student_ID, assignment_ID)"
                + "VALUES('" + _student.getUserType_ID() + "', '" + _taskSet.getAssignment_ID() + "')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (assign taskSet to individual (insert assign)" + e.getMessage() + "\n");
        }

        //assign task set
        inserTaskSet(conn, _taskSet);

        conn.close();
    }

    //---------------------------------------------
    //---------- assign task set to team ----------
    //---------------------------------------------
    public void assignTaskSetToTeam(int _team_ID, TaskSet _taskSet) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (assign task set to team (connection): " + e.getMessage() + "\n");
        }

        //assign task to team
        String sql = "INSERT INTO TeamsAndAssignments(team_ID, assignment_ID) "
                + "VALUES('" + _team_ID + "', '" + _taskSet.getAssignment_ID() + "')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (assign taskSet to team (insert assign)" + e.getMessage() + "\n");
        }

        //assign task to members of the team
        //get user_ID's and name for students
        try {
            Statement stat = conn.createStatement();
            ResultSet rsUsers = stat.executeQuery("SELECT user_ID FROM Users "
                    + "WHERE userType_ID IN (SELECT student_ID FROM teamsAndStudents WHERE team_ID = ('" + _team_ID + "')) "
                    + "AND type = ('" + "student" + "')");

            ResultSet rsStudents = stat.executeQuery("SELECT name FROM Students WHERE student_ID "
                    + "IN (SELECT student_ID FROM teamsAndStudents WHERE team_ID = ('" + _team_ID + "')");

            //assign task
            while (rsUsers.next()) {

                _taskSet.setUser_ID(rsUsers.getInt("user_ID"));

                _taskSet.setNameOfTheFiller(rsStudents.getString("name"));

                inserTaskSet(conn, _taskSet);
            }

        } catch (SQLException e) {
        }

        conn.close();
    }

    //----------------------------------------------------
    //---------- get teams uanssigned task sets ----------
    //----------------------------------------------------
    public ArrayList<TaskSet> getTeamsUnassignedTaskSets(int _team_ID, int _teachersUser_ID) throws SQLException, Exception {
        ArrayList<TaskSet> teamsUnassignedTaskSets = new ArrayList<>();
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get teams unassigned task set (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM taskSets WHERE assignment_ID NOT IN "
                    + "(SELECT assignment_ID FROM teamsAndAssignment WHERE team_ID = ('" + _team_ID + "') "
                    + "AND user_ID = ('" + _teachersUser_ID + "') )");

            teamsUnassignedTaskSets = loadTaskSets(conn, rs);

        } catch (SQLException e) {
            System.out.println("\n Database error (get teams unassigned task set (get task sets): " + e.getMessage() + "\n");
        }

        conn.close();

        return teamsUnassignedTaskSets;
    }

    //---------------------------------------------------
    //---------- get teams assiggned task sets ----------
    //---------------------------------------------------
    public ArrayList<TaskSet> getTeamsAssignedTaskSets(int _team_ID, int _teachersUser_ID) throws SQLException, Exception {
        ArrayList<TaskSet> teamsAssignedTaskSets = new ArrayList<>();
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get teams assigned task set (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM taskSets WHERE assignment_ID IN "
                    + "(SELECT assignment_ID FROM teamsAndAssignment WHERE team_ID = ('" + _team_ID + "')");

            teamsAssignedTaskSets = loadTaskSets(conn, rs);

        } catch (SQLException e) {
            System.out.println("\n Database error (get teams assigned task set (get task sets): " + e.getMessage() + "\n");
        }

        conn.close();

        return teamsAssignedTaskSets;
    }

    //---------------------------------------------------
    //---------- get teams unassigend students ----------
    //---------------------------------------------------
    public ArrayList<Student> getTeamsUnassignedStudents(int _team_ID, int _schoolID) throws SQLException, Exception {
        ArrayList<Student> teamsUnassignedStudents = new ArrayList<>();
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get teams unassigned students (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM Students WHERE student_ID NOT IN "
                    + "(SELECT student_ID FROM teamsAndStudents WHERE team_ID = ('" + _team_ID + "'))"
                    + "AND student_ID IN(SELECT userType_ID FROM users WHERE school_ID = ('" + _schoolID + "') AND type = ('" + "student" + "'))");

            teamsUnassignedStudents = loadStudents(conn, rs);

        } catch (SQLException e) {
            System.out.println("\n Database error (get teams unassigned students (get students): " + e.getMessage() + "\n");
        }

        conn.close();

        return teamsUnassignedStudents;
    }

    //-------------------------------------------------
    //---------- get teams assigned students ----------
    //-------------------------------------------------
    public ArrayList<Student> getTeamsAssignedStudents(int _team_ID) throws SQLException, Exception {
        ArrayList<Student> teamsAssignedStudents = new ArrayList<>();
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get teams assigned students (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM Students WHERE student_ID IN "
                    + "(SELECT student_ID FROM teamsAndStudents WHERE team_ID = ('" + _team_ID + "'))");

            teamsAssignedStudents = loadStudents(conn, rs);

        } catch (SQLException e) {
            System.out.println("\n Database error (get teams assigned students (get students): " + e.getMessage() + "\n");
        }

        conn.close();

        return teamsAssignedStudents;
    }
    /*
    public ArrayList<TaskSet> getAllTeachersTaskSets(int teacherID) {
        ArrayList alltasksets = new ArrayList();
        //placeholder
        // lav rigtig funktion senere
        return alltasksets;
    }*/
}
