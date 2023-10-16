package org.example;

public class Car {

    private Orientation orientation;
    private int rowPosition;
    private int colPosition;
    private char symbol;
    private boolean checked;

    public Car() {
    }

    public Car(Orientation orientation, int rowPos, int colPos, char symbol) {
        this.orientation = orientation;
        this.rowPosition = rowPos;
        this.colPosition = colPos;
        this.symbol = symbol;
        this.checked = false;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPos) {
        this.rowPosition =rowPos;
    }

    public int getColPosition() {
        return colPosition;
    }

    public void setColPosition(int colPos) {
        this.colPosition = colPos;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
