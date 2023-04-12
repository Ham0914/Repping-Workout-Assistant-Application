package controller;

import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBCreateExercise {

    public static void addInitialExercises (ActionEvent event){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");

            preparedStatement = connection.prepareStatement("INSERT INTO tblexercise (ExerciseName) VALUES ('Chest Fly')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblexercise (ExerciseName) VALUES ('Bench Press')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblexercise (ExerciseName) VALUES ('Incline Bench Press')");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO tblexercise (ExerciseName) VALUES ('Pull Ups')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblexercise (ExerciseName) VALUES ('Lat Pulldown')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblexercise (ExerciseName) VALUES ('Deadlift')");
            preparedStatement.executeUpdate();


            preparedStatement = connection.prepareStatement("INSERT INTO tblexercise (ExerciseName) VALUES ('Bicep Curl')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblexercise (ExerciseName) VALUES ('Hammer Curl')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblexercise (ExerciseName) VALUES ('Barbell Curl')");
            preparedStatement.executeUpdate();


            preparedStatement = connection.prepareStatement("INSERT INTO tblexercise (ExerciseName) VALUES ('Squats')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblexercise (ExerciseName) VALUES ('Leg Press')");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("INSERT INTO tblexercise (ExerciseName) VALUES ('Hamstring Curl')");
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void CreateNewExercise(ActionEvent event, String NewExerciseName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("INSERT INTO tblexercise (ExerciseName, UserIdentification) VALUES ('" + NewExerciseName + "', '" + model.User.getUserID() +"')");
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
