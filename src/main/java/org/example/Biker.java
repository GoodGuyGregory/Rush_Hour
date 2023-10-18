package org.example;

public class Biker {

    private int rowPosition;
    private int colPosition;

    public Biker(int rowPos, int colPos) {
        this.rowPosition = rowPos;
        this.colPosition = colPos;
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


}