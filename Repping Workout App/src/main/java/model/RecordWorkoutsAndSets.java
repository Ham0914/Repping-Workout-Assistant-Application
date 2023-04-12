package model;

import java.io.Serializable;
import java.sql.*;
import java.util.Date;

public class RecordWorkoutsAndSets implements Serializable {


    public static int recordedWorkoutID;

    public static int getRecordedWorkoutID() { return recordedWorkoutID; }


    public static void insertNewRecordedWorkout(String WorkoutName) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Date currentDate = new Date();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("INSERT INTO tblrecordedworkout (date, WorkoutName, IDofUser) VALUES (NOW(), ?, ?)");
            preparedStatement.setString(1, WorkoutName);
            preparedStatement.setInt(2, User.getUserID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setRecordedWorkoutID() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT RecordedWorkoutID FROM tblrecordedWorkout ORDER BY RecordedWorkoutID DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                recordedWorkoutID = resultSet.getInt("RecordedWorkoutID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void insertNewSet(int exerciseID) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("INSERT INTO tblSets (RecordedWorkoutID, IDofExercise, Weight, Set1Reps, Set2Reps, Set3Reps, TheUserID, SetDate) VALUES ("+ recordedWorkoutID + ", "+ exerciseID + ", 0, 0, 0, 0, " + User.getUserID() + ", NOW())");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void insertNewExerciseSets() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("INSERT INTO tblSets (RecordedWorkoutID, IDofExercise, Weight, Set1Reps, Set2Reps, Set3Reps, TheUserID, SetDate) VALUES (" + RecordWorkoutsAndSets.getRecordedWorkoutID() + ", " + Exercise.getExerciseID() + ", 0, 0, 0, 0, " + User.getUserID() + ", NOW())");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
