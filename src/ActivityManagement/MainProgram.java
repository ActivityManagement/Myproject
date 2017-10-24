package ActivityManagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainProgram extends Application {

    static Stage primaryWindow;
    static Scene loginScene;
    static Scene createactScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent login = FXMLLoader.load(getClass().getResource("loginpage.fxml"));
        Parent createact = FXMLLoader.load(getClass().getResource("createActPage.fxml"));
        loginScene = new Scene(login);
        createactScene = new Scene(createact);
        primaryWindow = primaryStage;
        primaryWindow.setTitle("Activity Management");
        primaryWindow.setScene(loginScene);
        primaryWindow.setMinWidth(1280);
        primaryWindow.setMinHeight(720);
        primaryWindow.show();
    }
}
