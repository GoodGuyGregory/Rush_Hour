package org.example;

public class TrafficState implements Comparable<TrafficState>{

    private char[][] currentState;
    private int movesWeight;

    public char[][] getCurrentState() {
        return currentState;
    }

    public void setCurrentState(char[][] currentState) {
        this.currentState = currentState;
    }

    public int getMovesWeight() {
        return movesWeight;
    }

    public void setMovesWeight(int movesWeight) {
        this.movesWeight = movesWeight;
    }

    public TrafficState() {
    }

    public TrafficState(char[][] currentState) {
        this.currentState = currentState;
    }

    @Override
    public int compareTo(TrafficState o) {
        return 0;
    }
}
