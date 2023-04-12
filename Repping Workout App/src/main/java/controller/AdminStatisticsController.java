package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import model.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminStatisticsController implements Initializable {

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
    public ChoiceBox cbExercise;
    @FXML
    public LineChart lcStat;
    @FXML
    public ChoiceBox cbUser;
    @FXML
    public Label lblSetName;


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

        AdminStats.clearUserList();
        AdminStats.setUserList();
        String[] userList = AdminStats.getUserList().toArray(new String[0]);
        cbUser.getItems().addAll(userList);

        User.setOriginalUsername();
        cbUser.setValue(null);


        cbUser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                String user = cbUser.getSelectionModel().getSelectedItem().toString();
                User.setUsername(cbUser.getSelectionModel().getSelectedItem().toString());
                User.setUserIDFromName(User.getUsername());

                cbExercise.getItems().removeAll(cbExercise.getItems());
                AdminExercise.clearExerciseList();
                AdminExercise.setExerciseList(User.getUserIDFromName());
                String[] exercises = AdminExercise.getExerciseList().toArray(new String[0]);
                cbExercise.getItems().addAll(exercises);

            }
        });

        XYChart.Series series = new XYChart.Series();
        series.setName("Workout progress");
        lcStat.setAnimated(false);

        cbExercise.disableProperty().bind(cbUser.getSelectionModel().selectedItemProperty().isNull());

        cbExercise.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                try {
                    Exercise.setExerciseID(cbExercise.getSelectionModel().getSelectedItem().toString());
                    AdminStats.clearDate();
                    AdminStats.clearWeight();
                    AdminStats.setDateWeight(Exercise.getExerciseID(), User.getUserIDFromName());

                    String[] Date = AdminStats.getDate().toArray(new String[0]);
                    String[] Weight = AdminStats.getWeight().toArray(new String[0]);

                    lcStat.getData().remove(series);
                    series.getData().clear();
                    for (int j = 0; j < Date.length; j++) {
                        series.getData().add(new XYChart.Data(Date[j], Integer.parseInt(Weight[j])));
                    }
                    lcStat.getData().add(series);
                } catch (NullPointerException e) {
                    cbExercise.setValue(null);
                }
            }
        });
    }
}