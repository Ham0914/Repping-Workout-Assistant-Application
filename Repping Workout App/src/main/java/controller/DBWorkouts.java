package controller;

import model.RecordWorkoutsAndSets;
import model.User;

import java.sql.*;

public class DBWorkouts {


    public static void deleteWorkout (String workoutName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("DELETE FROM tblworkout WHERE WorkoutName = '"
                    + workoutName + "' AND UserID = " + User.getUserID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteTempWorkouts () {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("DELETE FROM tbltempworkout");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        public static void deleteSets() {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
                preparedStatement = connection.prepareStatement("DELETE FROM tblsets WHERE RecordedWorkoutID =" + RecordWorkoutsAndSets.getRecordedWorkoutID());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    public static void deleteRecordedWorkouts() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("DELETE FROM tblrecordedworkout WHERE RecordedWorkoutID =" + RecordWorkoutsAndSets.getRecordedWorkoutID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteRecordedID(int recordedID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement2 = connection.prepareStatement("DELETE FROM tblsets WHERE RecordedWorkoutID = " + recordedID);
            preparedStatement2.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM tblrecordedworkout WHERE RecordedWorkoutID = " + recordedID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
