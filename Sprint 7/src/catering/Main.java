package catering;

import catering.models.Customer;
import catering.models.DBConn;
import catering.models.Event;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    public static Stage stage;
    public static Customer customer;

    /**
     * Default function called when the application starts,
     * all other scenes started here.
     * @param primaryStage  Main application stage
     * @throws Exception    Throws an exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass()
                .getResource("views/titleScene.fxml"));
        primaryStage.setTitle("Juliana's Catering");
        primaryStage.setScene(new Scene(root));
        customer = new Customer();
        primaryStage.show();
    }

    /**
     * Launches the application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
