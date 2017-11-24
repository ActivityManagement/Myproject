package ActivityManagement;

import ActivityManagement.Controller.*;
import ActivityManagement.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class MainProgram extends Application {

    public static Stage primaryWindow;
    private static Scene programScene;

    public static Stage getPrimaryWindow() {
        return primaryWindow;
    }

    public static Parent getLogin() {
        return login;
    }

    public static Parent getCreateact() {
        return createact;
    }

    public static Parent getMainpage() {
        return mainpage;
    }

    public static Parent getMainactpage() {
        return mainactpage;
    }

    public static Parent getMainDept() {
        return mainDept;
    }

    public static Parent getCreateDept() {
        return createDept;
    }

    public static Node getDeptPane() {
        return DeptPane;
    }

    public static Node getMemberactpane() {
        return memberactpane;
    }

    public static Person getPersonCurrent() {
        return personCurrent;
    }

    public static String getDBName() {
        return DBName;
    }

    public static MainPageController getStageMainPage() {
        return stageMainPage;
    }

    public static LoginController getStageLoginPage() {
        return stageLoginPage;
    }

    public static CreateActController getStageCreateActPage() {
        return stageCreateActPage;
    }

    public static ActPageController getStageMainActPage() {
        return stageMainActPage;
    }

    public static MemberActPaneController getStageMemberActPane() {
        return stageMemberActPane;
    }

    public static MainDeptController getStageMainDeptController() {
        return stageMainDeptController;
    }

    public static DeptPaneController getStageDeptPane() {
        return stageDeptPane;
    }

    public static CreateDeptController getStageCreateDeptPage() {
        return stageCreateDeptPage;
    }

    public static Parent login;
    public static Parent createact;
    public static Parent mainpage;
    public static Parent mainactpage;
    public static Parent mainDept;
    public static Parent createDept;
    public static Node DeptPane;
    public static Node memberactpane;

    public static Person personCurrent;
    private static Department ActivityCurrent;
    public static String DBName = "ActivityManagementDB.odb";

    public static MainPageController stageMainPage;
    public static LoginController stageLoginPage;
    public static CreateActController stageCreateActPage;
    public static ActPageController stageMainActPage;
    public static MemberActPaneController stageMemberActPane;
    public static MainDeptController stageMainDeptController;
    public static DeptPaneController stageDeptPane;
    public static CreateDeptController stageCreateDeptPage;

    private double winWidth = 1280;
    private double winHeigth = 720+40;

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
        loader.setLocation(getClass().getResource("View/memberactpane.fxml"));
        memberactpane = loader.load();
        stageMemberActPane = loader.getController();
        //------------------------------------------------------------------------------------------
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View/Department.fxml"));
        DeptPane = loader.load();
        stageDeptPane = loader.getController();
        //------------------------------------------------------------------------------------------
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View/MasterDepartment.fxml"));
        mainDept = loader.load();
        stageMainDeptController = loader.getController();
        //------------------------------------------------------------------------------------------
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View/CreateDeptPage.fxml"));
        createDept = loader.load();
        stageCreateDeptPage = loader.getController();
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
        em.getMetamodel().entity(Department.class);
        odb.closeConnection();

    }

    public static void updateActivity()
    {
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection(DBName);
        TypedQuery<Activity> query = em.createQuery("SELECT a FROM Activity a where a.actid = '"+stageMainPage.getCurrentselectact().getActid()+"'", Activity.class);
        List<Activity> results = query.getResultList();
        em.getTransaction().begin();
        for (Activity a : results) {
            stageMainPage.setCurrentselectact(a);
        }
        em.getTransaction().commit();
        odb.closeConnection();
    }


    public static void updatePerson()
    {
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection(DBName);
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p where p.id = "+personCurrent.getId()+"", Person.class);
        List<Person> results = query.getResultList();
        em.getTransaction().begin();
        for (Person p : results) {
            personCurrent = p;
        }
        em.getTransaction().commit();
        odb.closeConnection();
    }

    public static void updateDepartment()
    {
        ObjectDB odb = new ObjectDB();
        EntityManager em = odb.createConnection(DBName);
        TypedQuery<Department> query = em.createQuery("SELECT d FROM Department d where d.id = '"+ActivityCurrent.getId()+"'", Department.class);
        List<Department> results = query.getResultList();
        em.getTransaction().begin();
        for (Department d : results) {
            ActivityCurrent = d;
        }
        em.getTransaction().commit();
        odb.closeConnection();
    }
}
