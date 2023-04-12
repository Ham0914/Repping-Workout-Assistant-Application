package model;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CompletedWorkouts implements Serializable {

    static ArrayList<String> completedWorkoutInformation = new ArrayList<>();
    static ArrayList<String> specificWorkout = new ArrayList<>();
    static ArrayList<Integer> setIDFromList = new ArrayList<>();
    static int theRecordedID;
    static ArrayList<String> distinctCompletedWorkoutInformation = new ArrayList<>();


    public static int getRecordedID() { return theRecordedID; }
    public static void clearCompletedWorkoutInformation() { completedWorkoutInformation.clear(); }
    public static void clearDistinctWorkouts() { distinctCompletedWorkoutInformation.clear(); }
    public static void clearSpecificWorkout() { specificWorkout.clear(); }
    public static ArrayList<Integer> getSetIDFromList() { return setIDFromList; }
    public static ArrayList<String> getCompletedWorkoutInformation() { return completedWorkoutInformation; }
    public static ArrayList<String> getSpecificWorkout() { return specificWorkout; }
    public static ArrayList<String> getDistinctCompletedWorkoutInformation() { return distinctCompletedWorkoutInformation; }


    public static void setRecordedID(int recordedID){
        theRecordedID = recordedID;
    }


    public static void setSetIDFromList(int recordedWorkoutID) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT SetID FROM tblsets WHERE RecordedWorkoutID = " + recordedWorkoutID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int tempInt = resultSet.getInt("SetID");
                setIDFromList.add(tempInt);
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }



    public static void setCompletedWorkoutInformation() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT WorkoutName, RecordedWorkoutID, DATE_FORMAT(date, '%W %D %M %Y') AS datty FROM tblrecordedworkout WHERE IDofUser = " + User.getUserID());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String workoutName = resultSet.getString("WorkoutName");
                int recordedID = resultSet.getInt("RecordedWorkoutID");
                String IDstuff = resultSet.getString("RecordedWorkoutID");
                String date = resultSet.getString("datty");
                int j = IDstuff.length();
                if (j == 1) {
                    completedWorkoutInformation.add(recordedID + ":     " + workoutName + " workout completed on " + date);
                } if (j == 2) {
                    completedWorkoutInformation.add(recordedID + ":   " + workoutName + " workout completed on " + date);
                } if (j == 3) {
                    completedWorkoutInformation.add(recordedID + ":  " + workoutName + " workout completed on " + date);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setDistinctCompletedWorkoutInformation() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        distinctCompletedWorkoutInformation.add("All");
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT DISTINCT WorkoutName FROM tblrecordedworkout WHERE IDofUser = " + User.getUserID());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String workout = resultSet.getString("WorkoutName");
                distinctCompletedWorkoutInformation.add(workout);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setSpecificWorkout(String name) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT WorkoutName, RecordedWorkoutID, date FROM tblrecordedworkout WHERE IDofUser = " + User.getUserID() + " AND WorkoutName = '" + name + "'");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String workoutName = resultSet.getString("WorkoutName");
                int recordedID = resultSet.getInt("RecordedWorkoutID");
                String IDstuff = resultSet.getString("RecordedWorkoutID");
                java.sql.Date date = resultSet.getDate("date");
                int j = IDstuff.length();
                if (j == 1) {
                    specificWorkout.add(recordedID + ":    " + workoutName + " workout completed on " + date);
                } if (j == 2) {
                    specificWorkout.add(recordedID + ":  " + workoutName + " workout completed on " + date);
                } if (j == 3) {
                    specificWorkout.add(recordedID + ": " + workoutName + " workout completed on " + date);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
