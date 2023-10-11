package org.example;

import java.lang.reflect.Array;
import java.util.List;

public class TrafficGrid {

    // create a reference to the currentState
    private int parkingLotWidth;
    private int parkingLotHeight;
    private char[][] currentState;

    public TrafficGrid() {}

    public TrafficGrid(int parkingLotWidth, int parkingLotHeight, char[][] currentState){
        this.parkingLotWidth = parkingLotWidth;
        this.parkingLotHeight = parkingLotHeight;
        this.currentState = currentState;
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

    public char[][] getCurrentState() {
        return currentState;
    }

    public void setCurrentState(char[][] currentState) {
        this.currentState = currentState;
    }

    public void moveCars() {}



}
