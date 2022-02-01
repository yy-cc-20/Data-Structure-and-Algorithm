package backtracking;

import java.awt.Point;
import java.util.Scanner;
import java.util.Stack;

/*
	Given a maze, find a path from start to finish. X are walls, blanks are corridors,
	'S' is the starting point, 'E' is the ending point. At each intersection, you have 
	to decide between 
	- Go up
	- Go left
	- Go right
	- Go down
	
Test Cases:

CASE 1 (Best Case)
3
XXXX
XSEX
XXXX

CASE 2
3
XXXXXX
XES  X
XXXXXX

CASE 3
3
XXXXX 
XS EX
XXXXX
	
CASE 4
4
XXXXX 
XS  X
X  EX
XXXXX
	
CASE 5
4
XXXXX 
XE  X
X  SX
XXXXX
	
CASE 6
4
XXXXXXX
XSX   X 
X   XEX
XXXXXXX
	
CASE 7
4
XXXXXXXX
XSX    X
X   EX X
XXXXXXXX

CASE 8
4
XXXXXXXX
XES    X
X    X X
XXXXXXXX
	
CASE 9
10
XXXXXXXXXX
X        X
X        X
X        X
X        X
X        X
X        X
XX XXXXXXX
XS      EX
XXXXXXXXXX

CASE 10 (Worst Case)
10
XXXXXXXXXX
X        X
X        X
X        X
X        X
X        X
X        X
XX XXXXXXX
XES      X
XXXXXXXXXX

	
CASE 11
10
XXXXXXXXXX
X  X X X X
XX X   X X
XX XXX   X
X    X XXX
XX X X XEX
X  X   X X
XX XXX X X
XS   X   X
XXXXXXXXXX

CASE 12
10
XXXXXXXXXX
X  X X X X
XX X   X X
XX XXX   X
X    X XXX
XX X X XEX
X  X   XXX
XX XXX X X
XS   X   X
XXXXXXXXXX


 */

public class Maze {
	private static Scanner scanner;
	private char[][] maze;
	private Point startingPoint;
	private Point endingPoint;
	
	// Notations on the maze
	//private final static char WALL = 'X';
	private final static char CORRIDOR = ' ';
	private final static char STARTING_POINT = 'S';
	private final static char ENDING_POINT = 'E';
	
	private final static Point UNKNOWN_POSITION = new Point(-1, -1);
	private final static char DEAD_END = 'D';
	private final static char CURRENT_POSITION = '0';
	
	private final static char GO_UP    = '^';
	private final static char GO_RIGHT = '>';
	private final static char GO_DOWN  = 'v';
	private final static char GO_LEFT  = '<';
	
	public Maze(char[][] m) {
		maze = m.clone();
		findStartingAndEndingPoint();		
	}
	
	public Maze(char[][] m, Point s, Point e) {
		maze = m.clone();
	}

	// Visualize how the program explore the maze and finally get to the ending point
	public void displayAnimatedSolution() {
		if (startingPoint.equals(endingPoint)
				|| startingPoint.equals(UNKNOWN_POSITION) 
				|| endingPoint.equals(UNKNOWN_POSITION)) {
			System.out.println("Invalid Input");
			return;
		}
		
		// Initialize variables
		char[][] markedMaze = new char[maze.length][]; 
		Point pos = new Point(startingPoint); 
		Stack<Point> visitedPos = new Stack<>(); 
		visitedPos.push(startingPoint);
		for (int i = 0; i < maze.length; ++i) { // Clone 2D array
			markedMaze[i] = maze[i].clone(); 
		}
		
		display(markedMaze, pos.x, pos.y);
		while (move(markedMaze, pos, visitedPos)) {
			clearScreen();
			display(markedMaze, pos.x, pos.y);
		}
		if (visitedPos.empty())
			System.out.println("Destination cannot be reached.");
	}
	
	// Display the path to get to the ending point on the maze without showing dead end markers on the maze
	public void displayAnimatedSolution2() {
		if (startingPoint.equals(endingPoint)
				|| startingPoint.equals(UNKNOWN_POSITION) 
				|| endingPoint.equals(UNKNOWN_POSITION)) {
			System.out.println("Invalid Input");
			return;
		}
		
		// Initialize variables
		char[][] markedMaze = new char[maze.length][]; 
		for (int i = 0; i < maze.length; ++i) { // Clone 2D array
			markedMaze[i] = maze[i].clone(); 
		}
		
		if(move(markedMaze, startingPoint.x, startingPoint.y))
			display(markedMaze, -1, -1); // Does not need to show current position
		else
			System.out.println("Destination cannot be reached.");
	}
	
	// Move one step further in any direction without revisiting the location that has been visited, return if meet a dead end
	// @param markedMaze 	contains the maze, DEAD_END markers and visited locations
	// @param pos 			current	position
	// @param visitedPos 	memorize the sequence of the visited locations so that we can return after met an dead end
	// @return true to move, false to stop moving
	private boolean move(char[][] markedMaze, Point pos, Stack<Point> visitedPos) {
		if (pos.equals(endingPoint))
			return false;
		else if (pos.y > 0 
				&& (markedMaze[pos.y - 1][pos.x] == CORRIDOR 
				|| markedMaze[pos.y - 1][pos.x] == ENDING_POINT)) { // Go up
			pos.translate(0, -1);
			markedMaze[pos.y][pos.x] = GO_UP;
			visitedPos.push(new Point(pos));
		}
		else if (pos.x + 1 < markedMaze[pos.y].length 
				&& (markedMaze[pos.y][pos.x + 1] == CORRIDOR
				|| markedMaze[pos.y][pos.x + 1] == ENDING_POINT)) { // Go right
			pos.translate(1, 0);
			markedMaze[pos.y][pos.x] = GO_RIGHT; 
			visitedPos.push(new Point(pos));
		}
		else if (pos.y + 1 < markedMaze.length 
				&& (markedMaze[pos.y + 1][pos.x] == CORRIDOR
				|| markedMaze[pos.y + 1][pos.x] == ENDING_POINT)) { // Go down
			pos.translate(0, 1);
			markedMaze[pos.y][pos.x] = GO_DOWN; 
			visitedPos.push(new Point(pos));
		}
		else if (pos.x > 0 
				&& (markedMaze[pos.y][pos.x - 1] == CORRIDOR
				|| markedMaze[pos.y][pos.x - 1] == ENDING_POINT)) { // Go left
			pos.translate(-1, 0);
			markedMaze[pos.y][pos.x] = GO_LEFT; 
			visitedPos.push(new Point(pos));
		}
		else { // Go back
			markedMaze[pos.y][pos.x] = DEAD_END; // Mark the current position as path to an dead end
			visitedPos.pop();
			if (visitedPos.empty()) // No way to reach the ending point
				return false;
			pos.setLocation(visitedPos.peek()); // Go back
		}
		return true;
	}
	
	// Using backtracking algorithm
	// Find the shortest path to the ending point
	// @param markedMaze 	contains the maze and visited path
	// @param x, y 			current	position
	// @return true if the path is found
	private boolean move(char[][] markedMaze, int x, int y) {
		display(markedMaze, x, y);
		clearScreen();
		
		// If reach the goal node
		if (markedMaze[y][x] == ENDING_POINT) 
			return true;
		
		// If reach a child node
		if (y > 0 
				&& (markedMaze[y - 1][x] == CORRIDOR 
				|| markedMaze[y - 1][x] == ENDING_POINT)) { // Go up
			markedMaze[y][x] = GO_UP;
			if (move(markedMaze, x, y - 1)) // Exploring a subtree
				return true;
		}
		if (x + 1 < markedMaze[y].length 
				&& (markedMaze[y][x + 1] == CORRIDOR
				|| markedMaze[y][x + 1] == ENDING_POINT)) { // Go right
			markedMaze[y][x] = GO_RIGHT; 
			if (move(markedMaze, x + 1, y))
				return true;
		}
		if (y + 1 < markedMaze.length 
				&& (markedMaze[y + 1][x] == CORRIDOR
				|| markedMaze[y + 1][x] == ENDING_POINT)) { // Go down
			markedMaze[y][x] = GO_DOWN; 
			if (move(markedMaze, x, y + 1))
				return true;
		}
		if (x > 0 
				&& (markedMaze[y][x - 1] == CORRIDOR
				|| markedMaze[y][x - 1] == ENDING_POINT)) { // Go left
			markedMaze[y][x] = GO_LEFT; 
			if (move(markedMaze, x - 1, y))
				return true;
		}
		
		// If reach a leaf node
		markedMaze[y][x] = DEAD_END; 
		display(markedMaze, x, y);
		clearScreen();
		return false;
	}
	
	// pre: call clearScreen()
	private void display(char[][] markedMaze, int x, int y) {
		markedMaze[startingPoint.y][startingPoint.x] = STARTING_POINT;  
		markedMaze[endingPoint.y][endingPoint.x] = ENDING_POINT; 
		
		for (int i = 0; i < markedMaze.length; ++i) {
			for (int j = 0; j < markedMaze[i].length; ++j) {
				if (x == j && y == i)
					System.out.print(CURRENT_POSITION); // Layer 1
				else
					System.out.print(markedMaze[i][j]); // Layer 2
			}
			System.out.println();
		}
	}

	// Assuming one startingPoint and endingPoint
	private void findStartingAndEndingPoint() {
		for (int i = 0; i < maze.length; ++i) {
			for (int j = 0; j < maze[i].length; ++j) {
				if (maze[i][j] == STARTING_POINT)
					startingPoint = new Point(j, i);
				else if (maze[i][j] == ENDING_POINT)
					endingPoint = new Point(j, i);
			}
		}
		if (startingPoint.equals(new Point(0, 0)) && maze[0][0] != STARTING_POINT) // If startingPoint not found
			startingPoint = new Point(UNKNOWN_POSITION);
		else if (endingPoint.equals(new Point()) && maze[0][0] != ENDING_POINT) // If endingPoint not found
			endingPoint = new Point(UNKNOWN_POSITION);
	}
	
	static void clearScreen() {
		System.out.println("Enter >>");
		scanner.nextLine();
		for (int i = 0; i < 22; ++i)
			System.out.println();
	}
	
	public static void main(String... args) {
		scanner = new Scanner(System.in);
		
		
		// Input: 
		// first line: the number of rows of the maze
		// the rest of the line: maze
		 
		char[][] maze = new char[scanner.nextInt()][];
		scanner.nextLine();
		for (int i = 0; i < maze.length; ++i) {
			maze[i] = scanner.nextLine().toCharArray();
		}
		/*
		char[][] maze = new char[][] {
			"XXXXXXXXXX".toCharArray(), 
			"X  X X X X".toCharArray(),
			"XX X   X X".toCharArray(),
			"XX XXX   X".toCharArray(),
			"X    X XXX".toCharArray(),
			"XX X X XEX".toCharArray(),
			"X  X   X X".toCharArray(),
			"XX XXX X X".toCharArray(),
			"XS   X   X".toCharArray(),
			"XXXXXXXXXX".toCharArray()
		};
		*/
		
		// Output: display the maze with path to the ending point
		Maze aMaze = new Maze(maze);
	//	aMaze.displayAnimatedSolution();
		//clearScreen();
		aMaze.displayAnimatedSolution2();
		scanner.close();
	}
}
