package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {

    @FXML
    public Button btnBack;
    @FXML
    public Button btnChangePassword;
    @FXML
    public Button btnGoToChangeUsername;
    public TextField tfChangePassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Logs user in
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

        btnChangePassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DBChangeCredentials.ChangePassword(event, tfChangePassword.getText(), model.User.getUserID());
                    URL url = new File("src/main/java/view/Settings-tab.fxml").toURI().toURL();
                    DBLogSign.changeScene(event, url, "Change settings", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnGoToChangeUsername.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = new File("src/main/java/view/Change-Username.fxml").toURI().toURL();
                    DBLogSign.changeSceneLogSign(event, url, "Change username", null, null);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
