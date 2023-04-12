package model;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

public class User implements Serializable {

    public static int userID;
    public static Boolean isAdmin;

    public User(){
        userID = 0;
    }
    static ArrayList<String> userList = new ArrayList<>();
    public static int userIDFromName;
    public static String username;
    public static String originalUsername;

    public static int getUserIDFromName() { return userIDFromName; }
    public static ArrayList<String> getUserList() {
        return userList;
    }
    public static int getUserID() {
        return userID;
    }
    public static Boolean getIsAdmin() { return isAdmin; }
    public static void clearUserList() {
        userList.clear();
    }
    public static String getUsername() { return username; }
    public static String getOriginalUsername() { return originalUsername; }

    public static void setOriginalUsername() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT Username FROM user WHERE UserID = " + getUserID());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                originalUsername = resultSet.getString("Username");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setUsername(String name) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT Username FROM user WHERE Username = '" + name + "'");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                username = resultSet.getString("Username");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void setUserIDFromName(String username) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT UserID FROM user WHERE Username = '" + username + "'");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userIDFromName = resultSet.getInt("UserID");
            }
            System.out.println(userIDFromName);
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void setIsAdmin() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT usertype FROM user WHERE UserID = " + getUserID());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               String userType = resultSet.getString("usertype");
               if (userType.equals("Admin")) {
                   isAdmin = true;
               } else {
                   isAdmin = false;
               }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setUserID(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("SELECT UserID FROM user WHERE Username = '" + username + "'");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userID = resultSet.getInt("UserID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void makeUserIDZero(){
       userID = 0;
    }

    public static void deleteUser(String user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        setUserID(user);
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reppingdatabase", "root", "p2590746");
            preparedStatement = connection.prepareStatement("DELETE FROM tblsets WHERE TheUserID = " + userID);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM tblrecordedworkout WHERE IDofUser = " + userID);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM tblworkout WHERE UserID = " + userID);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM tblexercise WHERE UserIdentification = " + userID);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM user WHERE UserID = " + userID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
