package org.example;
import java.util.*;
import java.util.List;

public class TrafficGrid {

    // create a reference to the currentState
    private int parkingLotWidth;
    private int parkingLotHeight;
    // holds goal state
    private Biker biker;
    // seen elments for the BFS decision tree.
    private char[][] currentState;
    private List<Car> idleCars;
    private int movesCounter;

    public TrafficGrid() {

    }

    public TrafficGrid(int parkingLotWidth, int parkingLotHeight, char[][] currentState) {
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
    // adds them to a list within the TrafficGrid.

    // major row transversal..
    public void locateCars() {
        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState.length; j++) {

                if (currentState[i][j] == '-') {
                    Car foundCar = new Car(Orientation.Horizontal, i, j);
                    this.idleCars.add(foundCar);
                }

                if (currentState[i][j] == '|') {
                    Car foundCar = new Car(Orientation.Vertical, i, j);
                    this.idleCars.add(foundCar);
                }

            }
        }
    }

    // establishes car counts and their positions
    // establishes biker attributes and goal state
    public void buildParkingLot() {
        locateBiker();
        locateCars();
    }

    // this method will shuffle the cars around based on the list property
    // within the object
    // then maintain a status of the currentState as a new transition model
    public char[][] moveCars() {
        int countedMoves = 0;
        Collections.shuffle(this.idleCars);
        for (Car car : this.idleCars) {
            // first check the biker
            // ensure no out of bounds errors occur
            if (this.biker.getColPosition() + 1 <= this.getParkingLotWidth()) {
                // check for valid biker moves
                if (Character.isSpaceChar(currentState[this.biker.getRowPosition()][this.biker.getColPosition() + 1])) {
                    this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = ' ';
                    // move the piece and set the new column value
                    this.biker.setColPosition(this.biker.getColPosition() + 1);
                    countedMoves += 1;
                }
            }

            if (car.getOrientation() == Orientation.Horizontal) {
                if (this.horizontalMove(car, parkingLotWidth, parkingLotHeight)) {
                    countedMoves += 1;
                } else {
                    continue;
                }
            } else if (car.getOrientation() == Orientation.Vertical) {
                if (this.verticalMove(car, parkingLotWidth, parkingLotHeight)) {
                    countedMoves += 1;
                }
            }
        }
        this.movesCounter = countedMoves;
        return this.currentState;

    }

    private boolean horizontalMove(Car movingCar, int parkingLotWidth, int parkingLotHeight) {
        // look left logic
        // determine the car isn't parked at the edge of a row..
        if (movingCar.getColPosition() != 0) {
            // let's potentially move it
            char leftSpace = this.currentState[movingCar.getRowPosition()][movingCar.getColPosition() - 1];
            // check for opening
            if (Character.isSpaceChar(leftSpace)) {
                this.currentState[movingCar.getRowPosition()][movingCar.getColPosition()] = ' ';
                // make the left move..
                movingCar.setColPosition(movingCar.getColPosition() - 1);
                return true;
            } else {
                // blocked on left.
                // check right space
                if (movingCar.getColPosition() != (this.parkingLotWidth - 1)) {
                    // let's potentially move it
                    char rightSpace = this.currentState[movingCar.getRowPosition()][movingCar.getColPosition() + 1];
                    // check for opening
                    if (Character.isSpaceChar(rightSpace)) {
                        this.currentState[movingCar.getRowPosition()][movingCar.getColPosition()] = ' ';
                        // make the left move..
                        movingCar.setColPosition(movingCar.getColPosition() + 1);
                        return true;
                    } else {
                        // can't move you're stuck
                        return false;
                    }
                }
                return false;
            }
        }
        // in the left most position..
        else {
            // stuck in the last position for left moves..
            // check the right side..
            if (movingCar.getColPosition() != (this.parkingLotWidth - 1)) {
                // let's potentially move it
                char rightSpace = this.currentState[movingCar.getRowPosition()][movingCar.getColPosition() + 1];
                // check for opening
                if (Character.isSpaceChar(rightSpace)) {
                    this.currentState[movingCar.getRowPosition()][movingCar.getColPosition()] = ' ';
                    // make the right move..
                    movingCar.setColPosition(movingCar.getColPosition() + 1);
                    return true;
                } else {
                    // blocked on the right
                    return false;
                }
            }
            return false;
        }
    }

    private boolean verticalMove(Car movingCar, int parkingLotWidth, int parkingLotHeight) {
        // look up logic
        // determine the car isn't parked at the top of the parking lot..
        if (movingCar.getRowPosition() != 0) {
            // let's potentially move it
            char aboveSpace = this.currentState[movingCar.getRowPosition() + 1][movingCar.getColPosition()];
            // check for opening
            if (Character.isSpaceChar(aboveSpace)) {
                this.currentState[movingCar.getRowPosition()][movingCar.getColPosition()] = ' ';
                // make the up move..
                movingCar.setRowPosition(movingCar.getRowPosition() - 1);
                return true;
            } else {
                // cannot move further up.
                // check the space below
                if (movingCar.getRowPosition() != (this.parkingLotHeight - 1)) {
                    // let's potentially move it down.
                    char downSpace = this.currentState[movingCar.getRowPosition() + 1][movingCar.getColPosition()];
                    // check for opening
                    if (Character.isSpaceChar(downSpace)) {
                        this.currentState[movingCar.getRowPosition()][movingCar.getColPosition()] = ' ';
                        // make the downard move..
                        movingCar.setRowPosition(movingCar.getRowPosition() - 1);
                        return true;
                    } else {
                        // can't move you're stuck at the bottom
                        return false;
                    }
                }
                return false;
            }
        }
        // in the left most position..
        else {
            // stuck in the top position need to look for below moves..
            // let's potentially move it
            char belowSpace = this.currentState[movingCar.getRowPosition() - 1][movingCar.getColPosition()];
            // check for opening
            if (Character.isSpaceChar(belowSpace)) {
                this.currentState[movingCar.getRowPosition()][movingCar.getColPosition()] = ' ';
                // make the left move..
                movingCar.setRowPosition(movingCar.getRowPosition() - 1);
                return true;
            } else {
                // blocked from the top and bottom.
                return false;
            }
        }
    }

}
