/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

/**
 *
 * @author clara
 */
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.openjfx.dduaarsprojekt.databaseRepository.TestDatabaseMethods;
import org.openjfx.dduaarsprojekt.random.Team;

public class MainStudentController implements Initializable {
    @FXML ListView<String> myTeams;
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
       TestDatabaseMethods tdb = new TestDatabaseMethods();
       try {
            myTeams.getItems().addAll(getNames(tdb.getTeachersTeams(App.getLoggedInUser().getUser_ID())));
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    private void exit() {
        System.exit(0);
    }
    @FXML
    private void tests() throws IOException{
       App.setRoot("testStudent");
    }
    @FXML
    private void back() throws IOException{
        App.setRoot("mainStudent");
    }
    @FXML
    private void logout() throws IOException{
        App.setRoot("frontPage");
    }
    @FXML
    private void teams() throws IOException{
        App.setRoot("teamStudent");
    }
    private ArrayList<String> getNames(ArrayList<Team> teacherTeams) {
        ArrayList<String> myNames = new ArrayList();
        for(int i = 0; i < teacherTeams.size(); i++){
            myNames.add(teacherTeams.get(i).getTeamName());
        }
        return myNames;
    }
}
