package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

import model.*;

public class Controller implements Initializable {


    @FXML
    public Button btnLogin;
    @FXML
    public TextField tfUsername;
    @FXML
    public TextField tfPassword;
    @FXML
    public Button btnSignUp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Logs user in
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                User.setUserID(tfUsername.getText());
                DBLogSign.logInUser(event, tfUsername.getText(), tfPassword.getText());
                Workouts.setWorkouts();
                Exercise.setExerciseList();
                User.setIsAdmin();
                AdminWorkouts.setAdminWorkouts();
            }
        });

        //Action event button to change scene to sign up page
        btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/sign-up.fxml").toURI().toURL();
                    DBLogSign.changeSceneLogSign(event, url, "Sign Up!", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
