package org.example;

public class Biker {

    private int rowPosition;
    private int colPosition;
    private int[] goalPosition;

    public Biker() {}

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


}