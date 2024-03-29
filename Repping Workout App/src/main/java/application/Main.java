package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        URL url = new File("src/main/java/view/login-screen.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage.setTitle("Log in!");
        stage.setScene(new Scene(root, 500, 400));
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
