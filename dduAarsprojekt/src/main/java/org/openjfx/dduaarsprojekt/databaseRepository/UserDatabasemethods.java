/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt.databaseRepository;

import org.openjfx.dduaarsprojekt.random.User;
import org.openjfx.dduaarsprojekt.random.Teacher;
import org.openjfx.dduaarsprojekt.random.Student;
import org.openjfx.dduaarsprojekt.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author chris
 */
public class UserDatabasemethods {
    
    private final String connectionString = "jdbc:sqlite:Database.db";

    //---------------------------------------------
    //---------- check for matching user ----------
    //---------------------------------------------
    public boolean cehckForMatchingUser(String _username) throws SQLException, Exception {
        String databaseUsername = "";
        _username = _username.toLowerCase();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        //Class.forName("org.mysql.JDBC.Driver");
        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (check for matching user (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select Username from Users WHERE Username = ('" + _username + "');");

            rs.next();

            databaseUsername = rs.getString("Username");

            rs.close();
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (check for matching user (resultset): " + e.getMessage() + "\n");
        }

        if (_username.equalsIgnoreCase(databaseUsername)) {
            return true;
        }
        
        conn.close();

        return false;
    }

    //--------------------------------------------------
    //---------- check for matching password -----------
    //--------------------------------------------------
    public boolean checkForMatchingPassword(String _username, String _password) throws SQLException, Exception {
        String databasePassword = "";
        _username = _username.toLowerCase();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (check for matching password (connection)): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select Password from Users WHERE Username = ('" + _username + "');");

            rs.next();

            databasePassword = rs.getString("Password");

            rs.close();
            
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (check for matching password (result set)): " + e.getMessage() + "\n");
        }
        
        if (_password.equals(databasePassword)) {
            return true;
        }
        conn.close();
        return false;
    }

    //---------------------------------
    //---------- create user ----------
    //---------------------------------
    public void createUser(User _newUser, String _schoolKey) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        String sql;

        int school_ID = 0;

        _newUser.setUsername(_newUser.getUsername().toLowerCase());

        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skriver fejlhåndtering her
            System.out.println("\n Database error (create user (connection)): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select school_ID from Schools WHERE key = ('" + _schoolKey + "');");

            rs.next();

            school_ID = rs.getInt("school_ID");

            rs.close();
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (create user (result set school_id): " + e.getMessage() + "\n");
        }

        sql = "INSERT INTO Users(school_ID, userType_ID, userName, password, type) "
                + "VALUES('" + school_ID + "','" + _newUser.getUserType_ID() + "', '" + _newUser.getUsername() + "', "
                + "'" + _newUser.getPassword() + "', '" + _newUser.getType() + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (create user (insert user)): " + e.getMessage() + "\n");
        }
        conn.close();
    }

    //------------------------------------
    //---------- create teacher ----------
    //------------------------------------
    public void createTeacher(Teacher _newTeacher) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        String sql;

        int userType_ID = 0;

        _newTeacher.setUsername(_newTeacher.getUsername().toLowerCase());

        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skriver fejlhåndtering her
            System.out.println("\n Database error (create teacher (connection)): " + e.getMessage() + "\n");
        }

        //create teacher first
        sql = "INSERT INTO Teachers(key) VALUES('" + _newTeacher.getKey() + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (create teacher (insert teacher)): " + e.getMessage() + "\n");
        }

        //get teacher id
        try {   
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT MAX(teacher_ID) FROM Teachers;");

            rs.next();

            userType_ID = rs.getInt("MAX(teacher_ID)");
            System.out.println(userType_ID);

            rs.close();
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (create teacher (result set get teacher id)): " + e.getMessage() + "\n");
        }
        
        conn.close();
        
        User newUser = _newTeacher;

        newUser.setUserType_ID(userType_ID);

        createUser(newUser, _newTeacher.getKey());
    }

    //------------------------------------
    //---------- create student ----------
    //------------------------------------
    public void createStudent(Student _newStudent, String key) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        String sql;

        int userType_ID = 0;

        _newStudent.setUsername(_newStudent.getUsername().toLowerCase());

        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skriver fejlhåndtering her
            System.out.println("\n Database error (create student (connection)): " + e.getMessage() + "\n");
        }

        //create student
        sql = "INSERT INTO Students(name, schoolClass) VALUES('" + _newStudent.getName() + "', '" + _newStudent.getSchoolClass() + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (create student (insert student)): " + e.getMessage() + "\n");
        }

        //get student id
        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT MAX(student_ID) FROM Students;");

            rs.next();

            userType_ID = rs.getInt("MAX(student_ID)");

            rs.close();
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (create student (resultset get student ID): " + e.getMessage() + "\n");
        }
        
        conn.close();
        
        User newUser = _newStudent;

        newUser.setUserType_ID(userType_ID);

        createUser(newUser, key);
    }

    //----------------------------------------
    //---------- get logged in user ----------
    //----------------------------------------
    public User getLoggedInUser(String _username) throws SQLException, Exception {
        User loggedInUser = new User();

        _username = _username.toLowerCase();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get logged ind user (connection)): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select* from Users WHERE Username = ('" + _username + "');");

            rs.next();
            if(rs.getString("type").equalsIgnoreCase("teacher")) {
                
                Teacher loggedInTeacher = new Teacher(null, rs.getInt("user_ID"), rs.getInt("userType_ID"), rs.getString("username"), rs.getString("password"));
                
                rs.close();
                
                rs = stat.executeQuery("SELECT key from Teachers WHERE teacher_ID = ('" + loggedInTeacher.getUserType_ID() + "')");
                
                loggedInTeacher.setKey(rs.getString("key"));
                
                loggedInUser = loggedInTeacher;
                
                rs.close();
            } else if(rs.getString("type").equalsIgnoreCase("student")) {
               
                Student loggedInStudent = new Student(null, null, rs.getInt("user_ID"), rs.getInt("userType_ID"), rs.getString("username"), rs.getString("password"));
                
                rs.close();
                
                rs = stat.executeQuery("SELECT * from Students where student_ID = ('" + loggedInStudent.getUserType_ID() + "')");
                
                loggedInStudent.setSchoolClass(rs.getString("schoolClass"));
                loggedInStudent.setName(rs.getString("name"));
                
                loggedInUser = loggedInStudent;
                
                rs.close();
            }
            
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get logged ind user (result set get user)): " + e.getMessage() + "\n");
        }
        
        conn.close();
        
        return loggedInUser;
    }

    //---------------------------------------------------
    //---------- chekc for existing school key ----------
    //---------------------------------------------------
    public boolean checkForExistingKey(String _key) throws SQLException, Exception {
        String databasekey = "";

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (check for matching key (connection)): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select key from Schools WHERE key = ('" + _key + "');");

            rs.next();

            databasekey = rs.getString("key");

            rs.close();
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (check for matching key (result set)): " + e.getMessage() + "\n");
        }

        if (_key.equals(databasekey)) {
            return true;
        }
        
        conn.close();
        
        return false;
    }
}
