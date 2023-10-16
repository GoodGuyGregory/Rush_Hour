package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.Objects;

public class TrafficGrid implements Comparable<TrafficGrid>{

    // create a reference to the currentState
    private int id;
    private int parkingLotWidth;
    private int parkingLotHeight;

    // holds goal state
    private Biker biker;
    // seen elments for the BFS decision tree.
    private char[][] currentState;
    private List<Car> idleCars;
    private int movesCounter;

    public TrafficGrid() {

        this.biker = new Biker();
        this.idleCars = new ArrayList<Car>();
        this.movesCounter = 0;

    }

    public TrafficGrid(int id, int parkingLotWidth, int parkingLotHeight, char[][] currentState) {
        this.id = id;
        this.parkingLotWidth = parkingLotWidth;
        this.parkingLotHeight = parkingLotHeight;
        this.currentState = currentState;
        this.biker = new Biker();
        this.idleCars = new ArrayList<Car>();
        this.movesCounter = 0;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Biker getBiker() {
        return biker;
    }

    public void setBiker(Biker biker) {
        this.biker = biker;
    }

    public List<Car> getIdleCars() {
        return idleCars;
    }

    public void setIdleCars(List<Car> idleCars) {
        this.idleCars = idleCars;
    }

    public int getMovesCounter() {
        return movesCounter;
    }

    public void setMovesCounter(int movesCounter) {
        this.movesCounter = movesCounter;
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
                    goalPos[1] = currentState.length-1;
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
                    Car foundCar = new Car(Orientation.Horizontal, i, j, '-');
                    this.idleCars.add(foundCar);
                }

                if (currentState[i][j] == '|') {
                    Car foundCar = new Car(Orientation.Vertical, i, j,'|');

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

    public TrafficGrid moveCars() {
        int countedMoves = 0;
//        Collections.shuffle(this.idleCars);
        for (Car car : this.idleCars) {
            // first check the biker
            // ensure no out of bounds errors occur
            // moderate Heuristic to closest blocking car
            this.moveClosestToBike();

            if (this.biker.isGoalReached()) {
                this.biker.setGoalReached(true);
                return this;
            }

            if (!car.isChecked()) {
                countedMoves += moveSingleCar(car);
            }
        }
        this.movesCounter = countedMoves;

        return this;

    }

    public int moveSingleCar(Car car) {
        if (car.getOrientation() == Orientation.Horizontal) {
            if (this.horizontalMove(car, parkingLotWidth)) {
                car.setChecked(true);
                return 1;
            }
            else {
                return 0;
            }
        }
        if (car.getOrientation() == Orientation.Vertical) {
            if (this.verticalMove(car, parkingLotWidth, parkingLotHeight)) {
                car.setChecked(true);
                return 1;
            }
            else {
                return 0;
            }
        }
        return 0;
    }

    private void moveClosestToBike() {
        // check right
        if (this.biker.getColPosition() + 1 <= (this.parkingLotWidth - 1)) {
            char closestRight = this.currentState[this.biker.getRowPosition()][this.biker.getColPosition() + 1];
            if (Character.isSpaceChar(closestRight)) {
                this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = ' ';
                // move the piece and set the new column value
                this.biker.setColPosition(this.biker.getColPosition() + 1);
                if (this.biker.getColPosition() < this.parkingLotWidth) {
                    this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = '>';
                    this.movesCounter++;
                }
            } else {
                Car carFoundRight = this.idleCars.stream().filter(car -> {
                    if ((car.getRowPosition() == this.biker.getRowPosition()) && (car.getColPosition() == (this.biker.getColPosition() + 1))) {
                        return true;
                    } else {
                        return false;
                    }
                }).findFirst().orElse(null);

                if (Objects.nonNull(carFoundRight)) {

                    if (moveSingleCar(carFoundRight) > 0) {
                        this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = ' ';
                        this.biker.setColPosition(this.biker.getColPosition() + 1);
                        if (this.biker.getColPosition() < this.parkingLotWidth) {
                            this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = '>';
                            this.movesCounter++;
                        }
                    } else {
                        //check left
                        if (this.biker.getColPosition() != 0) {
                            char closestLeft = this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()-1];
                            if (Character.isSpaceChar(closestLeft)) {
                                this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = ' ';
                                // move the piece and set the new column value
                                this.biker.setColPosition(this.biker.getColPosition() - 1);
                                if (this.biker.getColPosition() < this.parkingLotWidth) {
                                    this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = '>';
                                    this.movesCounter++;
                                }
                            } else {
                                Car carFoundLeft = this.idleCars.stream().filter(car -> {
                                    if ((car.getRowPosition() == this.biker.getRowPosition()) && (car.getColPosition() == (this.biker.getColPosition()-1))) {
                                        return true;
                                    }
                                    else {
                                        return false;
                                    }
                                }).findFirst().orElse(null);

                                if (Objects.nonNull(carFoundLeft)) {
                                    if (moveSingleCar(carFoundLeft)>0) {
                                        this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = ' ';
                                        this.biker.setColPosition(this.biker.getColPosition() -1);
                                        if (this.biker.getColPosition() >= 0 ) {
                                            this.currentState[this.biker.getRowPosition()][this.biker.getColPosition()] = '>';
                                            this.movesCounter++;
                                        }
                                    }
                                    else {
                                        this.movesCounter += moveSingleCar(carFoundLeft);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // keeps code DRY
    public void cleanVertMoveHelper(Car movingCar, String direction) {
        int[] fromSpace = {movingCar.getRowPosition(),movingCar.getColPosition()};
        if (direction.equals("up")) {
            int[] toSpace = {movingCar.getRowPosition()-1,movingCar.getColPosition()};
            this.currentState[toSpace[0]][toSpace[1]] = movingCar.getSymbol();
            movingCar.setRowPosition(movingCar.getRowPosition() - 1);
            this.movesCounter++;
        }
        else {
            int[] toSpace = {movingCar.getRowPosition()+1,movingCar.getColPosition()};
            this.currentState[toSpace[0]][toSpace[1]] = movingCar.getSymbol();
            movingCar.setRowPosition(movingCar.getRowPosition() + 1);
            this.movesCounter++;
        }

        this.currentState[fromSpace[0]][fromSpace[1]] = ' ';

    }

    public void cleanHorizMoveHelper(Car movingCar, String direction) {
        int[] fromSpace = {movingCar.getRowPosition(),movingCar.getColPosition()};
        if (direction.equals("left")) {
            int[] toSpace = {movingCar.getRowPosition(),movingCar.getColPosition()-1};
            this.currentState[toSpace[0]][toSpace[1]] = movingCar.getSymbol();
            movingCar.setColPosition(movingCar.getColPosition() - 1);
        } else {
            int[] toSpace = {movingCar.getRowPosition(),movingCar.getColPosition()+1};
            this.currentState[toSpace[0]][toSpace[1]] = movingCar.getSymbol();
            movingCar.setColPosition(movingCar.getColPosition() + 1);
        }

        this.currentState[fromSpace[0]][fromSpace[1]] = ' ';

    }

    private boolean horizontalMove(Car movingCar, int parkingLotWidth) {
        // look left logic
        // determine the car isn't parked at the edge of a row..
        if (movingCar.getColPosition() != 0 && movingCar.getColPosition() != (parkingLotWidth - 1)) {
            // let's potentially move it
            while (movingCar.getColPosition() != 0) {
                char leftSpace = this.currentState[movingCar.getRowPosition()][movingCar.getColPosition() - 1];
                // check for opening
                if (Character.isSpaceChar(leftSpace)) {
                    // make the move left
                    cleanHorizMoveHelper(movingCar, "left");
                    this.movesCounter++;
                } else {
                    // blocked on left.
                    // check to see if the block can move...
                    Car leftBlockingCar = this.idleCars.stream().filter(car -> {
                        if ((car.getRowPosition() == movingCar.getRowPosition()) && (car.getColPosition() == (movingCar.getColPosition() - 1))) {
                            return true;
                        } else {
                            return false;
                        }
                    }).findFirst().orElse(null);

                    if (Objects.nonNull(leftBlockingCar)) {
                        if (moveSingleCar(leftBlockingCar) > 0) {
                            cleanHorizMoveHelper(movingCar, "left");
                            this.movesCounter++;
                            return true;
                        } else {
                            return false;
                        }
                    }
//                    cannot move further left
                    // check right space
                    // let's potentially move it
                    while (movingCar.getColPosition() != (parkingLotWidth - 1)) {
                        char rightSpace = this.currentState[movingCar.getRowPosition()][movingCar.getColPosition() + 1];
                        // check for opening
                        if (Character.isSpaceChar(rightSpace)) {
                            // make the move right
                            cleanHorizMoveHelper(movingCar, "right");

                        } else {
                            // blocked on right.
                            // check to see if the block can move...
                            Car rightBlockingCar = this.idleCars.stream().filter(car -> {
                                if ((car.getRowPosition() == movingCar.getRowPosition()) && (car.getColPosition() == (movingCar.getColPosition() + 1))) {
                                    return true;
                                } else {
                                    return false;
                                }
                            }).findFirst().orElse(null);

                            if (Objects.nonNull(rightBlockingCar)) {
                                if (moveSingleCar(rightBlockingCar) > 0) {
                                    cleanHorizMoveHelper(movingCar, "right");
                                    this.movesCounter++;
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        else {
            // left most position
            if (movingCar.getColPosition() == 0) {
                // stuck in the last position for left moves..
                // check the right side..
                while (movingCar.getColPosition() != (parkingLotWidth - 1)) {
                    // let's potentially move it
                    char rightSpace = this.currentState[movingCar.getRowPosition()][movingCar.getColPosition() + 1];
                    // check for opening
                    if (Character.isSpaceChar(rightSpace)) {
                        // make the move right
                        cleanHorizMoveHelper(movingCar, "right");
                    } else {
                        // blocked on the right
                        // check to see if the block can move...
                        Car rightBlockingCar = this.idleCars.stream().filter(car -> {
                            if ((car.getRowPosition() == movingCar.getRowPosition()) && (car.getColPosition() == (movingCar.getColPosition() + 1))) {
                                return true;
                            } else {
                                return false;
                            }
                        }).findFirst().orElse(null);

                        if (Objects.nonNull(rightBlockingCar)) {
                            if (moveSingleCar(rightBlockingCar) > 0) {
                                cleanHorizMoveHelper(movingCar, "right");
                                this.movesCounter++;
                            }
                            return false;
                        }
                        return false;
                    }
                }
                return true;
            }
            // Right position look left
            if (movingCar.getColPosition() == (parkingLotWidth - 1)) {
                // stuck in the right position need to look for below moves
                // let's potentially move it
                while (movingCar.getColPosition() != 0) {
                    char leftSpace = this.currentState[movingCar.getRowPosition()][movingCar.getColPosition() - 1];
                    // check for opening
                    if (Character.isSpaceChar(leftSpace)) {
                        cleanHorizMoveHelper(movingCar, "left");

                    } else {
                        // blocked on the left
                        // check to see if the block can move...
                        Car leftBlockingCar = this.idleCars.stream().filter(car -> {
                            if ((car.getRowPosition() == movingCar.getRowPosition()) && (car.getColPosition() == (movingCar.getColPosition() - 1))) {
                                return true;
                            } else {
                                return false;
                            }
                        }).findFirst().orElse(null);

                        if (Objects.nonNull(leftBlockingCar)) {
                            if (moveSingleCar(leftBlockingCar) > 0) {
                                cleanHorizMoveHelper(movingCar, "left");
                                this.movesCounter++;
                            } else {
                                return false;
                            }
                        }
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }


    private boolean verticalMove(Car movingCar, int parkingLotWidth, int parkingLotHeight) {
        // look up logic
        // determine the car isn't parked at the top of the parking lot..
        if (movingCar.getRowPosition() != 0 && movingCar.getRowPosition() != (parkingLotHeight-1)) {

            // let's potentially move it
            while (movingCar.getRowPosition() != 0) {
                char aboveSpace = this.currentState[movingCar.getRowPosition() - 1][movingCar.getColPosition()];
                // check for opening
                if (Character.isSpaceChar(aboveSpace)) {

                    cleanVertMoveHelper(movingCar, "up");
                    return true;
                } else {
                    // blocked above.
                    // check to see if the block can move...
                    Car aboveBlockingCar = this.idleCars.stream().filter(car -> {
                        if ((car.getRowPosition() == (movingCar.getRowPosition() - 1)) && (car.getColPosition() == movingCar.getColPosition())) {
                            return true;
                        } else {
                            return false;
                        }
                    }).findFirst().orElse(null);

                    if (Objects.nonNull(aboveBlockingCar)) {
                        if (moveSingleCar(aboveBlockingCar) > 0) {
                            cleanVertMoveHelper(movingCar, "up");
                            this.movesCounter++;
                            return true;
                        } else {
                            return false;
                        }
                    }

                    // cannot move further up.
                    // check the space below
                    // let's potentially move it down.
                    while (movingCar.getRowPosition() != (parkingLotHeight - 1)) {
                        char downSpace = this.currentState[movingCar.getRowPosition() + 1][movingCar.getColPosition()];
                        // check for opening
                        if (Character.isSpaceChar(downSpace)) {
                            cleanVertMoveHelper(movingCar, "down");
                        } else {
                            //blocked below
                            // check to see if the block can move...
                            Car belowBlockingCar = this.idleCars.stream().filter(car -> {
                                if ((car.getRowPosition() == (movingCar.getRowPosition() + 1)) && (car.getColPosition() == movingCar.getColPosition())) {
                                    return true;
                                } else {
                                    return false;
                                }
                            }).findFirst().orElse(null);

                            if (Objects.nonNull(belowBlockingCar)) {
                                if (moveSingleCar(belowBlockingCar) > 0) {
                                    cleanVertMoveHelper(movingCar, "down");
                                    this.movesCounter++;
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        else {
            // top position only check below
            if (movingCar.getRowPosition() == 0) {
                // stuck in the top position need to look for below moves
                // let's potentially move it
                while (movingCar.getRowPosition() != (parkingLotHeight-1)) {
                    char downSpace = this.currentState[movingCar.getRowPosition() + 1][movingCar.getColPosition()];
                    // check for opening
                    if (Character.isSpaceChar(downSpace)) {
                        cleanVertMoveHelper(movingCar, "down");
                        this.movesCounter++;
                    }
                    else {
                        //blocked below
                        // check to see if the block can move...
                        Car belowBlockingCar = this.idleCars.stream().filter(car -> {
                            if ((car.getRowPosition() == (movingCar.getRowPosition()+1)) && (car.getColPosition() == movingCar.getColPosition())) {
                                return true;
                            } else {
                                return false;
                            }
                        }).findFirst().orElse(null);

                        if (Objects.nonNull(belowBlockingCar)) {
                            if (moveSingleCar(belowBlockingCar) > 0) {
                                cleanVertMoveHelper(movingCar, "down");
                                this.movesCounter++;
                                return true;
                            }
                            else {
                                return false;
                            }
                        }
                        return false;
                    }
                }
                return true;
            }

            // bottom  position look up
            if (movingCar.getRowPosition() == (parkingLotHeight-1)) {
                // stuck in the top position need to look for below moves
                // let's potentially move it
                while (movingCar.getRowPosition() != 0) {
                    char aboveSpace = this.currentState[movingCar.getRowPosition()-1][movingCar.getColPosition()];
                    // check for opening
                    if (Character.isSpaceChar(aboveSpace)) {
                        cleanVertMoveHelper(movingCar,"up");

                    } else {
                        // blocked from the top and bottom.
                        //blocked below
                        // check to see if the block can move...
                        Car aboveBlockingCar = this.idleCars.stream().filter(car -> {
                            if ((car.getRowPosition() == (movingCar.getRowPosition()-1)) && (car.getColPosition() == movingCar.getColPosition())) {
                                return true;
                            } else {
                                return false;
                            }
                        }).findFirst().orElse(null);

                        if (Objects.nonNull(aboveBlockingCar)) {
                            if (moveSingleCar(aboveBlockingCar) > 0) {
                                cleanVertMoveHelper(movingCar, "up");
                                this.movesCounter++;
                                return true;
                            }
                             else {
                                 return false;
                            }
                        }
                        return false;
                    }
                }
                return true;

            }

        }
        return false;
    }


    @Override
    public int compareTo(TrafficGrid o) {
        return Integer.compare(this.id, o.id);
    }
}

