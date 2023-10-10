package org.example;


import java.io.FileNotFoundException;


public class Main {


    public static void main(String[] args) throws FileNotFoundException {
        // get the users requested puzzle
        String requestedFile = System.getProperty("user.dir") + args[0];

        try {
            BugRushReader rushReader = new BugRushReader();
            rushReader.readBugRushFile(requestedFile);
        }
        catch (FileNotFoundException notFound) {
            System.out.println(args[0] + " Not Found");
            throw notFound;
        }


    }
}