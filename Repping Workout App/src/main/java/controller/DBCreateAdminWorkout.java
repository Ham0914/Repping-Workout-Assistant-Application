package controller;

import javafx.event.ActionEvent;

import java.sql.*;

public class DBCreateAdminWorkout {


    public static void addNewAdminWorkout (ActionEvent event, String NewWorkoutName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");

            preparedStatement = connection.prepareStatement("INSERT INTO tbladminworkout (WorkoutName, AdminsExerciseID) VALUES ('" + NewWorkoutName + "', '" + model.Exercise.getExerciseID() + "')");
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
