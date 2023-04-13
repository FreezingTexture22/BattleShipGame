package Battleship;

import java.util.Scanner;

public class Stage1 {
	static String[][] field = new String[11][11]; // field for game, 10 x 10 plus coordinates
	static String[][] ship;
	

	public static void main(String[] args) {
		generateField();
		printField();
		enterCoordinates();
		

	}

	// BEGINNING OF GAME - new 10x10 field generation\
	private static void generateField() {

		// first row
		field[0][0] = " ";

		for (int i = 1; i <= 10; i++) {
			field[0][i] = Integer.toString(i);
		}

		// second and all others rows
		for (int i = 1; i <= 10; i++) {
			for (int j = 0; j <= 10; j++) {
				if (j == 0) {
					char c = (char) (i + 64); // letters at start of row
					field[i][j] = String.valueOf(c);
				} else {
					field[i][j] = "~";
				}
			}
		}

	}

	// print out field 10 x 10
	private static void printField() {
		System.out.println();
		for (int i = 0; i <= 10; i++) {
			for (int j = 0; j <= 10; j++) {
				System.out.print(field[i][j] + " ");
			}
			System.out.println();
		}
	}

	///// ЗАМЕТКА НА ЗАВТРА
	//// Здесь сделать отдельный метод для проверки координат и размера корабля,
	//// сами проверки уже сделаны ниже (кроме проверки занятости поля и
	//// соседства с другими кораблями) - надо раскидать передачу параметров
	//// между методами.
	////
	////

	private static void enterCoordinates() {

		Scanner scanner = new Scanner(System.in);

		System.out.println();
		System.out.printf("Enter the coordinates of the %s (%d cells): \n", "Aircraft Carrier", 5);

		int shipSize = 5;

		// start check (enter something like "A1 A5"):
		boolean noErrors = false;

		// do while there are errors...
		do {
			String beginShip = scanner.next(); // begin coordinate...

			// beginShip contains wrong characters?
			if (beginShip.matches("[A-J][1-9][0]?")) {
				noErrors = true;
//				System.out.println("INPUT OK");
			} else {
				noErrors = false;
				System.out.println("Error! beginShip Contains wrong characters! Try again:\n");
				continue;
			}

			String endShip = scanner.next(); // ...end coordinate
			// endShip contains wrong characters?
			if (endShip.matches("[A-J][1-9][0]?")) {
				noErrors = true;
//				System.out.println("INPUT OK");
			} else {
				noErrors = false;
				System.out.println("Error! endShip Contains wrong characters! Try again:\n");
				continue;
			}

			int beginShip1 = (int) beginShip.charAt(0) - 64; // first char A-J, getting char number
			int beginShip2 = Integer.parseInt(beginShip.replaceAll("[^0-9]", "")); // replacing all non numbers and
																					// convert to int

			int endShip1 = (int) endShip.charAt(0) - 64; // first char A-J, getting char number
			int endShip2 = Integer.parseInt(endShip.replaceAll("[^0-9]", "")); // replacing all non numbers and convert
																				// to int

			// is orthogonal?
			if (beginShip1 != endShip1 && beginShip2 != endShip2) {
				noErrors = false;
				System.out.println("Error! Wrong ship location! Try again:\n");
				continue;
			} else {
				noErrors = true;
//				System.out.println("Ship is orthogonal");
			}

			// is ship legal size?
			if (endShip1 - beginShip1 + 1 == shipSize || endShip2 - beginShip2 + 1 == shipSize) {
				noErrors = true;
//				System.out.println("Ship size legal");				
			} else {
				System.out.printf("Error! Wrong length of the %s! Try again: \n", "Aircraft Carrier");
				noErrors = false;
				continue;
			}

			// is within game board coordinates?
			if (beginShip1 < 1 || beginShip2 < 1 || endShip1 > 10 || endShip2 > 10) {
				System.out.println("Error! Ship location is out of bounds! Try again:");
				noErrors = false;
				continue;
			} else {
				noErrors = true;
//				System.out.println("Location OK!");
			}
			
			
			
			// !!!!!!!!!!!!!!!!!!
			// 13/04/2023
			//
			// create ship and fill coordinates
			Ship s1 = Ship.ACARRIER;
			
			System.out.println("*********************");
			
			for (int i = beginShip2; i <= endShip2; i++) {
				field[beginShip1][i] = "O";
				
			}
			
			printField();
			
			
			// is place available?

			// is adjacent to other ships?

		} while (!noErrors);

	}

	
}

enum Ship {

	ACARRIER(5, "Aircraft Carrier"), 
	BATTLESHIP(4, "Battleship"), 
	SUBMARINE(3, "Submarine"), 
	CRUISER(3, "Cruiser"),
	DESTROYER(2, "Destroyer");

	int shipSize;
	String shipType;



	Ship (int shipSize, String shipType) {
		this.shipSize = shipSize;
		this.shipType = shipType;


	}

	public int getShipSize() {
		return shipSize;
	}

	public String getShipType() {
		return shipType;
	}
}
