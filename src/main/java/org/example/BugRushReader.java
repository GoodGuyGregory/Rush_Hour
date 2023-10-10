package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

// Handles the reading process of the problems...
// https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
public class BugRushReader {


    // set up the scanner logic to read the
    private File rushFile;

    public File getRushFile() {
        return rushFile;
    }

    public void setRushFile(File rushFile) {
        this.rushFile = rushFile;
    }

    public BugRushReader() {}

    public BugRushReader(File rushFile) {
        this.rushFile = rushFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BugRushReader that = (BugRushReader) o;
        return Objects.equals(getRushFile(), that.getRushFile());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRushFile());
    }

    public void readBugRushFile(String fileName) throws FileNotFoundException {
        File rushFile = new File(fileName);
        Scanner fileReader = new Scanner(rushFile);


        TrafficGrid grid = new TrafficGrid();
        int gridSize = 0;

        while (fileReader.hasNextLine()) {
            System.out.println(fileReader.nextLine());
        }

    }

//    public TrafficGrid produceGrid() {}



}
