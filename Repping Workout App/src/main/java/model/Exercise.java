package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

public class Exercise implements Serializable {

    static ArrayList<String> ExerciseList = new ArrayList<String>();
    static ArrayList<Integer> ExerciseListFromWorkout = new ArrayList<Integer>();
    public static int exerciseID;
    public static String newExerciseName;
    public static int newExerciseID;
    public static String chosenExercise;

    public static String getChosenExercise() { return chosenExercise; }
    public static int getNewExerciseID() { return newExerciseID; }
    public static String getNewExerciseName() { return newExerciseName; }
    public static ArrayList<String> getExerciseList() {
        return ExerciseList;
    }
    public static ArrayList<Integer> getExerciseListFromWorkout() {
        return ExerciseListFromWorkout;
    }

    public static void clearExerciseList() { ExerciseList.clear(); }
    public static void clearExerciseListFromWorkout() { ExerciseListFromWorkout.clear(); }


    public static void setChosenExercise(String chosen) {
        chosenExercise = chosen;
    }

    public static void setNewExerciseID(int theExerciseID) {
       newExerciseID = theExerciseID;
       setNewExerciseName();
    }

    public static void setNewExerciseName() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT ExerciseName FROM tblexercise WHERE ExerciseID = " + getNewExerciseID());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                newExerciseName = resultSet.getString("ExerciseName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void setExerciseList() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT DISTINCT ExerciseName FROM tblexercise WHERE UserIdentification IS NULL OR UserIdentification = " + User.getUserID());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String exerciseName = resultSet.getString("ExerciseName");
                ExerciseList.add(exerciseName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void setExerciseID(String ExerciseName) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT ExerciseID FROM tblexercise WHERE ExerciseName = '" + ExerciseName + "'");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                exerciseID = resultSet.getInt("ExerciseID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getExerciseID(){
        return exerciseID;
    }

    public static void setExerciseListFromWorkout(String WorkoutName) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT ExerciseID FROM tblworkout WHERE WorkoutName = '" + WorkoutName + "' AND UserID = " + User.getUserID());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int exerciseID = resultSet.getInt("ExerciseID");
                ExerciseListFromWorkout.add(exerciseID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

