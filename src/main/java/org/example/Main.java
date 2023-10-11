package org.example;


import java.io.FileNotFoundException;


public class Main {


    public static void main(String[] args) throws FileNotFoundException {
        // get the users requested puzzle
        try {
            String requestedFile = System.getProperty("user.dir") + args[0];
            try {
                BugRushReader rushReader = new BugRushReader();
                rushReader.readBugRushFile(requestedFile);
                TrafficGrid initialState = rushReader.getInitialState();
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(args[0] + " Not Found");
            }

        }
        catch (ArrayIndexOutOfBoundsException arrayOutBounds) {
            System.out.println("provide a .bugs file as an argument...");
        }



    }
}