package controller;

import model.User;

import java.sql.*;

public class DBSuggestedController {

    static Boolean check = false;

    public static Boolean getCheck() { return check; }


    public static void setCheck (String workoutName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT COUNT(WorkoutName) AS count FROM tblworkout " +
                    "WHERE WorkoutName =  '" + workoutName + "' AND UserID = " + User.getUserID());
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

    public static void addToProfile (String workoutName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement2 = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT AdminsExerciseID FROM tbladminworkout WHERE WorkoutName =  '" + workoutName + "'");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int exercise = resultSet.getInt("AdminsExerciseID");
                preparedStatement2 = connection.prepareStatement("INSERT INTO tblworkout (WorkoutName, UserID, ExerciseID)" +
                        " VALUES ('" + workoutName + "', " + User.getUserID() + ", " + exercise + ")");
                preparedStatement2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteAdminWorkout (String workoutName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("DELETE FROM tbladminworkout WHERE WorkoutName = '"
                    + workoutName + "'");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
