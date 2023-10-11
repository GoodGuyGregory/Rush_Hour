package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// Handles the reading process of the problems...
// https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
public class BugRushReader {


    // set up the scanner logic to read the
    private TrafficGrid initialState;

    public TrafficGrid getInitialState() {
        return initialState;
    }

    public void setInitialState(TrafficGrid initialState) {
        this.initialState = initialState;
    }

    public BugRushReader() {}

    // reads file and sets initial state of the parkingLot
    public void readBugRushFile(String fileName) throws FileNotFoundException {
        File rushFile = new File(fileName);
        Scanner fileReader = new Scanner(rushFile);

        List<String> gridContainer = new ArrayList<>();

        int gridWidth = 0;
        int gridHeight = 0;

        // get the width of the grid
        if (fileReader.hasNextLine()) {
            gridWidth = fileReader.nextLine().length();
        }

        while (fileReader.hasNextLine()) {
            gridContainer.add(fileReader.nextLine());
        }

        gridHeight = gridContainer.size();

        // remove the first row...
        gridContainer.remove(0);

        this.initialState = produceGrid(gridContainer,gridWidth, gridHeight);
    }

    public TrafficGrid produceGrid(List<String> gridContainer, int gridWidth, int gridHeight) {
        char[][] parkingLot = new char[gridWidth][gridHeight];

        for (int i = 0; i < gridContainer.size(); i++) {
            // cast the string row into a char array to iterate
            char[] parkingSpaces = gridContainer.get(i).toCharArray();
            for (int j = 0; j < parkingLot.length; j++) {
               // place characters into grid.
                parkingLot[i][j] = parkingSpaces[j];
            }
        }

        return new TrafficGrid(gridWidth,gridHeight,parkingLot);
    }



}
