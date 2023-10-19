package org.example;

public class Car {

    private int rowPosition;
    private int colPosition;
    private char symbol;
    private boolean visited;

    public Car() {
    }

    public Car( int rowPos, int colPos, char symbol) {
        this.rowPosition = rowPos;
        this.colPosition = colPos;
        this.symbol = symbol;
        this.visited = false;
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
        return this.symbol;
    }

    public void  setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

}
