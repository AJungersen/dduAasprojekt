/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Clara Maj
 */
package org.openjfx.dduaarsprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.openjfx.dduaarsprojekt.databaseRepository.TestDatabaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class MainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void exit() {
        System.exit(0);
    }
    @FXML
    private void tests() throws IOException{
       App.setRoot("testStaff");
    }
    @FXML
    private void back() throws IOException{
        App.setRoot("mainStaff");
    }
    @FXML
    private void logout() throws IOException{
        App.setRoot("frontPage");
    }
    @FXML
    private void teams() throws IOException{
        App.setRoot("teamStaff");
    }
     
    
}
