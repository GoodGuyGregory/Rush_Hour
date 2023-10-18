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
    private List<Car> idleCars;


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

    public List<Car> getIdleCars() {
        return idleCars;
    }

    public void setIdleCars(List<Car> idleCars) {
        this.idleCars = idleCars;
    }

    public void printTrafficGrid() {
        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState.length; j++) {
                System.out.print(currentState[i][j]);
            }
            System.out.println();
        }
    }


//    public boolean moveSingleCar(Car car) {
//        if (car.getOrientation() == Orientation.Horizontal) {
//            if (this.horizontalMove(car, parkingLotWidth)) {
//                car.setChecked(true);
//                return true;
//            }
//            else {
//                return false;
//            }
//        }
//        if (car.getOrientation() == Orientation.Vertical) {
//            if (this.verticalMove(car, parkingLotWidth, parkingLotHeight)) {
//                car.setChecked(true);
//                return true;
//            }
//            else {
//                return false;
//            }
//        }
//        return false;
//    }

//    private void moveClosestToBike() {
//        // check right
//        if (this.biker.getColPosition() + 1 <= (this.parkingLotWidth - 1)) {
//            char closestRight = this.currentState[this.biker.getRowPosition()][this.biker.getColPosition() + 1];
//            if (Character.isSpaceChar(closestRight)) {
//                    this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = ' ';
//                    // move the piece and set the new column value
//                    this.biker.setColPosition(this.biker.getColPosition() + 1);
//                    if (this.biker.getColPosition() < this.parkingLotWidth) {
//                        this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = '>';
//                        // collect current state and add to set...
//                        String currentStateKey = this.buildKey(this.currentState);
//                        if (this.notSeenState(currentStateKey)) {
//                            this.collectMovesHash(currentStateKey, this.currentState);
//                        }
//                        // don't move anything revert...
//                        else {
//                            this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = ' ';
//                            this.biker.setColPosition(this.biker.getColPosition() - 1);
//                            this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = '>';
//                        }
//                    }
//
//            } else {
//                Car carFoundRight = this.idleCars.stream().filter(car -> {
//                    if ((car.getRowPosition() == this.biker.getRowPosition()) && (car.getColPosition() == (this.biker.getColPosition() + 1))) {
//                        return true;
//                    } else {
//                        return false;
//                    }
//                }).findFirst().orElse(null);
//
//                if (Objects.nonNull(carFoundRight)) {
//
//                    if (moveSingleCar(carFoundRight) > 0) {
//                        this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = ' ';
//                        this.biker.setColPosition(this.biker.getColPosition() + 1);
//                        if (this.biker.getColPosition() < this.parkingLotWidth) {
//                            this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = '>';
//                            this.movesCounter++;
//                        }
//                    } else {
//                        //check left
//                        if (this.biker.getColPosition() != 0) {
//                            char closestLeft = this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()-1];
//                            if (Character.isSpaceChar(closestLeft)) {
//                                this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = ' ';
//                                // move the piece and set the new column value
//                                this.biker.setColPosition(this.biker.getColPosition() - 1);
//                                if (this.biker.getColPosition() < this.parkingLotWidth) {
//                                    this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = '>';
//                                    this.movesCounter++;
//                                }
//                            } else {
//                                Car carFoundLeft = this.idleCars.stream().filter(car -> {
//                                    if ((car.getRowPosition() == this.biker.getRowPosition()) && (car.getColPosition() == (this.biker.getColPosition()-1))) {
//                                        return true;
//                                    }
//                                    else {
//                                        return false;
//                                    }
//                                }).findFirst().orElse(null);
//
//                                if (Objects.nonNull(carFoundLeft)) {
//                                    if (moveSingleCar(carFoundLeft)>0) {
//                                        this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = ' ';
//                                        this.biker.setColPosition(this.biker.getColPosition() -1);
//                                        if (this.biker.getColPosition() >= 0 ) {
//                                            this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = '>';
//                                            this.movesCounter++;
//                                        }
//                                    }
//                                    else {
//                                        this.movesCounter += moveSingleCar(carFoundLeft);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

}

