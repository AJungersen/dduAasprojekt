/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt.controllers;

import org.openjfx.dduaarsprojekt.databaseRepository.UserDatabasemethods;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.openjfx.dduaarsprojekt.App;
/**
 *
 * @author danie
 */
public class TestStaffController implements Initializable{
    @FXML
    TableView doneTest;
    TableView pendingTests;
    TableColumn doneName;
    TableColumn doneCorrect;
    TableColumn doneParticipation;
    TableColumn pendingName;
    TableColumn pendingCorrect;
    TableColumn pendingParticipation;
    
    
   @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //ArrayList<Test> tests = new ArrayList<>();
        //tests = UserDatabasemethods.getAllTests();
    }
    
    @FXML
    private void mineHold() throws IOException {
        App.setRoot("mineHold");
    }
    
    @FXML
    private void minePrøver() throws IOException {
        App.setRoot("minePrøver");
    }

    
}
