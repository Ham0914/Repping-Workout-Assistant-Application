package controller;

import javafx.event.ActionEvent;
import model.Exercise;
import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBCreateWorkout {

    public static void addNewWorkout (String NewWorkoutName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");

            preparedStatement = connection.prepareStatement("INSERT INTO tblworkout (WorkoutName, UserID, ExerciseID) VALUES ('" + NewWorkoutName + "', '" + User.getUserID() + "', '" + Exercise.getExerciseID() + "')");
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
