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
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import org.openjfx.dduaarsprojekt.App;

public class MainController {
    

    @FXML
    private void mineHold() throws IOException {
        App.setRoot("teamStaff");
    }
    
    @FXML
    private void minePrøver() throws IOException {
        App.setRoot("testStaff");
    }
    
    @FXML
    private void forside() throws IOException{
        App.setRoot("main");
    }
    
    @FXML
    private void logud() throws IOException{
        App.setRoot("login");
    }
}
