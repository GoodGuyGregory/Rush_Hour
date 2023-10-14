
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // get the users requested puzzle
        try {
            String requestedFile = System.getProperty("user.dir") + args[0];
            Set<char[][]> pastMoves = new HashSet<char[][]>();
            try {
                BugRushReader rushReader = new BugRushReader();
                rushReader.readBugRushFile(requestedFile);
                TrafficGrid initialState = rushReader.getInitialState();
                initialState.buildParkingLot();

                // initiate BFS search Algorithm
                char[][] transitionState = initialState.moveCars();
                pastMoves.add(transitionState);

            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(args[0] + " Not Found");
            }

        } catch (ArrayIndexOutOfBoundsException arrayOutBounds) {
            System.out.println("provide a .bugs file as an argument...");
        }

    }
}