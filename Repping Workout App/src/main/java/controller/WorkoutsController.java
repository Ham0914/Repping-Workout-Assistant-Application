package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class WorkoutsController implements Initializable {

    @FXML
    public Button btnHomeTab;
    @FXML
    public Button btnWorkoutsTab;
    @FXML
    public Button btnStatisticsTab;
    @FXML
    public Button btnSuggestedTab;
    @FXML
    public Button btnSettingsTab;
    @FXML
    public Button btnAddWorkout;
    @FXML
    public ListView<String> lvWorkouts;
    @FXML
    public Button btnStartWorkout;
    @FXML
    public Button btnDelete;
    @FXML
    public Button btnView;
    private String currentWorkout;
    @FXML
    public Label lblSetName;


    public void load() {
        Workouts.clearWorkouts();
        Workouts.setWorkouts();
        lvWorkouts.getItems().clear();
        String[] workouts = Workouts.getWorkouts().toArray(new String[0]);
        lvWorkouts.getItems().addAll(workouts);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblSetName.setText(User.getOriginalUsername());

        btnHomeTab.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/home-tab.fxml").toURI().toURL();
                    DBLogSign.changeScene(event, url, "Check out your previous workouts!", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnWorkoutsTab.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/workouts-tab.fxml").toURI().toURL();
                    DBLogSign.changeScene(event, url, "Create or start a workout!", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnStatisticsTab.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (User.getIsAdmin() == true) {
                    try {
                        URL url = new File("src/main/java/view/Admin-statistics-tab.fxml").toURI().toURL();
                        DBLogSign.changeScene(event, url, "Check out your workout statistics!", null, null);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        URL url = new File("src/main/java/view/statistics-tab.fxml").toURI().toURL();
                        DBLogSign.changeScene(event, url, "Check out your workout statistics!", null, null);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        btnSuggestedTab.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/suggested-tab.fxml").toURI().toURL();
                    DBLogSign.changeScene(event, url, "Check out some suggested workouts!", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnSettingsTab.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/settings-tab.fxml").toURI().toURL();
                    DBLogSign.changeScene(event, url, "Change settings", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnAddWorkout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/Create-Workout-Screen.fxml").toURI().toURL();
                    DBLogSign.changeScene(event, url, "Create a new workout!", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

            Workouts.clearWorkouts();
            Workouts.setWorkouts();
            String[] workouts = Workouts.getWorkouts().toArray(new String[0]);
            lvWorkouts.getItems().addAll(workouts);


        lvWorkouts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                currentWorkout = lvWorkouts.getSelectionModel().getSelectedItem();
            }
        });

        btnStartWorkout.disableProperty().bind(lvWorkouts.getSelectionModel().selectedItemProperty().isNull());
        btnView.disableProperty().bind(lvWorkouts.getSelectionModel().selectedItemProperty().isNull());
        btnDelete.disableProperty().bind(lvWorkouts.getSelectionModel().selectedItemProperty().isNull());


        btnStartWorkout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Workouts.setSelectedWorkout(lvWorkouts.getSelectionModel().getSelectedItem());
                Exercise.setExerciseListFromWorkout(lvWorkouts.getSelectionModel().getSelectedItem());
                TempWorkout.addTempWorkouts(lvWorkouts.getSelectionModel().getSelectedItem());
                try {
                        RecordWorkoutsAndSets.insertNewRecordedWorkout(Workouts.getSelectedWorkout());
                        RecordWorkoutsAndSets.setRecordedWorkoutID();
                        URL url = new File("src/main/java/view/Start-Workout-Screen.fxml").toURI().toURL();
                        DBLogSign.changeScene(event, url, "Workout!", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBWorkouts.deleteWorkout(lvWorkouts.getSelectionModel().getSelectedItem());
                load();
            }
        });

        btnView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = lvWorkouts.getSelectionModel().getSelectedItem();
                Workouts.setSelectedWorkout(name);
                try {
                    URL url = new File("src/main/java/view/View-Created-Workouts-Screen.fxml").toURI().toURL();
                    Parent root = null;
                    try {
                        FXMLLoader loader = new FXMLLoader(url);
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("View workout exercises");
                    stage.setScene(new Scene(root, stage.getWidth()-13, stage.getHeight()-35.5));
                    stage.show();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });



    }
}