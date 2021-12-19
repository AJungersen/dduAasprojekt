package org.openjfx.dduaarsprojekt;

import org.openjfx.dduaarsprojekt.random.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;
import java.io.IOException;
import org.openjfx.dduaarsprojekt.TestClasses.TaskSet;

/**
 * Branch test
 */
public class App extends Application {
    private static User loggedInUser = new User();
    private static Scene scene;
    private static TaskSet currentTaskSetWorkingOn = new TaskSet();

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("frontPage.fxml"));  
        scene = new Scene(loadFXML("frontPage"));
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT); 
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

    public static void setCurrentTaskSetWorkingOn(TaskSet currentTaskSetWorkingOn) {
        currentTaskSetWorkingOn = currentTaskSetWorkingOn;
    }

    public static TaskSet getCurrentTaskSetWorkingOn() {
        return currentTaskSetWorkingOn;
    }
}