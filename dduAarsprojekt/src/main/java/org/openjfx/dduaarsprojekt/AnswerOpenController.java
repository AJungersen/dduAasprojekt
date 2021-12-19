/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 *
 * @author clara
 */
public class AnswerOpenController {
    @FXML
    public static Label testName;

    public static void setTestName(String testName) {
        AnswerOpenController.testName.setAccessibleHelp(testName);
    }
    
    
}
