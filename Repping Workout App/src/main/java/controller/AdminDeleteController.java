package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import model.Exercise;
import model.User;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminDeleteController implements Initializable {

    @FXML
    public Button btnBack;
    @FXML
    public Button btnDelete;
    @FXML
    public ChoiceBox cbAccounts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) btnBack.getScene().getWindow();
                stage.close();
            }
        });

        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure you want to delete the account with username " + cbAccounts.getSelectionModel().getSelectedItem().toString());
                alert.setTitle("Delete user");
                alert.setHeaderText("Do you want to delete this user");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    User.deleteUser(cbAccounts.getSelectionModel().getSelectedItem().toString());
                    User.setUserList();
                    cbAccounts.getItems().remove(cbAccounts.getSelectionModel().getSelectedItem().toString());
                    cbAccounts.setValue(null);
                }
            }
        });

        User.setUserList();
        String[] userList = User.getUserList().toArray(new String[0]);
        cbAccounts.getItems().addAll(userList);

    }
}
