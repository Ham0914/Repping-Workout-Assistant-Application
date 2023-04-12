package model;

import java.sql.*;
import java.util.ArrayList;

public class TempWorkout {

    static ArrayList<String> TempWorkouts = new ArrayList<>();
    static String selectedTempWorkout;


    public static ArrayList<String> getTempWorkouts() {
        return TempWorkouts;
    }

    public static String getSelectedTempWorkout () { return selectedTempWorkout; }

    public static void clearTempWorkouts() {
        TempWorkouts.clear();
    }

    public static void addTempWorkouts(String nameOfWorkout) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer[] array = Exercise.getExerciseListFromWorkout().toArray(new Integer[0]);
        for (int i = 0; i < array.length; i++) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("INSERT INTO tbltempworkout (TempWorkoutName, TempUserID, TempExerciseID) VALUES ('" + nameOfWorkout + "', " + User.getUserID() + ", " + array[i] + ")");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
            }
        }
    }

    public static void addSingleTempWorkout(String nameOfWorkout, int IDofExercise) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
                preparedStatement = connection.prepareStatement("INSERT INTO tbltempworkout (TempWorkoutName, TempUserID, TempExerciseID) VALUES ('" + nameOfWorkout + "', " + User.getUserID() + ", " + IDofExercise + ")");
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    public static void setTempWorkouts() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT DISTINCT TempWorkoutName FROM tbltempworkout WHERE TempUserID = " + User.getUserID());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String workoutName = resultSet.getString("TempWorkoutName");
                TempWorkouts.add(workoutName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setSelectedTempWorkout(String thisWorkout) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT TempWorkoutName FROM tbltempworkout WHERE TempWorkoutName = '" + thisWorkout + "'");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                selectedTempWorkout = resultSet.getString("TempWorkoutName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
