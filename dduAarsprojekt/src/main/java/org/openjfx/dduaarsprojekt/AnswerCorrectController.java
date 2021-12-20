/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import org.openjfx.dduaarsprojekt.TestClasses.QuestionsType;

/**
 *
 * @author clara
 */
public class AnswerCorrectController implements Initializable {
   @FXML private Text questionNumber;
    @FXML Text Question;
    @FXML TextArea Answer;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
                         
        for(int i = 0; i < TestMakerController.tasks.size(); i++){
            if(AnswerTestController.ts.getTasks().get(i).getQuestion().getType().equals(QuestionsType.correctAnswerBasedQuestion)){
                Question.setText(TestMakerController.tasks.get(i).getQuestion().getQuestion());
            }
        }
        
    }
    
    @FXML
    public void saveAnswer(){
        String a = Answer.getText();
        AnswerTestController.ts.getTasks().get(AnswerTestController.nrQuest).setAnswer(a);
        AnswerTestController.nrQuest++;
    } 
}
