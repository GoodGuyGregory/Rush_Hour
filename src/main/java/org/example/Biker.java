package org.example;

public class Biker {

    private int rowPosition;
    private int colPosition;
    private int[] goalPosition;
    private boolean goalReached;

    public Biker() {
        this.goalReached = false;
    }

    public Biker(int rowPos, int colPos, int[] goalPos) {
        this.rowPosition = rowPos;
        this.colPosition = colPos;
        this.goalPosition = goalPos;
        this.goalReached = false;
    }

    public int getRowPosition() {
        return this.rowPosition;
    }

    public void setRowPosition(int rowPos) {
        this.rowPosition = rowPos;
    }

    public int getColPosition() {
        return this.colPosition;
    }

    public void setColPosition(int colPos) {
        this.colPosition = colPos;
    }

    public int[] getGoalPosition() {
        return this.goalPosition;
    }

    public void setGoalPosition(int[] goalPosition) {
        this.goalPosition = goalPosition;
    }

    public boolean isGoalReached() {
       return (this.rowPosition == this.goalPosition[0] && this.colPosition == this.goalPosition[1]);
    }

    public void setGoalReached(boolean goalReached) {
        this.goalReached = goalReached;
    }

}