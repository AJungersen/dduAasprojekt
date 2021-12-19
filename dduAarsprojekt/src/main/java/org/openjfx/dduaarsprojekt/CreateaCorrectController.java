/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.dduaarsprojekt.TestClasses.CorrectAnswerBasedQuestion;

/**
 *
 * @author clara
 */
public class CreateaCorrectController {
    @FXML private Text questionNumber;
    @FXML TextField question;
    @FXML TextField answer;


@FXML
public void save() {
    String q = question.getText();
    String c = answer.getText();
    TestMakerController.tasks.add(new CorrectAnswerBasedQuestion(c,q));
    }

}
