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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class TestMakerController implements Initializable {
   
    @FXML
    ListView questionList;
    TextArea description;
    TextField testName;
    ComboBox options;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    public void createMultQuestion() throws IOException{
        App.setRoot("createMult");
    }
    
    @FXML
    public void createAnswerbasedQuestion() throws IOException{
        App.setRoot("createAnswerbased");
    }
    
    @FXML
    public void savetaskSet(){
        
    }
}
