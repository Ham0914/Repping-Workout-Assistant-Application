package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import org.w3c.dom.events.MouseEvent;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class    StartWorkoutController implements Initializable{

    @FXML
    public TableView<model.tvStartWorkout> tvWorkout;
    @FXML
    public TableColumn<model.tvStartWorkout, String> tcExercise;
    @FXML
    public TableColumn<model.tvStartWorkout, Integer> tcWeight;
    @FXML
    public TableColumn<model.tvStartWorkout, Integer> tcReps1;
    @FXML
    public TableColumn<model.tvStartWorkout, Integer> tcReps2;
    @FXML
    public TableColumn<model.tvStartWorkout, Integer> tcReps3;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnUpdate;
    @FXML
    public TextField tfWeight;
    @FXML
    public TextField tfReps1;
    @FXML
    public TextField tfReps2;
    @FXML
    public TextField tfReps3;
    @FXML
    public Button btnBack;
    @FXML
    public Button btnCompleteWorkout;
    @FXML
    public ChoiceBox cbExercise;
    @FXML
    public Label lblNumber;

    int weight, set1reps, set2reps, set3reps;
    int index = -1;
    ObservableList<model.tvStartWorkout> list = FXCollections.observableArrayList();
    String nameOfExercise;

    @FXML
    public void getSelected () {
        index = tvWorkout.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        tfWeight.setText(tcWeight.getCellData(index).toString());
        tfReps1.setText(tcReps1.getCellData(index).toString());
        tfReps2.setText(tcReps2.getCellData(index).toString());
        tfReps3.setText(tcReps3.getCellData(index).toString());
        nameOfExercise = tcExercise.getCellData(index).toString();
    }

    public void edit () {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            int value1 = Integer.parseInt(tfWeight.getText());
            int value2 = Integer.parseInt(tfReps1.getText());
            int value3 = Integer.parseInt(tfReps2.getText());
            int value4 = Integer.parseInt(tfReps3.getText());
            preparedStatement = connection.prepareStatement("UPDATE tblSets SET Weight=" + value1 + ", Set1Reps= "
                    + value2 + ", Set2Reps= " + value3 + ", Set3Reps= " + value4 + " WHERE RecordedWorkoutID = "
                    + RecordWorkoutsAndSets.getRecordedWorkoutID() + " AND IDofExercise = " + Exercise.getExerciseID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        tcExercise.setCellValueFactory(new PropertyValueFactory<model.tvStartWorkout, String>("exerciseName"));
        tcWeight.setCellValueFactory(new PropertyValueFactory<model.tvStartWorkout, Integer>("weight"));
        tcReps1.setCellValueFactory(new PropertyValueFactory<model.tvStartWorkout, Integer>("reps1"));
        tcReps2.setCellValueFactory(new PropertyValueFactory<model.tvStartWorkout, Integer>("reps2"));
        tcReps3.setCellValueFactory(new PropertyValueFactory<model.tvStartWorkout, Integer>("reps3"));

            Connection connection = null;
            ResultSet resultSet = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
                preparedStatement = connection.prepareStatement("SELECT DISTINCT ExerciseID FROM tblworkout WHERE WorkoutName = '" + model.Workouts.getSelectedWorkout() + "'");
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int exerciseID = resultSet.getInt("ExerciseID");
                    Exercise.setNewExerciseID(exerciseID);
                    RecordWorkoutsAndSets.insertNewSet(exerciseID);
                    Sets.setWeight();
                    Sets.setSet1Reps();
                    Sets.setSet2Reps();
                    Sets.setSet3Reps();
                    list.add(new tvStartWorkout(Exercise.getNewExerciseName(), Sets.getWeight(), Sets.getSet1Reps(), Sets.getSet2Reps(), Sets.getSet3Reps()));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            tvWorkout.setItems(list);

        lblNumber.setVisible(false);

        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                weight = Integer.parseInt(tfWeight.getText());
                set1reps = Integer.parseInt(tfReps1.getText());
                set2reps = Integer.parseInt(tfReps2.getText());
                set3reps = Integer.parseInt(tfReps3.getText());


                Exercise.setExerciseID(nameOfExercise);
                edit();
                list.clear();
                lblNumber.setVisible(false);

                Connection connection = null;
                ResultSet resultSet = null;
                PreparedStatement preparedStatement = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
                    preparedStatement = connection.prepareStatement("SELECT DISTINCT TempExerciseID FROM tbltempworkout WHERE TempWorkoutName = '" + Workouts.getSelectedWorkout() + "'");
                    resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        int exerciseID = resultSet.getInt("TempExerciseID");
                        Exercise.setNewExerciseID(exerciseID);
                        Sets.setWeight();
                        Sets.setSet1Reps();
                        Sets.setSet2Reps();
                        Sets.setSet3Reps();
                        list.add(new tvStartWorkout(Exercise.getNewExerciseName(), Sets.getWeight(), Sets.getSet1Reps(), Sets.getSet2Reps(), Sets.getSet3Reps()));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                tfWeight.setText("0");
                tfReps1.setText("0");
                tfReps2.setText("0");
                tfReps3.setText("0");
            } catch (NumberFormatException e){
                    lblNumber.setVisible(true);
                } catch (Exception e){
                    lblNumber.setVisible(true);
                }
            }
        });




        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("This will cancel the workout and the workout will not be recorded");
                alert.setTitle("Cancel workout");
                alert.setHeaderText("Do you want to cancel this workout?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get()==ButtonType.OK) {
                        DBWorkouts.deleteSets();
                        DBWorkouts.deleteRecordedWorkouts();
                        DBWorkouts.deleteTempWorkouts();
                    try {
                        URL url = new File("src/main/java/view/workouts-tab.fxml").toURI().toURL();
                        DBLogSign.changeScene(event, url, "Workout!", null, null);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        btnCompleteWorkout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Your workout will be completed and you will not be able to make anymore changes");
                alert.setTitle("Finish workout");
                alert.setHeaderText("Do you want to complete this workout?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    DBWorkouts.deleteTempWorkouts();
                    try {
                        URL url = new File("src/main/java/view/home-tab.fxml").toURI().toURL();
                        DBLogSign.changeScene(event, url, "Check out your previous workouts!", null, null);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


        String[] exercises = Exercise.getExerciseList().toArray(new String[0]);
        cbExercise.getItems().addAll(exercises);

        btnAdd.disableProperty().bind(cbExercise.getSelectionModel().selectedItemProperty().isNull());

        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            String choice = cbExercise.getValue().toString();
            Exercise.setExerciseID(choice);
            TempWorkout.addSingleTempWorkout(Workouts.getSelectedWorkout(), Exercise.getExerciseID());
            list.add(new tvStartWorkout(choice, 0, 0, 0, 0));
            tvWorkout.refresh();
            RecordWorkoutsAndSets.insertNewExerciseSets();
            }
        });



    }
}