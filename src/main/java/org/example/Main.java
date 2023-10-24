package org.example;


import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;


public class Main {
    public static Queue<TrafficState> trafficStates = new ArrayDeque<TrafficState>();
    public static HashMap<String, TrafficState> previousStates = new HashMap<String, TrafficState>();
    public static TrafficGrid initialGrid = null;

    public static int movesToSolve = 0;

    public static char[][] initialParkingLot(char[][] intialLot) {
        char[][] deepCopyParkingLot = new char[intialLot.length][intialLot[0].length];
        for (int i = 0; i < intialLot.length; i++) {
            for (int j = 0; j < intialLot[0].length; j++) {
                deepCopyParkingLot[i][j] = intialLot[i][j];
            }
        }
        return deepCopyParkingLot;
    }

    // Padma Lochan's Answer
    //https://www.quora.com/What-is-the-right-way-to-deep-copy-an-object-in-Java-How-do-you-do-it-in-your-code
    public static char[][] deepClone(char[][] initialLot) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(initialLot);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            return (char[][]) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static List<Car> deepCloneCars(List<Car> intialList) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(intialList);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            return (List<Car>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void getNextStates(TrafficState trafficState, int parkingWidth, int parkingHeight) {
        // iterate through the parkingLot

        // check if the current state is in the closed list...
        // if the weight is better on this current state swap the closed list reference
        char[][] parkingLot = trafficState.getCurrentState();
        char[][] transitionalLot = deepClone(parkingLot);
        for (Car idleCar : trafficState.getIdleCars()) {
            // if vertical Car...
            // add all vertical Car logic.
            if (idleCar.getSymbol() == '|') {
                TrafficState transitionalState = new TrafficState();
                transitionalState.setMovesWeight(trafficState.getMovesWeight());
                if (verticalMove(transitionalState,idleCar, parkingHeight, transitionalLot, trafficState.getIdleCars())) {
                    transitionalLot = deepClone(parkingLot);
                }
            }
            // if horizontal Car...
            // add all horizontal logic..
            if (idleCar.getSymbol() == '>' || idleCar.getSymbol() == '-') {
                TrafficState transitionalState = new TrafficState();
                transitionalState.setMovesWeight(trafficState.getMovesWeight());
                if (horizontalMove(transitionalState, idleCar, parkingWidth, transitionalLot, trafficState.getIdleCars())) {
                    transitionalLot = deepClone(parkingLot);
                }
            }
        }
    }


    public static boolean horizontalMove(TrafficState trafficState,Car movingCar, int parkingLotWidth, char[][] currentState, List<Car> idleCars) {

        // look left logic
        // determine the car isn't parked at the edge of a row
        // middle scenario
        if (movingCar.getColPosition() != 0 && movingCar.getColPosition() != (parkingLotWidth - 1)) {
            // let's potentially move it
            char leftSpace = currentState[movingCar.getRowPosition()][movingCar.getColPosition() - 1];
            // check for opening
            if (Character.isSpaceChar(leftSpace)) {
                // make the move left
                if (cleanHorizMoveHelper(trafficState,movingCar, "left", currentState)) {
                    return true;
                }
                return false;
            } else {
                // blocked on left.
                // check to see if the block can move...
                Car leftBlockingCar = idleCars.stream().filter(car -> {
                    if ((car.getRowPosition() == movingCar.getRowPosition()) && (car.getColPosition() == (movingCar.getColPosition() - 1))) {
                        return true;
                    } else {
                        return false;
                    }
                }).findFirst().orElse(null);

                if (Objects.nonNull(leftBlockingCar) && !leftBlockingCar.isVisited()) {
                    leftBlockingCar.setVisited(true);
                    if (moveSingleCar(trafficState,leftBlockingCar, currentState, idleCars)) {
                        //printTrafficGrid(currentState, idleCars);
                        return true;
                    }
                    else {

                        return false;
                    }
                }
            }
            // check right space
            // let's potentially move it
            char rightSpace = currentState[movingCar.getRowPosition()][movingCar.getColPosition() + 1];
            // check for opening
            if (Character.isSpaceChar(rightSpace)) {
                // make the move right
                return cleanHorizMoveHelper(trafficState,movingCar, "right", currentState);
            } else {
                // blocked on right.
                // check to see if the block can move...
                Car rightBlockingCar = idleCars.stream().filter(car -> {
                    return (car.getRowPosition() == movingCar.getRowPosition()) && (car.getColPosition() == (movingCar.getColPosition() + 1));
                }).findFirst().orElse(null);

                if (Objects.nonNull(rightBlockingCar) && !rightBlockingCar.isVisited()) {
                    rightBlockingCar.setVisited(true);
                    if (moveSingleCar(trafficState,rightBlockingCar, currentState, idleCars)) {
                        //printTrafficGrid(currentState, idleCars);
                        return true;
                    } else {

                        return false;
                    }
                }
                return false;
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
                    if (cleanHorizMoveHelper(trafficState,movingCar, "right", currentState)) {
                        //printTrafficGrid(currentState, idleCars);
                        return true;
                    }
                    else {
                        return false;
                    }
                } else {
                    // blocked on the right
                    // check to see if the block can move...
                    Car rightBlockingCar = idleCars.stream().filter(car -> {
                        return (car.getRowPosition() == movingCar.getRowPosition()) && (car.getColPosition() == (movingCar.getColPosition() + 1));
                    }).findFirst().orElse(null);

                    if (Objects.nonNull(rightBlockingCar) && !rightBlockingCar.isVisited()) {
                        rightBlockingCar.setVisited(true);
                        if (moveSingleCar(trafficState,rightBlockingCar, currentState, idleCars)) {
                            //printTrafficGrid(currentState, idleCars);
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                }

                return false;
            }
        }

        // Right position look left
        if (movingCar.getColPosition() == (parkingLotWidth - 1)) {
            // stuck in the right position need to look for below moves
            // let's potentially move it
            char leftSpace = currentState[movingCar.getRowPosition()][movingCar.getColPosition() - 1];
            // check for opening
            if (Character.isSpaceChar(leftSpace)) {
                return cleanHorizMoveHelper(trafficState,movingCar, "left", currentState);
            } else {
                // blocked on the left
                // check to see if the block can move...
                Car leftBlockingCar = idleCars.stream().filter(car -> {
                    return (car.getRowPosition() == movingCar.getRowPosition()) && (car.getColPosition() == (movingCar.getColPosition() - 1));
                }).findFirst().orElse(null);

                if (Objects.nonNull(leftBlockingCar) && !leftBlockingCar.isVisited()) {
                    leftBlockingCar.setVisited(true);
                    if (moveSingleCar(trafficState,leftBlockingCar, currentState, idleCars)) {
                        //printTrafficGrid(currentState, idleCars);
                        return true;
                    }
                    else {

                        return false;
                    }
                }
                return false;
            }
        }
        return false;
    }


    public static boolean verticalMove(TrafficState trafficState, Car movingCar, int parkingLotHeight, char[][] currentState, List<Car> idleCars) {
        // look up logic
        // determine the car isn't parked at the top of the parking lot
        // middle spot
        if (movingCar.getRowPosition() != 0 && movingCar.getRowPosition() != (parkingLotHeight - 1)) {

            // let's potentially move it
            char aboveSpace = currentState[movingCar.getRowPosition() - 1][movingCar.getColPosition()];
            // check for opening
            if (Character.isSpaceChar(aboveSpace)) {
                if (cleanVertMoveHelper(trafficState,movingCar, "up", currentState)) {
                    //printTrafficGrid(currentState, idleCars);
                    return true;
                }
            } else {
                // blocked above.
                // check to see if the block can move...
                Car aboveBlockingCar = idleCars.stream().filter(car -> {
                    if ((car.getRowPosition() == (movingCar.getRowPosition() - 1)) && (car.getColPosition() == movingCar.getColPosition())) {
                        return true;
                    } else {
                        return false;
                    }
                }).findFirst().orElse(null);

                if (Objects.nonNull(aboveBlockingCar) && !aboveBlockingCar.isVisited()) {
                    aboveBlockingCar.setVisited(true);
                    if ( moveSingleCar(trafficState,aboveBlockingCar, currentState, idleCars)) {
                        //printTrafficGrid(currentState, idleCars);
                        return true;
                    }
                    else {

                        return  false;
                    }
                }
            }

            // cannot move further up.
            // check the space below
            // let's potentially move it down.
            //
            char downSpace = currentState[movingCar.getRowPosition() + 1][movingCar.getColPosition()];
            // check for opening
            if (Character.isSpaceChar(downSpace)) {
                if (cleanVertMoveHelper(trafficState,movingCar, "down", currentState)) {
                    //printTrafficGrid(currentState, idleCars);
                    return true;
                }
            } else {
//                    //blocked below
//                    // check to see if the block can move...
                Car belowBlockingCar = idleCars.stream().filter(car -> {
                    if ((car.getRowPosition() == (movingCar.getRowPosition() + 1)) && (car.getColPosition() == movingCar.getColPosition())) {
                        return true;
                    } else {
                        return false;
                    }
                }).findFirst().orElse(null);

                if (Objects.nonNull(belowBlockingCar) && !belowBlockingCar.isVisited()) {
                    belowBlockingCar.setVisited(true);
                    if( moveSingleCar(trafficState,belowBlockingCar, currentState, idleCars)) {
                        //printTrafficGrid(currentState, idleCars);
                        return true;
                    }
                    else {

                        return false;
                    }

                }
                return false;
            }
        }
        // top position need to look down
        // top position only check below
        if (movingCar.getRowPosition() == 0) {
            // stuck in the top position need to look for below moves
            // let's potentially move it
            char downwardSpace = currentState[movingCar.getRowPosition() + 1][movingCar.getColPosition()];
            // check for opening
            if (Character.isSpaceChar(downwardSpace)) {
                if (cleanVertMoveHelper(trafficState, movingCar, "down", currentState)) {
                    //printTrafficGrid(currentState, idleCars);
                    return true;
                } else {
                    return false;
                }
            } else {
                //blocked below
                // check to see if the block can move...
                Car belowBlockingCar = idleCars.stream().filter(car -> {
                    if ((car.getRowPosition() == (movingCar.getRowPosition() + 1)) && (car.getColPosition() == movingCar.getColPosition())) {
                        return true;
                    } else {
                        return false;
                    }
                }).findFirst().orElse(null);

                if (Objects.nonNull(belowBlockingCar) && !belowBlockingCar.isVisited()) {
                    belowBlockingCar.setVisited(true);
                    if (moveSingleCar(trafficState,belowBlockingCar, currentState, idleCars)) {
                        //printTrafficGrid(currentState, idleCars);
                        return true;
                    }
                    else {

                        return false;
                    }
                }
            }
            return false;
        }

        // bottom  position look up
        if (movingCar.getRowPosition() == (parkingLotHeight - 1)) {
            // stuck in the top position need to look for below moves
            // let's potentially move it
            char aboveSpace = currentState[movingCar.getRowPosition() - 1][movingCar.getColPosition()];
            // check for opening
            if (Character.isSpaceChar(aboveSpace)) {
                if (cleanVertMoveHelper(trafficState,movingCar, "up", currentState)) {
                    //printTrafficGrid(currentState, idleCars);
                    return true;
                } else {
                    return false;
                }
            } else {
                // blocked from the top and bottom.
                //blocked below
                // check to see if the block can move...
                Car aboveBlockingCar = idleCars.stream().filter(car -> {
                    if ((car.getRowPosition() == (movingCar.getRowPosition() - 1)) && (car.getColPosition() == movingCar.getColPosition())) {
                        return true;
                    } else {
                        return false;
                    }
                }).findFirst().orElse(null);

                if (Objects.nonNull(aboveBlockingCar) && !aboveBlockingCar.isVisited()) {
                    aboveBlockingCar.setVisited(true);
                    if (moveSingleCar(trafficState,aboveBlockingCar, currentState, idleCars)) {
                        //printTrafficGrid(currentState, idleCars);
                        return true;
                    }
                    else {

                        return false;
                    }

                }
                return false;
            }

        }
        return false;
    }

    public static boolean moveSingleCar(TrafficState trafficState,Car car, char[][] currentState, List<Car> idleCars) {
        if (car.getSymbol() == '-' || car.getSymbol() == '>') {
            if (horizontalMove(trafficState,car, currentState[0].length, currentState, idleCars)) {
                car.setVisited(true);
                //printTrafficGrid(currentState, idleCars);
                return true;
            } else {
                return false;
            }
        }
        if (car.getSymbol() == '|') {
            if (verticalMove(trafficState,car,currentState.length,currentState, idleCars)) {
                car.setVisited(true);
                //printTrafficGrid(currentState, idleCars);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean cleanHorizMoveHelper(TrafficState trafficState,Car movingCar, String direction, char[][] currentState) {
        int[] fromSpace = {movingCar.getRowPosition(), movingCar.getColPosition()};
        if (direction.equals("left")) {
            int[] toSpace = {movingCar.getRowPosition(), movingCar.getColPosition() - 1};
            currentState[toSpace[0]][toSpace[1]] = movingCar.getSymbol();
         /*   movingCar.setColPosition(movingCar.getColPosition() - 1);*/
        } else {
            int[] toSpace = {movingCar.getRowPosition(), movingCar.getColPosition() + 1};
            currentState[toSpace[0]][toSpace[1]] = movingCar.getSymbol();
//            movingCar.setColPosition(movingCar.getColPosition() + 1);
        }

        currentState[fromSpace[0]][fromSpace[1]] = ' ';
        trafficState.setCurrentState(currentState);
        String hashKey = buildKey(currentState);
        trafficState.setMovesWeight(trafficState.getMovesWeight()+1);
        if (!seenState(hashKey)) {
            collectMovesHash(hashKey, trafficState);
            trafficStates.add(trafficState);
            movesToSolve++;
            return true;
        }
        // revert the logic
        else {
            // ensures its swapping for a better value.
            if (previousStates.get(hashKey).getMovesWeight() > trafficState.getMovesWeight()) {
                trafficStates.add(trafficState);
                previousStates.put(hashKey, trafficState);
            } else {
                // moves things back...
                if (direction.equals("left")) {
                    int[] toSpace = {movingCar.getRowPosition(), movingCar.getColPosition() - 1};
                    currentState[toSpace[0]][toSpace[1]] = ' ';
                    // put the car back
                    currentState[fromSpace[0]][fromSpace[1]] = movingCar.getSymbol();

                    return false;
                } else {
                    int[] toSpace = {movingCar.getRowPosition(), movingCar.getColPosition() + 1};
                    currentState[toSpace[0]][toSpace[1]] = ' ';
                    // put the car back
                    currentState[fromSpace[0]][fromSpace[1]] = movingCar.getSymbol();
                    return false;
                }
            }
        }
        return true;
    }


    public static boolean cleanVertMoveHelper(TrafficState trafficState, Car movingCar, String direction, char[][] currentState) {
        int[] fromSpace = {movingCar.getRowPosition(), movingCar.getColPosition()};
        if (direction.equals("up")) {
            int[] toSpace = {movingCar.getRowPosition() - 1, movingCar.getColPosition()};
            currentState[toSpace[0]][toSpace[1]] = movingCar.getSymbol();
//            movingCar.setRowPosition(movingCar.getRowPosition() - 1);
        } else {
            int[] toSpace = {movingCar.getRowPosition() + 1, movingCar.getColPosition()};
            currentState[toSpace[0]][toSpace[1]] = movingCar.getSymbol();
//            movingCar.setRowPosition(movingCar.getRowPosition() + 1);
        }

        currentState[fromSpace[0]][fromSpace[1]] = ' ';
        String hashKey = buildKey(currentState);
        trafficState.setCurrentState(currentState);
        trafficState.setMovesWeight(trafficState.getMovesWeight()+1);

        if (!seenState(hashKey)) {
            collectMovesHash(hashKey, trafficState);
            trafficStates.add(trafficState);
            movesToSolve++;
        } else {
            // ensures its swapping for a better value.
            if (previousStates.get(hashKey).getMovesWeight() >= trafficState.getMovesWeight()) {
                trafficStates.add(trafficState);
                previousStates.put(hashKey, trafficState);
            } else {
                // moves things back...
                if (direction.equals("up")) {
                    // replace the car's coordinates with a ' '
                    int[] toSpace = {movingCar.getRowPosition() - 1, movingCar.getColPosition()};
                    currentState[toSpace[0]][toSpace[1]] = ' ';
                    // put the car back
                    currentState[fromSpace[0]][fromSpace[1]] = movingCar.getSymbol();
                    return false;
                } else {
                    int[] toSpace = {movingCar.getRowPosition() + 1, movingCar.getColPosition()};
                    currentState[toSpace[0]][toSpace[1]] = ' ';
                    currentState[fromSpace[0]][fromSpace[1]] = movingCar.getSymbol();
                    return false;
                }
            }
        }
        return true;
    }

    public static String buildKey(char[][] providedState) {
        StringBuilder currentStateStr = new StringBuilder();
        for (int i = 0; i < providedState.length; i++) {
            for (int j = 0; j < providedState[0].length; j++) {
                currentStateStr.append(providedState[i][j]);
            }
        }
        return currentStateStr.toString();
    }


    public static boolean seenState(String trafficKey) {
        return previousStates.containsKey(trafficKey);
    }

    public static void collectMovesHash(String currentStateKey, TrafficState providedState) {
        previousStates.put(currentStateKey, providedState);
    }

    public static List<Car> locateCars(char[][] currentState, int parkingWidth, int parkingHeight) {
        List<Car> idleCars = new ArrayList<Car>();
        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState[0].length; j++) {
                if (currentState[i][j] == '-') {
                    Car foundCar = new Car(i, j, '-');
                    idleCars.add(foundCar);
                }

                if (currentState[i][j] == '|') {
                    Car foundCar = new Car(i, j, '|');
                    idleCars.add(foundCar);
                }

                if (currentState[i][j] == '>') {
                    Car foundCar = new Car(i, j, '>');
                    idleCars.add(foundCar);
                }

            }
        }
        return idleCars;
    }

    public static void determineGoalState(TrafficGrid grid) {
        char[][] initialState = grid.getCurrentState();
        for (int i = 0; i < initialState.length; i++) {
            for (int j = 0; j < initialState[0].length; j++) {
                if (initialState[i][j] == '>') {
                    int[] goalCoordinates = {(i),(grid.getParkingLotWidth()-1)};
                    grid.setGoalState(goalCoordinates);

                }
            }
        }

    }

    public static boolean checkGoalClear(TrafficState currentState,TrafficGrid trafficGrid) {
        // count backwards and determine if the path is clear to stop wasted moves...
        char[][] cs = currentState.getCurrentState();
        for (int i = 0; i < cs.length; i++) {
            for (int j = 0; j < cs[0].length; j++) {
                if (cs[i][j] == '>') {
                    if (i == trafficGrid.getGoalState()[0] && j == trafficGrid.getGoalState()[1]) {
                        return true;
                    } else {
                        int k = 0;
                        while (k < cs[0].length - 1) {
                            if (cs[i][k + 1] == ' ') {
                                k++;
                            } else {
                                // still have moving to do..
                                return false;
                            }
                        }
                        // add the clear moves...
                        currentState.setMovesWeight(currentState.getMovesWeight() + k);
                        return true;
                    }
                }
                }
            }

        // something wrong.
        return false;
    }

    public static void printTrafficGrid(char[][] currentState, List<Car> idleCarsState) {
//        printInitialState();
        System.out.println(Arrays.deepToString(currentState));
        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState[0].length; j++) {
                System.out.print(currentState[i][j]);
            }
            System.out.println();
        }

        for (Car car: idleCarsState) {
            System.out.print("["+ "{:" + String.valueOf(car.getSymbol())+ ":}" +" "+ "x:" + String.valueOf(car.getRowPosition()) + " y:" + String.valueOf(car.getColPosition()) + "]");
        }
    }

    private static void printInitialState() {
        System.out.println("initial parkingLot: ");
        char[][] initialState = initialGrid.getCurrentState();
        for (int i = 0; i < initialState.length; i++) {
            for (int j = 0; j < initialState[0].length; j++) {
                System.out.print(initialState[i][j]);
            }
            System.out.println();
        }
        System.out.println("=======================================");
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
                initialGrid = trafficGrid;
                parkingLotWidth = trafficGrid.getParkingLotWidth();
                parkingLotHeight = trafficGrid.getParkingLotHeight();

                determineGoalState(trafficGrid);
//                    System.out.println("Found Goal State...");



                TrafficState initialState = new TrafficState(trafficGrid.getCurrentState());
                // test certain states:

                initialState.setIdleCars(locateCars(initialState.getCurrentState(),parkingLotWidth, parkingLotHeight));

                // add to the queue
                trafficStates.add(initialState);
                // add to the steps to prevent back stepping
                String hashKey = buildKey(initialState.getCurrentState());
                if (!seenState(hashKey)) {
                    collectMovesHash(hashKey, initialState);
                }

                int[] goalStateCoords = trafficGrid.getGoalState();
               boolean solvable = false;

                while (trafficStates.size() > 0) {

                    TrafficState currentTrafficGrid = trafficStates.remove();

                    currentTrafficGrid.setIdleCars(locateCars(currentTrafficGrid.getCurrentState(), parkingLotWidth, parkingLotHeight));

                    if (checkGoalClear(currentTrafficGrid, trafficGrid)) {
                        // found the way...
                        solvable = true;
                        System.out.println(currentTrafficGrid.getMovesWeight());
                        break;
                    } else {
                        getNextStates(currentTrafficGrid, trafficGrid.getParkingLotWidth(), trafficGrid.getParkingLotHeight());
                    }
                }

                if (solvable == false) {
                    System.out.println("unsat");
                }


            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(args[0] + " Not Found");
            }

        } catch (ArrayIndexOutOfBoundsException arrayOutBounds) {
            System.out.println("provide a .bugs file as an argument...");
        }

    }
}