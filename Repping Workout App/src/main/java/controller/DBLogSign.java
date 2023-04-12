package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CompletedWorkouts;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.ResultSet;

public class DBLogSign {


    public static void changeSceneLogSign(ActionEvent event, URL url, String title, String username, String usertype) {
        Parent root = null;

        // Check if user is switching between log-in and sign up screen
        if (username != null && usertype != null) {
            try {
                FXMLLoader loader = new FXMLLoader(url);
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Setting the stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 500, 400));
        stage.show();
        stage.centerOnScreen();
    }

    //Method to change scenes during the log-in and sign up phase
    public static void changeScene(ActionEvent event, URL url, String title, String username, String usertype) {
        Parent root = null;

        // Check if user is switching between log-in and sign up screen
        if (username != null && usertype != null) {
            try {
                FXMLLoader loader = new FXMLLoader(url);
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Setting the stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 850, 550));
        stage.show();
        stage.centerOnScreen();
    }

    //Method to sign up a user to the database
    public static void signUpUser(ActionEvent event, String username, String password, String usertype){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager .getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");

            psCheckUserExists = connection.prepareStatement("SELECT * FROM user WHERE Username = ?");
            //Sets the "?" in the prepared statement to the username variable
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            //Checks if user already exists in the database
            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username already in use, please create a different username");
                alert.show();
              //Inserts user into the database
            } else {
                psInsert = connection.prepareStatement("INSERT INTO user (username, password, usertype) VALUES (?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, usertype);
                psInsert.executeUpdate();
                //Changes scene after sign up to automatically log user in
                URL url = new File("src/main/java/view/home-tab.fxml").toURI().toURL();
                changeScene(event, url, "Check out your previous workouts!", username, usertype);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
          //Closing database connection
        } finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }

    //Method to log in an existing user
    public static void logInUser (ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT password, usertype FROM user WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            //Checks if user does not exist and displays an error message
            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Your username or password is incorrect");
                alert.show();
             //Checks if user's credentials are all correct and changes scene if it is correct
            }else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedusertype = resultSet.getString("usertype");
                    if (retrievedPassword.equals(password)) {
                        URL url = new File("src/main/java/view/home-tab.fxml").toURI().toURL();
                        changeScene(event,url, "Welcome", username, retrievedusertype);
                    } else {
                        System.out.println("Password did not match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Your username or password is incorrect");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void addInitialWorkouts (ActionEvent event){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("INSERT INTO tblworkout (WorkoutName, UserID, ExerciseID) VALUES ('Chest', '" + model.User.getUserID() +"', '4')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblworkout (WorkoutName, UserID, ExerciseID) VALUES ('Chest', '" + model.User.getUserID() +"', '5')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblworkout (WorkoutName, UserID, ExerciseID) VALUES ('Chest', '" + model.User.getUserID() +"', '6')");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO tblworkout (WorkoutName, UserID, ExerciseID) VALUES ('Back', '" + model.User.getUserID() +"', '7')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblworkout (WorkoutName, UserID, ExerciseID) VALUES ('Back', '" + model.User.getUserID() +"', '8')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblworkout (WorkoutName, UserID, ExerciseID) VALUES ('Back', '" + model.User.getUserID() +"', '9')");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO tblworkout (WorkoutName, UserID, ExerciseID) VALUES ('Biceps', '" + model.User.getUserID() +"', '10')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblworkout (WorkoutName, UserID, ExerciseID) VALUES ('Biceps', '" + model.User.getUserID() +"', '11')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblworkout (WorkoutName, UserID, ExerciseID) VALUES ('Biceps', '" + model.User.getUserID() +"', '12')");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO tblworkout (WorkoutName, UserID, ExerciseID) VALUES ('Legs', '" + model.User.getUserID() +"', '13')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblworkout (WorkoutName, UserID, ExerciseID) VALUES ('Legs', '" + model.User.getUserID() +"', '14')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblworkout (WorkoutName, UserID, ExerciseID) VALUES ('Legs', '" + model.User.getUserID() +"', '15')");
            preparedStatement.executeUpdate();


        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
