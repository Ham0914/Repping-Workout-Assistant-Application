package model;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

public class AdminWorkouts implements Serializable {

    static ArrayList<String> adminWorkouts = new ArrayList<>();
    static String selectedAdminWorkout;
    static Boolean check = false;
    static String adminWorkoutName;

    public static String getAdminWorkoutName() { return adminWorkoutName; }
    public static ArrayList<String> getAdminWorkouts() {
        return adminWorkouts;
    }
    public static Boolean getCheck() { return check; }
    public static String getSelectedAdminWorkout () { return selectedAdminWorkout; }
    public static void clearAdminWorkouts() {
        adminWorkouts.clear();
    }


    public static void setAdminWorkoutName(String workoutName) {
        adminWorkoutName = workoutName;
    }

    public static void setAdminWorkouts() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT DISTINCT WorkoutName FROM tbladminworkout");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String workoutName = resultSet.getString("WorkoutName");
                adminWorkouts.add(workoutName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setSelectedAdminWorkout(String thisWorkout) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT WorkoutName FROM tbladminworkout WHERE workoutName = '" + thisWorkout + "'");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                selectedAdminWorkout = resultSet.getString("WorkoutName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void setCheck (String workoutName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT COUNT(WorkoutName) AS count FROM tbladminworkout WHERE WorkoutName =  '" + workoutName + "'");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
                if (count > 0) {
                    check = true;
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
