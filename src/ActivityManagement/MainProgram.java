package ActivityManagement;

import ActivityManagement.Controller.Activity;
import ActivityManagement.Model.ObjectDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import ActivityManagement.Controller.Person;

import javax.persistence.EntityManager;

public class MainProgram extends Application {

    public static Stage primaryWindow;
    public static Scene programScene;
    public static Parent login;
    public static Parent createact;
    public static Parent mainpage;
    public static Person personCurrent;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
//        login = FXMLLoader.load(getClass().getResource("View/loginpage.fxml"));
//        createact = FXMLLoader.load(getClass().getResource("View/createActPage.fxml"));
        login = loadPage("View/loginpage.fxml");
        createact = loadPage("View/createActPage.fxml");
        mainpage = loadPage("View/mainpage.fxml");
        programScene = new Scene(login);
        primaryWindow = primaryStage;
        primaryWindow.setTitle("Activity Management");
        primaryWindow.setScene(programScene);
        primaryWindow.getScene().setRoot(login);
        primaryWindow.setMinWidth(1280);
        primaryWindow.setMinHeight(720);
        primaryWindow.show();

        //program running
        // Create Table of ActivityManagement
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection("ActivityManagement.odb");
        em.getMetamodel().entity(Activity.class);
        em.getMetamodel().entity(Person.class);
        odb.closeConnection();

    }

    public Parent loadPage(String url)
    {
        try {
            return FXMLLoader.load(getClass().getResource(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
