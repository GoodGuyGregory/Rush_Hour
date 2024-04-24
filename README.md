## BugRush Project

Greg Witt

This project is intended to build a State Space Search 
implementation of a smaller scale version of the popular game RushHour.

This popular game requires users to solve the puzzle by moving blocking cars out of the way of a specific car among a grid
of a specified length and with.

for this assigment I broke out the elements of the game into various classes.

**BugRushReader** 

this class is used to read the assigned file from the args provided by the user.
the arguments represent the puzzle in the form of a bugs file.

**TrafficGrid**

the *TrafficGrid* class represents a global variable that is used through the Main method and processes the inital state provided 
from the BugRushReader. it carries the initial representation of the game board and cars lists

**TrafficStates**

This class holds most of the main logic for each board. It is used to hold a representation of the current board after a state change.
it is passed to the Queue and also the item that is held in the Hashset of the current board layout.

it contains a **weight** which represents the number of moves made on board to get to the current state.
it also contains a list of **Car** objects. this is ment to be an immutable object ArrayList to hold the coordinates and positions
of the cars for the initial processing. and a `char[][]` **currentState** which is the main state of the board and is passed throughout the processing
portions to determine the next transitional state.

**Car**

this class holds position elements row and column information. as well as a symbol to represent its orientation.
I also have a visited boolean value to determine if the car is explored for potential moves when implemeting the Breadth
First Search.


## Implementation

Most of the logic I used within the application to determine the shortest path. works for all puzzles except the `fun3x4.bugs` file.
Every problem returns an answer that is remotely expected except the aforementioned puzzle. the values are bit off. 
I am not entirely sure why. I would like to discuss some refactoring options with you because learning this is actually important to me.

**Basic BFS layout**

1. the file is read into the system and a **TrafficGrid** object is created. which determines a int[] for holding the two values that represent the positions of the goal state.
2. Empty PriorityQueue `Queue<TrafficState> trafficStates = new PriorityQueue<TrafficSta` and Hashmap `HashMap<String, TrafficState> previousStates` are loaded into the global scope
3. the first initial state is enqueued into the Hashmap of previousStates.
4. the first `TrafficState` is created and added to the **trafficStates** queue
5. the While loop is stated checking if the length of the queue isn't empty. this will determine if the options have run out and later determine if the puzzle is `unsat`
6. the `checkGoalClear` method is called on the intial state and if it isn't satisfied it is then moved to the else block which calls `getNextStates`
7. `getNextStates` processes the states and checks if the transitionState has been seen within the hashset and if not it is added. this prevents previous states from being checked
the previously seen states are then compared by weight to ensure the outlist (in this case the hashmap) is saving the best lowest value and thus the best path.
8. the process continues until each node is checked and if possible the trafficState is hashed and added to the queue for further exploration.
9. the process continues until the goal state is met. then the weight of the **TrafficState** found at goal state is returned as the moves count made.


**Executing this code** 

I am using JDK11 so Java11 for some of the deep copy and other functionality. I also used a custom method from a link I sited within the project in Main.java

ensure all provided files are within the same package as the Main.java file.
from within this directory `Rush_Hour\src\main\java`

cd into `Rush_Hour\src\main\java` and then run the following commands

```java
// within the same directory as the <something>.bugs file
javac *.java && java main <currentWorkingDir>/<example>.bugs 

// actual example
// from within this directory Rush_Hour\src\main\java
// compile the code
javac org/example/*.java       
java org/example/Main ./org/example/hardest3x4.bugs
```

let me know if you have any trouble with this, as I had some problems without the files being all in the same directory.


**Additional Sources**

* [Rushhour Solver in Python](https://medium.com/swlh/programming-puzzle-rush-hour-traffic-jam-3ee513e6c4ab)
* [Reading Files in Java](https://www.geeksforgeeks.org/different-ways-reading-text-file-java/)
* [CompareTo Override for Priority Queue](https://www.geeksforgeeks.org/how-to-override-compareto-method-in-java/)




