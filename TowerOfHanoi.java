package recrusion;

import java.util.Scanner;
import java.util.Stack;

/* Move all the disks from one needle to another
 * Only one disk could be moved at a time
 * A larger disk must never be stacked above a smaller one
 * One and only one extra needle could be used for intermediate storage of disks
 */
public class TowerOfHanoi {
	static Scanner scanner = new Scanner(System.in);
	static int numberOfSteps = 0;
	
	static void towerOfHanoiAnimatedSolution(int numberOfDisks) {
		Stack<Integer> rod1 = new Stack<>();
		Stack<Integer> rod2 = new Stack<>();
		Stack<Integer> rod3 = new Stack<>();
				
		// Initialize rod 1

		for (int i = numberOfDisks; i > 0; --i)
			rod1.push(i);
		displayTowerOfHanoi(rod1, rod2, rod3);
		
		if (numberOfDisks % 2 == 0) {
			while (rod3.size() != numberOfDisks) {
				if (!rod1.empty() && (rod2.empty() || rod1.peek() < rod2.peek()))
					rod2.push(rod1.pop());
				else 
					rod1.push(rod2.pop()); 
				displayTowerOfHanoi(rod1, rod2, rod3);
				
				if (!rod1.empty() && (rod3.empty() || rod1.peek() < rod3.peek()))
					rod3.push(rod1.pop());
				else
					rod1.push(rod3.pop()); 
				displayTowerOfHanoi(rod1, rod2, rod3);
				
				if (!rod2.empty() && (rod3.empty() || rod2.peek() < rod3.peek()))
					rod3.push(rod2.pop());
				else
					rod2.push(rod3.pop()); 
				displayTowerOfHanoi(rod1, rod2, rod3);
			}
		} else {
			if (!rod1.empty() && (rod3.empty()|| rod1.peek() < rod3.peek()))
				rod3.push(rod1.pop());
			else
				rod1.push(rod3.pop()); 
			displayTowerOfHanoi(rod1, rod2, rod3);
			
			while (rod3.size() != numberOfDisks) {
				if (!rod1.empty() && (rod2.empty() || rod1.peek() < rod2.peek()))
					rod2.push(rod1.pop());
				else 
					rod1.push(rod2.pop()); 
				displayTowerOfHanoi(rod1, rod2, rod3);
				
				if (!rod2.empty() && (rod3.empty() || rod2.peek() < rod3.peek()))
					rod3.push(rod2.pop());
				else
					rod2.push(rod3.pop()); 
				displayTowerOfHanoi(rod1, rod2, rod3);
				
				if (!rod1.empty() && (rod3.empty() || rod1.peek() < rod3.peek()))
					rod3.push(rod1.pop());
				else
					rod1.push(rod3.pop()); 
				displayTowerOfHanoi(rod1, rod2, rod3);
			}
		}
		System.out.println("Done!");
		scanner.close();
	}
	
	// pre: numberOfDisks > 0
	// Recursive
	static void towerOfHanoiTextSolution(int sourceRod, int destinationRod, int numberOfDisks) {
		numberOfSteps++;
		
		int temporaryRod = 6 - sourceRod - destinationRod; // rod 1 + rod 2 + rod 3 = 6
		
		if (numberOfDisks == 1)
			System.out.println("Move from " + sourceRod + " to " + destinationRod);
		else {
			towerOfHanoiTextSolution(sourceRod, temporaryRod, numberOfDisks - 1); // Move (n - 1) disks from source to temporary rod
			
			System.out.println("Move from " + sourceRod + " to " + destinationRod); // Now the nth disk is the top disk, move it to the destination rod 
			
			towerOfHanoiTextSolution(temporaryRod, destinationRod, numberOfDisks - 1); // Move (n - 1) disks from temporary rod to destination rod
		}
	}
	
	/*
	 for disks = 4
	 
	 0     |         |         |
	 1     |         |         |	
	 2     |         |        (|)    
	 3     |         |       ( | )   
	 4 ((((|)))) ((((|)))) (   |   ) 
	       1         2         3
	   
	  */
	
	// @param first element is 0
	static void displayTowerOfHanoi(int[] disksOnRod1, int[] disksOnRod2, int[] disksOnRod3) {
		int totalDisks = disksOnRod1.length;
		
		// To display the rods
		int rod1Position = totalDisks;
		int rod2Position = totalDisks * 3 + 2;
		int rod3Position = totalDisks * 5 + 4;
		
		// To display the disks
		int rod1Range = totalDisks * 2 + 1;
		int rod2Range = totalDisks * 4 + 3;
		int rod3Range = totalDisks * 6 + 5;
		
		for (int i = 0; i < totalDisks; ++i) { // For every level of the rods
			for (int j = 0; j < rod3Range; ++j) { // Construct the level
				if (j == rod1Position || j == rod2Position || j == rod3Position) // Display the rods
					System.out.print("|");
				else if (i == 0) // First level always no disk
					System.out.print(" "); 
				else { // Other levels
					if (j < rod1Range) { // Rod 1
						if (j == rod1Position - disksOnRod1[i])
							System.out.print("(");
						else if (j == rod1Position + disksOnRod1[i])
							System.out.print(")");
						else
							System.out.print(" ");
					} else if (j < rod2Range) { // Rod 2
						if (j == rod2Position - disksOnRod2[i])
							System.out.print("(");
						else if (j == rod2Position + disksOnRod2[i])
							System.out.print(")");
						else
							System.out.print(" ");
					} else { // Rod 3
						if (j == rod3Position - disksOnRod3[i])
							System.out.print("(");
						else if (j == rod3Position + disksOnRod3[i])
							System.out.print(")");
						else
							System.out.print(" ");
					}
				}
			}
			System.out.println();
		}
	}
	
	static void displayTowerOfHanoi(Stack<Integer> disksOnRod1, Stack<Integer> disksOnRod2, Stack<Integer> disksOnRod3) {
		clearScreen();
		
		int totalDisks = disksOnRod1.size() + disksOnRod2.size() + disksOnRod3.size(); 
		
		// To display the rods
		int rod1Position = totalDisks;
		int rod2Position = totalDisks * 3 + 2;
		int rod3Position = totalDisks * 5 + 4;
		
		// To display the disks
		int rod1Range = totalDisks * 2 + 1;
		int rod2Range = totalDisks * 4 + 3;
		int rod3Range = totalDisks * 6 + 5;
		
		for (int i = totalDisks; i >= 0; --i) { // For every level of the rods
			for (int j = 0; j < rod3Range; ++j) { // Construct the level
				if (j == rod1Position || j == rod2Position || j == rod3Position) // Display the rods
					System.out.print("|");
				else if (i == totalDisks) // First level always no disk
					System.out.print(" "); 
				else { // Other levels
					if (j < rod1Range) { // Rod 1
						if (i >= disksOnRod1.size()) // Does not have disk on this level
							System.out.print(" ");
						else if (j == rod1Position - disksOnRod1.get(i))
							System.out.print("(");
						else if (j == rod1Position + disksOnRod1.get(i))
							System.out.print(")");
						else
							System.out.print(" ");
					} else if (j < rod2Range) { // Rod 2
						if (i >= disksOnRod2.size()) 
							System.out.print(" ");
						else if (j == rod2Position - disksOnRod2.get(i))
							System.out.print("(");
						else if (j == rod2Position + disksOnRod2.get(i))
							System.out.print(")");
						else
							System.out.print(" ");
					} else { // Rod 3
						if (i >= disksOnRod3.size()) 
							System.out.print(" ");
						else if (j == rod3Position - disksOnRod3.get(i))
							System.out.print("(");
						else if (j == rod3Position + disksOnRod3.get(i))
							System.out.print(")");
						else
							System.out.print(" ");
					}
				}
			}
			System.out.println();
		}
	}
	
	static void clearScreen() {
		System.out.println("Enter >>");
		scanner.nextLine();
		for (int i = 0; i < 22; ++i)
			System.out.println();
	}
	
	static int getMinimumOperation(int disks) {
		return (int)Math.pow(2, disks) - 1;
	}
	
	public static void main(String... args) {
		System.out.print("Enter the number of disks >> ");
		int disks = scanner.nextInt();
		scanner.nextLine();
		
		//towerOfHanoiAnimatedSolution(disks);
		towerOfHanoiTextSolution(1, 3, disks);
		System.out.println("Number of steps using recursion = " + numberOfSteps);
		System.out.println("Minimum number of steps = " + getMinimumOperation(disks));
	}
}