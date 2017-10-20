package ActivityManagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainProgram extends Application {

    static Stage primaryWindow;
    static Parent login;
    static Parent createact;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        login = FXMLLoader.load(getClass().getResource("loginpage.fxml"));
        createact = FXMLLoader.load(getClass().getResource("createActPage.fxml"));
        primaryWindow = primaryStage;
        primaryWindow.setTitle("Activity Management");
        primaryWindow.setScene(new Scene(createact));
        primaryWindow.setMinWidth(1280);
        primaryWindow.setMinHeight(720);
        primaryWindow.show();
    }
}
