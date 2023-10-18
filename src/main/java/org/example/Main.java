package org.example;


import java.io.FileNotFoundException;
import java.util.*;


public class Main {
    public static Queue<TrafficState> trafficStates = new PriorityQueue<TrafficState>();
    public static HashMap<String, TrafficState> previousStates = new HashMap<String, TrafficState>();


    public static TrafficState moveVehicle(TrafficState trafficState, int parkingWidth, int parkingHeight) {
        // iterate through the parkingLot
        char[][] parkingLot = trafficState.getCurrentState();
        for (int i = 0; i < parkingHeight; i++) {
            for (int j = 0; j < parkingHeight; j++) {
                // if vertical Car...
                // add all vertical Car logic.
                if (parkingLot[i][j] == '|') {
                    Car parkedCar = new Car(i, j, '|');
                    if (verticalMove(parkedCar, parkingWidth, parkingLot)) {
                        trafficState.setCurrentState(parkingLot);
                        trafficState.setMovesWeight(trafficState.getMovesWeight()+1);
                        return trafficState;
                    }
                }
                // if horizontal Car...
                // add all horizontal logic..
                if (parkingLot[i][j] == '-' || parkingLot[i][j] == '>') {
                    if (parkingLot[i][j] == '-') {
                        Car parkedCar = new Car(i, j, '-');
                        if (horizontalMove(parkedCar, parkingWidth, parkingLot)) {
                            trafficState.setCurrentState(parkingLot);
                            trafficState.setMovesWeight(trafficState.getMovesWeight()+1);
                            return trafficState;
                        }

                    }
                    if (parkingLot[i][j] == '>') {
                        Car parkedCar = new Car(i, j, '>');
                        if (horizontalMove(parkedCar, parkingWidth, parkingLot)) {
                            trafficState.setCurrentState(parkingLot);
                            trafficState.setMovesWeight(trafficState.getMovesWeight()+1);
                            return trafficState;
                        }
                    }
                }
            }
        }
        trafficState.setCurrentState(parkingLot);
        return trafficState;
    }

    public static boolean horizontalMove(Car movingCar, int parkingLotWidth, char[][] currentState) {
        // look left logic
        // determine the car isn't parked at the edge of a row..
        if (movingCar.getColPosition() != 0 && movingCar.getColPosition() != (parkingLotWidth - 1)) {
            // let's potentially move it
            char leftSpace = currentState[movingCar.getRowPosition()][movingCar.getColPosition() - 1];
            // check for opening
            if (Character.isSpaceChar(leftSpace)) {
                // make the move left
                if (cleanHorizMoveHelper(movingCar, "left", currentState)) {
                    return true;
                }
                else {
                    return false;
                }
            } else {
                // blocked on left.
                // check to see if the block can move...
//                    cannot move further left
                // check right space
                // let's potentially move it
                char rightSpace = currentState[movingCar.getRowPosition()][movingCar.getColPosition() + 1];
                // check for opening
                if (Character.isSpaceChar(rightSpace)) {
                    // make the move right
                    if (cleanHorizMoveHelper(movingCar, "right", currentState)) {
                        return true;
                    }
                    else {
                        return false;
                    }
                } else {
                    // blocked on right.
                    // check to see if the block can move...
                    /*Car rightBlockingCar = this.idleCars.stream().filter(car -> {
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
                    }*/
                    return false;
                }
            }
        } else {
            // left most position
            if (movingCar.getColPosition() == 0) {
                // stuck in the last position for left moves..
                // check the right side..
                // let's potentially move it
                char rightSpace = currentState[movingCar.getRowPosition()][movingCar.getColPosition() + 1];
                // check for opening
                if (Character.isSpaceChar(rightSpace)) {
                    // make the move right
                    if (cleanHorizMoveHelper(movingCar, "right", currentState)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    // blocked on the right
                    // check to see if the block can move...
//                        Car rightBlockingCar = this.idleCars.stream().filter(car -> {
//                            if ((car.getRowPosition() == movingCar.getRowPosition()) && (car.getColPosition() == (movingCar.getColPosition() + 1))) {
//                                return true;
//                            } else {
//                                return false;
//                            }
//                        }).findFirst().orElse(null);
//
//                        if (Objects.nonNull(rightBlockingCar)) {
//                            if (moveSingleCar(rightBlockingCar) > 0) {
//                                cleanHorizMoveHelper(movingCar, "right");
//                                this.movesCounter++;
//                            }
                    return false;
                }
            }
        }
        // Right position look left
        if (movingCar.getColPosition() == (parkingLotWidth - 1)) {
            // stuck in the right position need to look for below moves
            // let's potentially move it
            char leftSpace = currentState[movingCar.getRowPosition()][movingCar.getColPosition() - 1];
            // check for opening
            if (Character.isSpaceChar(leftSpace)) {
                if (cleanHorizMoveHelper(movingCar, "left", currentState)) {
                    return true;
                }
                else {
                    return false;
                }
            } else {
                // blocked on the left
                // check to see if the block can move...
//                    Car leftBlockingCar = thiidleCars.stream().filter(car -> {
//                        if ((car.getRowPosition() == movingCar.getRowPosition()) && (car.getColPosition() == (movingCar.getColPosition() - 1))) {
//                            return true;
//                        } else {
//                            return false;
//                        }
//                    }).findFirst().orElse(null);
//
//                    if (Objects.nonNull(leftBlockingCar)) {
//                        if (moveSingleCar(leftBlockingCar) > 0) {
//                            cleanHorizMoveHelper(movingCar, "left",currentState);
//                            this.movesCounter++;
//                        } else {
//                            return false;
//                        }
//                    }
                return false;
            }
        }

        return false;
    }


    public static boolean verticalMove(Car movingCar, int parkingLotHeight, char[][] currentState) {
        // look up logic
        // determine the car isn't parked at the top of the parking lot..
        if (movingCar.getRowPosition() != 0 && movingCar.getRowPosition() != (parkingLotHeight - 1)) {

            // let's potentially move it
            char aboveSpace = currentState[movingCar.getRowPosition() - 1][movingCar.getColPosition()];
            // check for opening
            if (Character.isSpaceChar(aboveSpace)) {
                if (cleanVertMoveHelper(movingCar, "up", currentState)) {
                    return true;
                }
                else {
                    return false;
                }
            } else {
                //todo:
                // blocked above.
                // check to see if the block can move...

                // cannot move further up.
                // check the space below
                // let's potentially move it down.
                //
                char downSpace = currentState[movingCar.getRowPosition() + 1][movingCar.getColPosition()];
                // check for opening
                if (Character.isSpaceChar(downSpace)) {
                    if (cleanVertMoveHelper(movingCar, "down", currentState)) {
                        return true;
                    }
                    else {
                        return false;
                    }
                } else {
//                    //blocked below
//                    // check to see if the block can move...
//
                    return false;
                }
            }
        } else {
            // top position only check below
            if (movingCar.getRowPosition() == 0) {
                // stuck in the top position need to look for below moves
                // let's potentially move it
                char downSpace = currentState[movingCar.getRowPosition() + 1][movingCar.getColPosition()];
                // check for opening
                if (Character.isSpaceChar(downSpace)) {
                    if ( cleanVertMoveHelper(movingCar, "down", currentState)) {
                        return true;
                    }
                    else {
                        return false;
                    }
                } else {
                    //blocked below
                    // check to see if the block can move...
                    return false;
                }
            }
        }
        // bottom  position look up
        if (movingCar.getRowPosition() == (parkingLotHeight - 1)) {
            // stuck in the top position need to look for below moves
            // let's potentially move it
            char aboveSpace = currentState[movingCar.getRowPosition() - 1][movingCar.getColPosition()];
            // check for opening
            if (Character.isSpaceChar(aboveSpace)) {
                if (cleanVertMoveHelper(movingCar, "up", currentState)) {
                    return true;
                }
                else {
                    return false;
                }
            } else {
                return false;
//                        // blocked from the top and bottom.
//                        //blocked below
//                        // check to see if the block can move...
////                        Car aboveBlockingCar = this.idleCars.stream().filter(car -> {
////                            if ((car.getRowPosition() == (movingCar.getRowPosition()-1)) && (car.getColPosition() == movingCar.getColPosition())) {
////                                return true;
////                            } else {
////                                return false;
////                            }
////                        }).findFirst().orElse(null);
////
////                        if (Objects.nonNull(aboveBlockingCar)) {
////                            if (moveSingleCar(aboveBlockingCar) > 0) {
////                                cleanVertMoveHelper(movingCar, "up");
////                                this.movesCounter++;
////                                return true;
////                            }

////                        }
            }
        }
        return false;
    }

    public static boolean cleanHorizMoveHelper(Car movingCar, String direction, char[][] currentState) {
        int[] fromSpace = {movingCar.getRowPosition(), movingCar.getColPosition()};
        if (direction.equals("left")) {
            int[] toSpace = {movingCar.getRowPosition(), movingCar.getColPosition() - 1};
            currentState[toSpace[0]][toSpace[1]] = movingCar.getSymbol();
            movingCar.setColPosition(movingCar.getColPosition() - 1);
        } else {
            int[] toSpace = {movingCar.getRowPosition(), movingCar.getColPosition() + 1};
            currentState[toSpace[0]][toSpace[1]] = movingCar.getSymbol();
            movingCar.setColPosition(movingCar.getColPosition() + 1);
        }

        currentState[fromSpace[0]][fromSpace[1]] = ' ';
        String hashKey = buildKey(currentState);
        if (!seenState(hashKey)) {
            collectMovesHash(hashKey, currentState);
            return true;
        }
        // revert the logic
        else {
            // moves things back...
            if (direction.equals("left")) {
                int[] toSpace = {movingCar.getRowPosition(), movingCar.getColPosition()};
                currentState[toSpace[0]][toSpace[1]] = ' ';
                // put the car back
                currentState[fromSpace[0]][fromSpace[1]] = movingCar.getSymbol();
                movingCar.setRowPosition(fromSpace[0]);
                movingCar.setColPosition(fromSpace[1]);
                return false;
            } else {
                int[] toSpace = {movingCar.getRowPosition(), movingCar.getColPosition()};
                currentState[toSpace[0]][toSpace[1]] = ' ';
                // put the car back
                currentState[fromSpace[0]][fromSpace[1]] = movingCar.getSymbol();
                movingCar.setRowPosition(fromSpace[0]);
                movingCar.setColPosition(fromSpace[1]);
                return false;
            }
        }
    }




    public static boolean cleanVertMoveHelper(Car movingCar, String direction, char[][] currentState) {
        int[] fromSpace = {movingCar.getRowPosition(), movingCar.getColPosition()};
        if (direction.equals("up")) {
            int[] toSpace = {movingCar.getRowPosition() - 1, movingCar.getColPosition()};
            currentState[toSpace[0]][toSpace[1]] = movingCar.getSymbol();
            movingCar.setRowPosition(movingCar.getRowPosition() - 1);
        } else {
            int[] toSpace = {movingCar.getRowPosition() + 1, movingCar.getColPosition()};
            currentState[toSpace[0]][toSpace[1]] = movingCar.getSymbol();
            movingCar.setRowPosition(movingCar.getRowPosition() + 1);
        }

        currentState[fromSpace[0]][fromSpace[1]] = ' ';
        String hashKey = buildKey(currentState);
        if (!seenState(hashKey)) {
            collectMovesHash(hashKey, currentState);
            return true;
        } else {
            // moves things back...
            if (direction.equals("up")) {
                // replace the car's coordinates with a ' '
                int[] toSpace = {movingCar.getRowPosition() + 1, movingCar.getColPosition()};
                currentState[toSpace[0]][toSpace[1]] = ' ';
                // put the car back
                currentState[fromSpace[0]][fromSpace[1]] = movingCar.getSymbol();
                movingCar.setRowPosition(fromSpace[0]);
                movingCar.setColPosition(fromSpace[1]);
                return false;
            } else {
                int[] toSpace = {movingCar.getRowPosition(), movingCar.getColPosition()};
                currentState[toSpace[0]][toSpace[1]] = ' ';
                // put the car back
                currentState[fromSpace[0]][fromSpace[1]] = movingCar.getSymbol();
                movingCar.setRowPosition(fromSpace[0]);
                movingCar.setColPosition(fromSpace[1]);
                return false;
            }
        }
    }

    public static String buildKey(char[][] providedState) {
        StringBuilder currentStateStr = new StringBuilder();
        for (int i = 0; i < providedState.length; i++) {
            for (int j = 0; j < providedState.length; j++) {
                currentStateStr.append(providedState[i][j]);
            }
        }
        return currentStateStr.toString();
    }


    public static boolean seenState(String trafficKey) {
        return previousStates.containsKey(trafficKey);
    }

    public static void collectMovesHash(String currentStateKey, char[][] providedState) {
        previousStates.put(currentStateKey, new TrafficState(providedState));
    }

    public static void determineGoalState(TrafficGrid grid) {
        char[][] initialState = grid.getCurrentState();
        for (int i = 0; i < grid.getParkingLotWidth(); i++) {
            for (int j = 0; j < grid.getParkingLotHeight(); j++) {
                if (initialState[i][j] == '>') {
                    int[] goalCoordinates = {(i),(grid.getParkingLotHeight()-1)};
                    grid.setGoalState(goalCoordinates);
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        int parkingLotWidth = 0;
        int parkingLotHeight = 0;


        // get the users requested puzzle
        try {
            String requestedFile = System.getProperty("user.dir") + args[0];
            try {
                BugRushReader rushReader = new BugRushReader();
                rushReader.readBugRushFile(requestedFile);
                TrafficGrid trafficGrid = rushReader.getInitialState();
                parkingLotWidth = trafficGrid.getParkingLotWidth();
                parkingLotHeight = trafficGrid.getParkingLotHeight();

                determineGoalState(trafficGrid);



                TrafficState initialState = new TrafficState(trafficGrid.getCurrentState());
                // add to the queue
                trafficStates.add(initialState);
                // add to the steps to prevent back stepping
                String hashKey = buildKey(initialState.getCurrentState());
                if (!seenState(hashKey)) {
                    collectMovesHash(hashKey, initialState.getCurrentState());
                }

                int[] goalStateCoords = trafficGrid.getGoalState();

                while (trafficStates.size() > 0) {
                    TrafficState currentTrafficGrid = trafficStates.remove();

                    char[][] currentState = currentTrafficGrid.getCurrentState();
                    if (checkGoalClear(currentState, parkingLotWidth, parkingLotHeight)) {
                        // found the way...
                        System.out.println("solved in "+ trafficGrid.getMovesMade() + " moves");
                        break;
                    }
                    else {
                        TrafficState transitionState = moveVehicle(currentTrafficGrid, trafficGrid.getParkingLotWidth(), trafficGrid.getParkingLotHeight());
                        trafficStates.add(transitionState);
                        trafficGrid.setMovesMade(transitionState.getMovesWeight());
                    }
                }



            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(args[0] + " Not Found");
            }

        }
        catch (ArrayIndexOutOfBoundsException arrayOutBounds) {
            System.out.println("provide a .bugs file as an argument...");
        }



    }

    private static boolean checkGoalClear(char[][] currentState, int parkWidth, int parkHeight) {
        // count backwards and determine if the path is clear to stop wasted moves..
        for (int i = 0; i < parkWidth; i++) {
            for (int j = 0; j < parkHeight; j++) {
                if (currentState[i][j] == '>') {
                    int k = 0;
                    while (k < parkWidth-1) {
                        if (currentState[i][k+1] == ' ') {
                           k++;
                        }
                        else {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}