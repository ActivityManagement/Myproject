package ActivityManagement;

import ActivityManagement.Controller.*;
import ActivityManagement.Model.ObjectDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import javax.persistence.EntityManager;

public class MainProgram extends Application {

    public static Stage primaryWindow;
    public static Scene programScene;
    public static Parent login;
    public static Parent createact;
    public static Parent mainpage;
    public static Parent mainactpage;
    public static Node memberactpane;

    public static Person personCurrent;
    public static String DBName = "ActivityManagementDB.odb";

    public static MainPageController stageMainPage;
    public static LoginController stageLoginPage;
    public static CreateActController stageCreateActPage;
    public static ActPageController stageMainActPage;

    private double winWidth = 1280;
    private double winHeigth = 720;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
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
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View/mainact.fxml"));
        mainactpage = loader.load();
        stageMainActPage = loader.getController();
        //------------------------------------------------------------------------------------------
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View/mainactpane.fxml"));
        memberactpane = loader.load();
//        stageMemberActPane = loader.getController();
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
