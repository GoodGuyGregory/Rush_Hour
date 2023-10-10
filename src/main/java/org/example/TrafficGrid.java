package org.example;

import java.lang.reflect.Array;
import java.util.List;

public class TrafficGrid {

    // create a reference to the currentState
    private int parkingLotSize;
    private Array[][] currentState;
    private List<Car> cars;

    public int getParkingLotSize() {
        return parkingLotSize;
    }

    public void setParkingLotSize(int parkingLotSize) {
        this.parkingLotSize = parkingLotSize;
    }

    public Array[][] getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Array[][] currentState) {
        this.currentState = currentState;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }


    public void initializeBoard() {
    }

    public void moveCars() {}



}
