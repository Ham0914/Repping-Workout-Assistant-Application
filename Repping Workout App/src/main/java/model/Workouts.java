package model;

import javafx.collections.FXCollections;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

public class Workouts implements Serializable {

    static ArrayList<String> Workouts = new ArrayList<>();
    static String selectedWorkout;


    public static ArrayList<String> getWorkouts() {
        return Workouts;
    }

    public static String getSelectedWorkout () { return selectedWorkout; }

    public static void clearWorkouts() {
        Workouts.clear();
    }

    public static void setWorkouts() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT DISTINCT WorkoutName FROM tblworkout WHERE UserID = " + User.getUserID());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String workoutName = resultSet.getString("WorkoutName");
                Workouts.add(workoutName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setSelectedWorkout(String thisWorkout) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT WorkoutName FROM tblworkout WHERE workoutName = '" + thisWorkout + "'");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                selectedWorkout = resultSet.getString("WorkoutName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
