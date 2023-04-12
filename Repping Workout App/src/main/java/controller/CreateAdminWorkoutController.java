package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.AdminWorkouts;
import model.Exercise;
import model.Workouts;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateAdminWorkoutController implements Initializable {

    @FXML
    public Button btnCreateWorkout;
    @FXML
    public TextField tfNewWorkoutName;
    @FXML
    public ListView lvExistingExercises;
    @FXML
    public ListView lvAddedExercises;
    @FXML
    public Button btnAddExercise;
    @FXML
    public Button btnRemoveExercise;
    @FXML
    public Button btnBack;
    @FXML
    public Button btnCreateNewExercise;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String[] existingExercises = Exercise.getExerciseList().toArray(new String[0]);
        lvExistingExercises.getItems().addAll(existingExercises);

        lvExistingExercises.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                String currentExistingExercise = (String) lvExistingExercises.getSelectionModel().getSelectedItem();
                btnAddExercise.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        lvAddedExercises.getItems().add(currentExistingExercise);
                        lvExistingExercises.getItems().remove(currentExistingExercise);
                    }
                });
            }
        });

        lvAddedExercises.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                String currentAddedExercise = (String) lvAddedExercises.getSelectionModel().getSelectedItem();

                btnRemoveExercise.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        lvExistingExercises.getItems().add(currentAddedExercise);
                        lvAddedExercises.getItems().remove(currentAddedExercise);
                    }
                });
            }
        });

        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/Suggested-tab.fxml").toURI().toURL();
                    DBLogSign.changeScene(event, url, "Check out some suggested workouts!", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnCreateWorkout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AdminWorkouts.setCheck(tfNewWorkoutName.getText());
                if (AdminWorkouts.getCheck() == false) {
                if (!lvAddedExercises.getItems().isEmpty() && !tfNewWorkoutName.getText().trim().isEmpty()) {
                        ObservableList<String> allAddedExercises = lvAddedExercises.getItems();
                        for (int i = 0; i < lvAddedExercises.getItems().size(); i++) {
                            String currentExerciseName = allAddedExercises.get(i);
                            Exercise.setExerciseID(currentExerciseName);
                            DBCreateAdminWorkout.addNewAdminWorkout(event, tfNewWorkoutName.getText());
                        }
                        AdminWorkouts.clearAdminWorkouts();
                        AdminWorkouts.setAdminWorkouts();
                        try {
                            URL url = new File("src/main/java/view/Suggested-tab.fxml").toURI().toURL();
                            DBLogSign.changeScene(event, url, "Check out some suggested workouts!", null, null);
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("Please enter a workout name and add at least 1 exercise to the workout");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Please enter a workout name and add at least 1 exercise to the workout");
                        alert.show();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Workout name has been taken, please choose another");
                    alert.show();
                }
        }
    });

        btnCreateNewExercise.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/Create-Exercise-Screen.fxml").toURI().toURL();
                    DBLogSign.changeScene(event, url, "Create a new exercise!", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }
}
