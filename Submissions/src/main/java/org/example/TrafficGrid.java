package org.example;

import java.lang.reflect.Array;
import java.util.*;

public class TrafficGrid{

    // create a reference to the currentState
    private int parkingLotWidth;
    private int parkingLotHeight;
    // seen elements for the BFS decision tree.
    private char[][] currentState;
    private int[] goalState;
    private int movesMade;



    public TrafficGrid() {
        this.movesMade = 0;
    }

    public TrafficGrid(int id, int parkingLotWidth, int parkingLotHeight, char[][] currentState) {
        this.parkingLotWidth = parkingLotWidth;
        this.parkingLotHeight = parkingLotHeight;
        this.currentState = currentState;
        this.movesMade = 0;
    }

    public int getParkingLotWidth() {
        return parkingLotWidth;
    }

    public void setParkingLotWidth(int parkingLotWidth) {
        this.parkingLotWidth = parkingLotWidth;
    }

    public int getParkingLotHeight() {
        return parkingLotHeight;
    }

    public void setParkingLotHeight(int parkingLotHeight) {
        this.parkingLotHeight = parkingLotHeight;
    }

    public int getMovesMade() {
        return movesMade;
    }

    public void setMovesMade(int movesMade) {
        this.movesMade = movesMade;
    }

    public char[][] getCurrentState() {
        return currentState;
    }

    public void setCurrentState(char[][] currentState) {
        this.currentState = currentState;
    }

    public int[] getGoalState() {
        return goalState;
    }

    public void setGoalState(int[] goalState) {
        this.goalState = goalState;
    }


}

