package org.example;

import java.util.ArrayList;
import java.util.List;

public class TrafficState implements Comparable<TrafficState>{

    private char[][] currentState;
    private int movesWeight;
    private List<Car> idleCars;

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

    public List<Car> getIdleCars() {
        return idleCars;
    }

    public void setIdleCars(List<Car> idleCars) {
        this.idleCars = idleCars;
    }

    public TrafficState() {
    }

    public List<Car> idleCars(char[][] currentState) {
        List<Car> deepCars = new ArrayList<Car>();
        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState[0].length; j++) {
                if (currentState[i][j] == '-') {
                    Car foundCar = new Car(i, j, '-');
                    deepCars.add(foundCar);
                }

                if (currentState[i][j] == '|') {
                    Car foundCar = new Car(i, j, '|');
                    deepCars.add(foundCar);
                }

                if (currentState[i][j] == '>') {
                    Car foundCar = new Car(i, j, '>');
                    deepCars.add(foundCar);
                }

            }
        }
        return deepCars;
    }

    public TrafficState(char[][] currentState) {
        this.currentState = currentState;
    }



    @Override
    public int compareTo(TrafficState o) {
        return 0;
    }
}
