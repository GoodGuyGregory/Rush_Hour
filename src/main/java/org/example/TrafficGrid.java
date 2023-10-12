package org.example;

import java.lang.reflect.Array;
import java.util.List;

public class TrafficGrid {

    // create a reference to the currentState
    private int parkingLotWidth;
    private int parkingLotHeight;
    // holds goal state 
    private Biker biker;
    private char[][] currentState;
    private List<Car> idleCars;
    private int movesCounter;

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

    public void printTrafficGrid() {
        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState.length; j++) {
                System.out.print(currentState[i][j]);
            }
            System.out.println();
        }
    }

    // locates the Biker item
    // locates the goalstate of the biker
    public void locateBiker() {
            int[] coordinates = new int[2];
            for (int i = 0; i < currentState.length; i++) {
                for (int j = 0; j < currentState.length; j++) {
                    if (currentState[i][j] == '>') {
                        coordinates[0] = i;
                        coordinates[1] = j;
                        this.biker.setRowPosition(i);
                        this.biker.setColPosition(j);
                        int[] goalPos = new int[2];
                        goalPos[0] = i;
                        goalPos[1] = currentState.length;
                        this.biker.setGoalPosition(goalPos);
                }
                
            }
        }
    }

    // locates cars and establishes their attributes.
    // adds them to a list within the TrafficGrid
        public void locateCars() {
            for (int i = 0; i < currentState.length; i++) {
                for (int j = 0; j < currentState.length; j++) {
        
                    if (currentState[i][j] == '-') {
                        Car foundCar = new Car(Orientation.Horizontal,i,j);
                        this.idleCars.add(foundCar);
                    }
                    
                    if (currentState[i][j] == '|') {
                        Car foundCar = new Car(Orientation.Vertical,i,j);
                        this.idleCars.add(foundCar);
                    }
                
            }
        }
    }

        // establishes car counts and their positions
        // establishes biker attributes and goal state 
    public void buildParkingLot(){

        locateBiker();
        locateCars();
    
    }

    public void moveCars() {}



}
