package model;

public class tvStartWorkout {

    private String exerciseName;
    private int weight;
    private int reps1;
    private int reps2;
    private int reps3;

    public tvStartWorkout(String exerciseName, int weight, int reps1, int reps2, int reps3) {
        this.exerciseName = exerciseName;
        this.weight = weight;
        this.reps1 = reps1;
        this.reps2 = reps2;
        this.reps3 = reps3;
    }


    public int getReps1() {
        return reps1;
    }

    public int getReps2() {
        return reps2;
    }

    public int getReps3() {
        return reps3;
    }

    public int getWeight() {
        return weight;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setReps1(int reps1) {
        this.reps1 = reps1;
    }

    public void setReps2(int reps2) {
        this.reps2 = reps2;
    }

    public void setReps3(int reps3) {
        this.reps3 = reps3;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
