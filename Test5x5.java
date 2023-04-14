package Battleship;

import java.util.Scanner;

public class Test5x5 {
	static String[][] board = new String [5][5];
	
	public static void main (String[] args) {

		
		generateBoard();
		printBoard();

		board[2][2] = "X";
		board[2][3] = "X";
		
		printBoard();
		
		askCoordinates();
		
		printBoard();
		
		
	}

	private static void askCoordinates() {
		System.out.println("Enter grid coordinates (0-4), horizontal, vertical (ie: \"1 3\") ");
		Scanner in = new Scanner(System.in);
		int input1 = in.nextInt();
		int input2 = in.nextInt();
		
		if (board[input1][input2].equals("X")) {
			System.out.println("Place is occupied, nothing changed:");
		} else if (board[input1-1][input2].equals("X") || board[input1+1][input2].equals("X") || board[input1][input2-1].equals("X") || board[input1][input2+1].equals("X")) {
			System.out.println("Its too close to other X, nothing changed:");
		}
		
		else {
			board[input1][input2] = "X";
			
		}
		
	}

	static void generateBoard() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				board[i][j] = "O";				
			}			
		}
	}
	
	static void printBoard() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("******");
		System.out.println();
	}
	
}
