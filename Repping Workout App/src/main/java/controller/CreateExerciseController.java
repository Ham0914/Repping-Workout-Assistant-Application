package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Exercise;
import model.Workouts;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateExerciseController implements Initializable {

    public Button btnCreateExercise;
    @FXML
    public TextField tfNewExercise;
    @FXML
    public Button btnBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        btnCreateExercise.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tfNewExercise.getText().trim().isEmpty()) {
                    DBCreateExercise.CreateNewExercise(event, tfNewExercise.getText());
                    Exercise.clearExerciseList();
                    Exercise.setExerciseList();
                    try {
                        URL url = new File("src/main/java/view/Create-Workout-Screen.fxml").toURI().toURL();
                        DBLogSign.changeScene(event, url, "Create a workout!", null, null);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to complete exercise creation");
                    alert.show();
                }
            }
        });

        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/Create-Workout-Screen.fxml").toURI().toURL();
                    DBLogSign.changeScene(event, url, "Create a workout!", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }
}
