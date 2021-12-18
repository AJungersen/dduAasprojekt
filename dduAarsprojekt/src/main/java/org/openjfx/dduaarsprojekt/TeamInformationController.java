/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author clara
 */
public class TeamInformationController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
      @FXML
    private void back() throws IOException{
        App.setRoot("main");
    }
    @FXML
    private void logout() throws IOException{
        App.setRoot("frontPage");
    }
    @FXML
    private void tests() throws IOException{
        App.setRoot("testStaff");
    }
    @FXML
    private void teams() throws IOException{
        App.setRoot("teamStaff");
    }
    @FXML
    private void exit() {
        System.exit(0);
    }
    @FXML
    private void create() throws IOException {
        App.setRoot("createTest");
    }
    @FXML
    private void info () throws IOException{
        App.setRoot("teamInformation");
    }
}
