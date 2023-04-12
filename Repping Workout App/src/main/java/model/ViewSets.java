package model;

import java.io.Serializable;
import java.sql.*;

public class ViewSets implements Serializable {

    public static int weight = 0;
    public static int set1Reps = 0;
    public static int set2Reps = 0;
    public static int set3Reps = 0;

    public static int getWeight() { return weight; }
    public static int getSet1Reps() { return set1Reps; }
    public static int getSet2Reps() { return set2Reps; }
    public static int getSet3Reps() { return set3Reps; }

    public static void setWeight() {
        weight = 0;
    }

    public static void setSet1Reps() {
        set1Reps = 0;
    }

    public static void setSet2Reps() {
        set2Reps = 0;
    }

    public static void setSet3Reps() {
        set3Reps = 0;
    }

}
