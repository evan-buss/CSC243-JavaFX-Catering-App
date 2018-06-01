package catering;

import catering.models.Customer;
import catering.models.Event;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    public static Stage stage;
    public static ArrayList<Event> events = new ArrayList<>();
    public static Customer customer;

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

    public static void main(String[] args) {
        launch(args);
    }
}
