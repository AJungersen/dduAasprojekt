package org.openjfx.dduaarsprojekt;

import org.openjfx.dduaarsprojekt.random.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Branch test
 */
public class App extends Application {
    private static User loggedInUser = new User();
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public static void setLoggedInUser(User _loggedInUser) {
        loggedInUser = _loggedInUser;
    }
    
    public static User getLoggedInUser() {
        return loggedInUser;
    }

}