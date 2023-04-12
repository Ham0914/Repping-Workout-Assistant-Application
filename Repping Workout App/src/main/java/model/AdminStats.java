package model;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

public class AdminStats implements Serializable {

    static ArrayList<String> weight = new ArrayList<>();
    static ArrayList<String> date = new ArrayList<>();
    static ArrayList<String> userList = new ArrayList<>();
    static int userid;

    public static int getUserid() { return userid; }
    public static ArrayList<String> getWeight() {
        return weight;
    }
    public static ArrayList<String> getDate() {
        return date;
    }
    public static ArrayList<String> getUserList() { return userList; }

    public static void clearWeight() {
        weight.clear();
    }
    public static void clearDate() {
        date.clear();
    }
    public static void clearUserList() {
        userList.clear();
    }

    public static void setUserid(int id) {
        userid = id;
    }

    public static void setDateWeight(int exerciseID, int userid) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT Weight, SetDate FROM tblsets WHERE IDofExercise = " + exerciseID + " AND TheUserID = " + userid);
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


    public static void setUserList() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT Username FROM user WHERE usertype = 'User'" );
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String users = resultSet.getString("Username");
                userList.add(users);
            }
            userList.add(User.getOriginalUsername());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
