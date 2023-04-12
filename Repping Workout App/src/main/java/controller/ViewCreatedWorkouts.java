package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ViewCreatedWorkouts implements Initializable{

    @FXML
    public TableView<tvStartWorkout> tvWorkout;
    @FXML
    public TableColumn<tvStartWorkout, String> tcExercise;
    @FXML
    public TableColumn<tvStartWorkout, Integer> tcWeight;
    @FXML
    public TableColumn<tvStartWorkout, Integer> tcReps1;
    @FXML
    public TableColumn<tvStartWorkout, Integer> tcReps2;
    @FXML
    public TableColumn<tvStartWorkout, Integer> tcReps3;
    @FXML
    public Button btnBack;
    ObservableList<tvStartWorkout> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        tcExercise.setCellValueFactory(new PropertyValueFactory<tvStartWorkout, String>("exerciseName"));
        tcWeight.setCellValueFactory(new PropertyValueFactory<tvStartWorkout, Integer>("weight"));
        tcReps1.setCellValueFactory(new PropertyValueFactory<tvStartWorkout, Integer>("reps1"));
        tcReps2.setCellValueFactory(new PropertyValueFactory<tvStartWorkout, Integer>("reps2"));
        tcReps3.setCellValueFactory(new PropertyValueFactory<tvStartWorkout, Integer>("reps3"));

            Connection connection = null;
            ResultSet resultSet = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
                preparedStatement = connection.prepareStatement("SELECT DISTINCT ExerciseID FROM tblworkout WHERE WorkoutName = '" + Workouts.getSelectedWorkout() + "' AND UserID = " + User.getUserID());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int exerciseID = resultSet.getInt("ExerciseID");
                    Exercise.setNewExerciseID(exerciseID);
                    list.add(new tvStartWorkout(Exercise.getNewExerciseName(), ViewSets.getWeight(), ViewSets.getSet1Reps(), ViewSets.getSet2Reps(), ViewSets.getSet3Reps()));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            tvWorkout.setItems(list);

        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) btnBack.getScene().getWindow();
                stage.close();
                }
        });


    }
}