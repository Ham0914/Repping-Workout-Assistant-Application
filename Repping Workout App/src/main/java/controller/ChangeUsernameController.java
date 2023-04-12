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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangeUsernameController implements Initializable {

    @FXML
    public Button btnBack;
    @FXML
    public Button btnChangeUsername;
    @FXML
    public Button btnGoToChangePassword;
    public TextField tfChangeUsername;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/Settings-tab.fxml").toURI().toURL();
                    DBLogSign.changeScene(event, url, "Change settings", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnGoToChangePassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/Change-Password.fxml").toURI().toURL();
                    DBLogSign.changeSceneLogSign(event, url, "Change password", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnChangeUsername.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DBChangeCredentials.ChangeUsername(event, tfChangeUsername.getText(), model.User.getUserID());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
