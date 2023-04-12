package model;

import java.io.Serializable;
import java.sql.*;

public class CompletedSets implements Serializable {

    public static int weight;
    public static int set1Reps;
    public static int set2Reps;
    public static int set3Reps;

    public static int getWeight() { return weight; }
    public static int getSet1Reps() { return set1Reps; }
    public static int getSet2Reps() { return set2Reps; }
    public static int getSet3Reps() { return set3Reps; }

    public static void setWeight() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT Weight FROM tblsets WHERE RecordedWorkoutID = " + CompletedWorkouts.getRecordedID() + " AND IDofExercise = " + Exercise.getNewExerciseID());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                weight = resultSet.getInt("Weight");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setSet1Reps() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT Set1Reps FROM tblsets WHERE RecordedWorkoutID = " + CompletedWorkouts.getRecordedID() + " AND IDofExercise = " + Exercise.getNewExerciseID());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                set1Reps = resultSet.getInt("Set1Reps");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setSet2Reps() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT Set2Reps FROM tblsets WHERE RecordedWorkoutID = " + CompletedWorkouts.getRecordedID() + " AND IDofExercise = " + Exercise.getNewExerciseID());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                set2Reps = resultSet.getInt("Set2Reps");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setSet3Reps() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT Set3Reps FROM tblsets WHERE RecordedWorkoutID = " + CompletedWorkouts.getRecordedID() + " AND IDofExercise = " + Exercise.getNewExerciseID());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                set3Reps = resultSet.getInt("Set3Reps");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
