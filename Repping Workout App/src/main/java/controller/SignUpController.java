package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.AdminWorkouts;
import model.Exercise;
import model.User;
import model.Workouts;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    public Button btnCreateAccount;
    @FXML
    public Button btnLogIn;
    @FXML
    public RadioButton rbUser;
    @FXML
    public RadioButton rbAdmin;
    @FXML
    public TextField tfNewUsername;
    @FXML
    public TextField tfNewPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Creates the group for the radio buttons on the sign-up page to select user or admin
        ToggleGroup toggleGroup = new ToggleGroup();
        rbUser.setToggleGroup(toggleGroup);
        rbAdmin.setToggleGroup(toggleGroup);

        //Selects user to be automatically selected
        rbUser.setSelected(true);

        //Checks if user credentials are all entered
        btnCreateAccount.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();

                if (!tfNewUsername.getText().trim().isEmpty() && !tfNewPassword.getText().trim().isEmpty()) {
                    DBLogSign.signUpUser(event, tfNewUsername.getText(), tfNewPassword.getText(), toggleName);
                    User.setUserID(tfNewUsername.getText());
                    DBLogSign.addInitialWorkouts(event);
                    Workouts.setWorkouts();
                    Exercise.setExerciseList();
                    User.setIsAdmin();
                    AdminWorkouts.setAdminWorkouts();
                } else {
                    System.out.println("Please fill in all credentials");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all credentials to sign up");
                    alert.show();
                }
            }
        });

        //Changes scene to log in page from sign up page
        btnLogIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/login-screen.fxml").toURI().toURL();
                    DBLogSign.changeSceneLogSign(event, url, "Log In!", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
