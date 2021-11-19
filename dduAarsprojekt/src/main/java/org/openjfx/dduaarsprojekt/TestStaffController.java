/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import java.io.IOException;
import javafx.fxml.FXML;
/**
 *
 * @author danie
 */
public class TestStaffController {
    @FXML
    private void mineHold() throws IOException {
        App.setRoot("mineHold");
    }
    
    @FXML
    private void minePrøver() throws IOException {
        App.setRoot("minePrøver");
    }
}
