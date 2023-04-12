package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;

import static controller.DBLogSign.changeScene;

public class DBChangeCredentials {


    public static void ChangeUsername(ActionEvent event, String username, int UserID) throws SQLException, MalformedURLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
        psCheckUserExists = connection.prepareStatement("SELECT * FROM user WHERE Username = ?");
        //Sets the "?" in the prepared statement to the username variable
        psCheckUserExists.setString(1, username);
        resultSet = psCheckUserExists.executeQuery();
        if (resultSet.isBeforeFirst()) {
            System.out.println("User already exists");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username already in use, please create a different username");
            alert.show();
        } else {
            psInsert = connection.prepareStatement("UPDATE user SET Username='" + username + "' WHERE UserID=" + UserID);
            psInsert.executeUpdate();
            URL url = new File("src/main/java/view/Settings-tab.fxml").toURI().toURL();
            DBLogSign.changeScene(event, url, "Change settings", null, null);
        }
    }

    public static void ChangePassword(ActionEvent event, String password, int UserID) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            psInsert = connection.prepareStatement("UPDATE user SET Password='" + password + "' WHERE UserID=" + UserID);
            psInsert.executeUpdate();
        }
}