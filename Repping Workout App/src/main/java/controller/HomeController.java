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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CompletedWorkouts;
import model.Exercise;
import model.User;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

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
    public Button btnViewWorkout;
    @FXML
    public ListView lvCompletedWorkouts;
    @FXML
    public Button btnDelete;
    @FXML
    public ChoiceBox cbFilter;
    @FXML
    public Label lblSetName;
    private String currentWorkout;

    public void load() {
        CompletedWorkouts.clearCompletedWorkoutInformation();
        CompletedWorkouts.setCompletedWorkoutInformation();
        lvCompletedWorkouts.getItems().clear();
        String[] workouts = CompletedWorkouts.getCompletedWorkoutInformation().toArray(new String[0]);
        lvCompletedWorkouts.getItems().addAll(workouts);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DBWorkouts.deleteTempWorkouts();

        User.setOriginalUsername();
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

        CompletedWorkouts.clearCompletedWorkoutInformation();
        CompletedWorkouts.setCompletedWorkoutInformation();
        Collections.reverse(CompletedWorkouts.getCompletedWorkoutInformation());
        String[] doneWorkouts = CompletedWorkouts.getCompletedWorkoutInformation().toArray(new String[0]);
        lvCompletedWorkouts.getItems().addAll(doneWorkouts);
        lvCompletedWorkouts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
            }
        });


        btnViewWorkout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String selected = lvCompletedWorkouts.getSelectionModel().getSelectedItem().toString();
                int iend = selected.indexOf(":");
                String subString = selected.substring(0 , iend);
                CompletedWorkouts.setSetIDFromList(Integer.valueOf(subString));

                CompletedWorkouts.setRecordedID(Integer.valueOf(subString));

                try {
                    URL url = new File("src/main/java/view/View-Workout-Screen.fxml").toURI().toURL();
                    Parent root = null;
                    try {
                        FXMLLoader loader = new FXMLLoader(url);
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("View");
                    stage.setScene(new Scene(root, stage.getWidth()-13, stage.getHeight()-35.5));
                    stage.show();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        btnDelete.disableProperty().bind(lvCompletedWorkouts.getSelectionModel().selectedItemProperty().isNull());
        btnViewWorkout.disableProperty().bind(lvCompletedWorkouts.getSelectionModel().selectedItemProperty().isNull());

        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String selected = lvCompletedWorkouts.getSelectionModel().getSelectedItem().toString();
                int iend = selected.indexOf(":");
                String subString = selected.substring(0 , iend);;
                DBWorkouts.deleteRecordedID(Integer.valueOf(subString));
                load();
            }
        });

        CompletedWorkouts.clearSpecificWorkout();
        CompletedWorkouts.clearDistinctWorkouts();
        CompletedWorkouts.setDistinctCompletedWorkoutInformation();
        String[] distinctWorkouts = CompletedWorkouts.getDistinctCompletedWorkoutInformation().toArray(new String[0]);
        cbFilter.getItems().addAll(distinctWorkouts);





        cbFilter.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                if (cbFilter.getValue().toString() == "All"){
                    lvCompletedWorkouts.getItems().clear();
                    CompletedWorkouts.clearCompletedWorkoutInformation();
                    CompletedWorkouts.setCompletedWorkoutInformation();
                    String[] AllWorkouts = CompletedWorkouts.getCompletedWorkoutInformation().toArray(new String[0]);
                    lvCompletedWorkouts.getItems().addAll(AllWorkouts);
                    lvCompletedWorkouts.refresh();
                    cbFilter.setValue("All");
                } else if (cbFilter.getValue().toString() != "All"){
                    CompletedWorkouts.clearSpecificWorkout();
                    CompletedWorkouts.setSpecificWorkout(cbFilter.getValue().toString());
                    lvCompletedWorkouts.getItems().clear();
                    String[] filteredWorkouts = CompletedWorkouts.getSpecificWorkout().toArray(new String[0]);
                    lvCompletedWorkouts.getItems().addAll(filteredWorkouts);
                    lvCompletedWorkouts.refresh();
                }
            }
        });



    }
}
