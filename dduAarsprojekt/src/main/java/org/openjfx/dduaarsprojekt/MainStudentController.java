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
import javafx.fxml.FXML;

public class MainStudentController {
    
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
}
