package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

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
    public Button btnLogOut;
    @FXML
    public Button btnAdmin;
    @FXML
    public Button btnEditProfile;
    @FXML
    public Pane pAdmin;
    @FXML
    public ImageView ivAdmin;
    @FXML
    public Label lblSetName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

        btnLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/login-screen.fxml").toURI().toURL();
                    DBLogSign.changeSceneLogSign(event, url, "Log in!", null, null);
                    User.makeUserIDZero();
                    Workouts.clearWorkouts();
                    CompletedWorkouts.clearCompletedWorkoutInformation();
                    AdminWorkouts.clearAdminWorkouts();
                    Exercise.clearExerciseListFromWorkout();
                    Exercise.clearExerciseList();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        btnEditProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                URL url = null;
                try {
                    url = new File("src/main/java/view/Change-Username.fxml").toURI().toURL();
                    DBLogSign.changeSceneLogSign(event, url, "Make selection", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        if (User.getIsAdmin() == true) {
            btnAdmin.setVisible(true);
        } else {
            btnAdmin.setVisible(false);
            ivAdmin.setVisible(false);
            pAdmin.setVisible(false);
        }

        btnAdmin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/Admin-Delete-Screen.fxml").toURI().toURL();
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
                    stage.setScene(new Scene(root, stage.getWidth() - 13, stage.getHeight() - 35.5));
                    stage.show();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


      }
}
