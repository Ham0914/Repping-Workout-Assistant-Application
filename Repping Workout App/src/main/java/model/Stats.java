package model;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

public class Stats implements Serializable {

    static ArrayList<String> weight = new ArrayList<>();
    static ArrayList<String> date = new ArrayList<>();


    public static ArrayList<String> getWeight() {
        return weight;
    }
    public static ArrayList<String> getDate() {
        return date;
    }

    public static void clearWeight() {
        weight.clear();
    }
    public static void clearDate() {
        date.clear();
    }

    public static void setDateWeight(int exerciseID) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT Weight, SetDate FROM tblsets WHERE TheUserID = " + User.getUserID() + " AND IDofExercise = " + exerciseID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String theWeight = resultSet.getString("Weight");
                java.sql.Date theDate = resultSet.getDate("SetDate");
                weight.add(theWeight);
                date.add(theDate.toString());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
