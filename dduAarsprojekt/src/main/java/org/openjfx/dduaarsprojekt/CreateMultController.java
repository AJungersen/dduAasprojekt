/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import org.openjfx.dduaarsprojekt.TestClasses.MultipelChoiseAnswer;
import org.openjfx.dduaarsprojekt.TestClasses.MultipelChoiseQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.dduaarsprojekt.TestClasses.Task;


/**
 * FXML Controller class
 *
 * @author danie
 */
public class CreateMultController implements Initializable {
    @FXML TextField question;
    @FXML Button saveQuestion;
    @FXML  Text questionNumber;  
    @FXML  TextField AnswerQ;
    @FXML  TextField AnswerR;
    @FXML  TextField firstQ;
    @FXML  TextField secondQ;
    @FXML  TextField thridQ;
    @FXML  TextField fourthQ;
    @FXML  TextField fifthQ;
    @FXML  TextField sixthQ;
    @FXML  CheckBox firstR;
    @FXML  CheckBox secondR;
    @FXML  CheckBox thridR;
    @FXML  CheckBox fourthR;
    @FXML  CheckBox fifthR;
    @FXML  CheckBox sixthR;
    public int choiceCounter;
       
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choiceCounter = 0;
    }    
    @FXML
      private void addAnswer () {
          System.out.println("tilføj svarmulighed");
          choiceCounter++;
          
        switch(choiceCounter){
          case 1:
              firstR.setVisible(true);  
              firstQ.setVisible(true);
              break;
          case 2:
              secondR.setVisible(true);
              secondQ.setVisible(true);
              break;
          case 3:
              thridR.setVisible(true);
              thridQ.setVisible(true);
              break;
          case 4:
              fourthR.setVisible(true);
              fourthQ.setVisible(true);
              break;
          case 5:
              fifthR.setVisible(true);
              fifthQ.setVisible(true);
              break;
          case 6:
              sixthR.setVisible(true);
              sixthQ.setVisible(true);
              break;
          default:
              firstR.setVisible(false);  
              firstQ.setVisible(false);
              secondR.setVisible(false);
              secondQ.setVisible(false);
              thridR.setVisible(false);
              thridQ.setVisible(false);
              fourthR.setVisible(false);
              fourthQ.setVisible(false);
              fifthR.setVisible(false);
              fifthQ.setVisible(false);
              sixthR.setVisible(false);
              sixthQ.setVisible(false);              
          }
      }    
    
    @FXML
    public void save() throws IOException{
        ArrayList<MultipelChoiseAnswer> answers = new ArrayList<>();
        answers.add(MultipelChoiseAnswer(firstQ.getText(),firstR.pressedProperty()));
        answers.add(MultipelChoiseAnswer(secondQ.getText(),secondR.pressedProperty()));
        answers.add(MultipelChoiseAnswer(thridQ.getText(),thridR.pressedProperty()));
        answers.add(MultipelChoiseAnswer(fourthQ.getText(),fourthR.pressedProperty()));
        answers.add(MultipelChoiseAnswer(fifthQ.getText(),fifthR.pressedProperty()));
        answers.add(MultipelChoiseAnswer(sixthQ.getText(),sixthR.pressedProperty()));
        
        TestMakerController.tasks.add(new Task(new MultipelChoiseQuestion(answers, question.getText()), null, null));
        App.setRoot("testMaker");
    }

    private MultipelChoiseAnswer MultipelChoiseAnswer(String text, ReadOnlyBooleanProperty pressedProperty) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
