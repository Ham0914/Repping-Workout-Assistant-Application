package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AdminWorkouts;
import model.RecordWorkoutsAndSets;
import model.User;
import model.Workouts;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;

public class SuggestedController implements Initializable {

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
    public Button btnView;
    @FXML
    public Button btnAdminAdd;
    @FXML
    public Button btnAddToProfile;
    @FXML
    public ListView lvSuggestedWorkouts;
    @FXML
    public Button btnDelete;
    @FXML
    public Label lblSetName;


    public void load() {
        AdminWorkouts.clearAdminWorkouts();
        AdminWorkouts.setAdminWorkouts();
        lvSuggestedWorkouts.getItems().clear();
        String[] workouts = AdminWorkouts.getAdminWorkouts().toArray(new String[0]);
        lvSuggestedWorkouts.getItems().addAll(workouts);
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


        if (User.getIsAdmin() == true) {
            btnAdminAdd.setVisible(true);
        } else {
            btnAdminAdd.setVisible(false);
        }

        btnAdminAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/Create-Admin-Workout-Screen.fxml").toURI().toURL();
                    DBLogSign.changeScene(event, url, "Create a new Admin Workout", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        String[] workouts = AdminWorkouts.getAdminWorkouts().toArray(new String[0]);
        lvSuggestedWorkouts.getItems().addAll(workouts);
        lvSuggestedWorkouts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
            }
        });

        btnAddToProfile.disableProperty().bind(lvSuggestedWorkouts.getSelectionModel().selectedItemProperty().isNull());
        btnView.disableProperty().bind(lvSuggestedWorkouts.getSelectionModel().selectedItemProperty().isNull());

        btnAddToProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBSuggestedController.setCheck(lvSuggestedWorkouts.getSelectionModel().getSelectedItem().toString());
                if (DBSuggestedController.getCheck() == false) {
                    DBSuggestedController.addToProfile(lvSuggestedWorkouts.getSelectionModel().getSelectedItem().toString());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(lvSuggestedWorkouts.getSelectionModel().getSelectedItem().toString() + " has been added to your profile");
                    alert.setTitle("Workout Added");
                    alert.setHeaderText("Workout added successfully");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(lvSuggestedWorkouts.getSelectionModel().getSelectedItem().toString() + " already exists on your profile");
                    alert.setTitle("Error adding workout");
                    alert.setHeaderText("Workout not added");
                    alert.show();
                }

            }
        });

        if (User.getIsAdmin() == true) {
            btnDelete.setVisible(true);
        } else {
            btnDelete.setVisible(false);
        }
        btnDelete.disableProperty().bind(lvSuggestedWorkouts.getSelectionModel().selectedItemProperty().isNull());

        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBSuggestedController.deleteAdminWorkout(lvSuggestedWorkouts.getSelectionModel().getSelectedItem().toString());
                load();
            }
        });


        btnView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = lvSuggestedWorkouts.getSelectionModel().getSelectedItem().toString();
                AdminWorkouts.setAdminWorkoutName(name);
                try {
                    URL url = new File("src/main/java/view/View-Admin-Workout.fxml").toURI().toURL();
                    Parent root = null;
                    try {
                        FXMLLoader loader = new FXMLLoader(url);
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("View admin workout");
                    stage.setScene(new Scene(root, stage.getWidth()-13, stage.getHeight()-35.5));
                    stage.show();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }
}
