package ActivityManagement;

import ActivityManagement.Controller.*;
import ActivityManagement.Model.ObjectDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import javax.persistence.EntityManager;

public class MainProgram extends Application {

    public static Stage primaryWindow;
<<<<<<< HEAD
=======
    public static Scene programScene;
    public static Parent login;
    public static Parent createact;
    public static Parent mainpage;
    public static Person personCurrent;
    public static String DBName = "ActivityManagementDB.odb";

    public static MainPageController stageMainPage;
    public static LoginController stageLoginPage;
    public static CreateActController stageCreateActPage;

    private double winWidth = 1280;
    private double winHeigth = 720;
>>>>>>> create_act

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
<<<<<<< HEAD
        Parent login = FXMLLoader.load(getClass().getResource("View/loginpage.fxml"));
        primaryWindow = primaryStage;
        primaryWindow.setTitle("Activity Management");
        Scene scene = new Scene(login);
        primaryWindow.setScene(scene);
        primaryWindow.setMinWidth(1280);
        primaryWindow.setMinHeight(720);
=======
        FXMLLoader loader;
        //------------------------------------------------------------------------------------------
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View/loginpage.fxml"));
        login = loader.load();
        stageLoginPage = loader.getController();
        //------------------------------------------------------------------------------------------
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View/createActPage.fxml"));
        createact = loader.load();
        stageCreateActPage = loader.getController();
        //------------------------------------------------------------------------------------------
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View/mainpage.fxml"));
        mainpage = loader.load();
        stageMainPage = loader.getController();
        //------------------------------------------------------------------------------------------
        programScene = new Scene(login);
        primaryWindow = primaryStage;
        primaryWindow.setTitle("Activity Management");
        primaryWindow.setScene(programScene);
        primaryWindow.getScene().setRoot(login);
        primaryWindow.setWidth(winWidth);
        primaryWindow.setHeight(winHeigth);
        primaryWindow.setMinWidth(winWidth);
        primaryWindow.setMinHeight(winHeigth);
>>>>>>> create_act
        primaryWindow.show();

        //program running
        // Create Table of ActivityManagement
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection(DBName);
        em.getMetamodel().entity(Activity.class);
        em.getMetamodel().entity(Person.class);
        em.getMetamodel().entity(HasActivity.class);
        odb.closeConnection();

    }
}
