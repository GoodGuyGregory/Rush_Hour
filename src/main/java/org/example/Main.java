package org.example;


import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;


public class Main {


    public static void main(String[] args) throws FileNotFoundException {
        Queue<TrafficGrid> trafficGrids = new PriorityQueue<TrafficGrid>();
        Set<TrafficGrid> transitionStates = new HashSet<TrafficGrid>();


        // get the users requested puzzle
        try {
            String requestedFile = System.getProperty("user.dir") + args[0];
            try {
                BugRushReader rushReader = new BugRushReader();
                rushReader.readBugRushFile(requestedFile);
                TrafficGrid initialState = rushReader.getInitialState();

                initialState.buildParkingLot();
                trafficGrids.add(initialState);

                // begin transition state logic..
                
                int totalMoves = 0;

                while (trafficGrids.size() > 0) {
                    TrafficGrid state = trafficGrids.remove();


                    if (state.getBiker().isGoalReached()) {
                        // found the way...
                        System.out.println("solved in " + state.getMovesCounter() + " moves");
                        break;
                    }
                    else {
                        state.moveCars();
//                        totalMoves += state.getMovesCounter();
                        trafficGrids.add(state);
                        // add the state into the collection of previous states for memoization
  //                      if (!transitionStates.contains(state)) {
//                            transitionStates.add(state);
//
//                        }
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
}