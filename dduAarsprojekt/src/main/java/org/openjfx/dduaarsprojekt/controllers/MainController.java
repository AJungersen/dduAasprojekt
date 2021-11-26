/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Clara Maj
 */
package org.openjfx.dduaarsprojekt.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import org.openjfx.dduaarsprojekt.App;

public class MainController {

    @FXML
    private void mineHold() throws IOException {
        App.setRoot("mineHold");
    }
    
    @FXML
    private void minePrøver() throws IOException {
        App.setRoot("minePrøver");
    }
}
