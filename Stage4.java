package Battleship;

import java.util.Scanner;

public class Stage4 {
	// field for game, 10 x 10 plus coordinates
	static String[][] field = new String[11][11];

	// creating ships from ENUM
	static Ship aircraftCarrier = Ship.ACARRIER;
	static Ship battleship = Ship.BATTLESHIP;
	static Ship submarine = Ship.SUBMARINE;
	static Ship cruiser = Ship.CRUISER;
	static Ship destroyer = Ship.DESTROYER;

	//some static vars
	static int notSinkedShips = 5; // Quantity of ships on board, change as needed
	static boolean isFirstRound = true;
	
	// running the game
	public static void main(String[] args) {
		generateField();
		printField();
		placementOfAircraftCarrier();
		placementOfBattleship();
		placementOfSubmarine();
		placementOfCruiser();
		placementOfDestroyer();
		
		gameStart();
		
		do {
			takeShot();
		} while (notSinkedShips != 0);
		


	}

	private static void fogOfWar() {

		System.out.println();
		for (int i = 0; i <= 10; i++) {
			for (int j = 0; j <= 10; j++) {
				if (field[i][j].equals("O")) {
					System.out.print("~ ");
				} else {
					System.out.print(field[i][j] + " ");
				}

			}
			System.out.println();
		}

	}

/////////////////////////////////////////////	
	// Start the Game
	private static void gameStart() {
		System.out.println();
		System.out.println("The game starts!");
		fogOfWar();

	}

/////////////////////////////////////////////
	// Take a shot
	private static void takeShot() {
		System.out.println();
		
		if (isFirstRound == true) {
			System.out.println("Take a shot!");
			System.out.println();	
			isFirstRound = false;
		}

		Scanner scanner = new Scanner(System.in);
		int fireCoordV = 0;
		int fireCoordH = 0;

		// start check of fire coordinate (enter something like "A1"):
		boolean noErrors = false;

		// do while there are errors...
		do {

			String fireCoordinate = scanner.next(); // fire coordinate

			// beginShip contains wrong characters?
			if (fireCoordinate.matches("[A-J][1-9][0]?")) {
				noErrors = true;
			} else {
				noErrors = false;
				System.out.println();
				System.out.println("Error! You entered the wrong coordinates! Try again:\n");
				continue;
			}

			fireCoordV = (int) fireCoordinate.charAt(0) - 64;
			// vertical coordinate, first char A-J, getting char number

			fireCoordH = Integer.parseInt(fireCoordinate.replaceAll("[^0-9]", ""));
			// horizontal coordinate, replacing all non numbers and convert to int

		} while (!noErrors);

		if (field[fireCoordV][fireCoordH] == "~" || field[fireCoordV][fireCoordH] == "M") {
			field[fireCoordV][fireCoordH] = "M";
			fogOfWar();
			System.out.println();
			System.out.println("You missed. Try again:");			
			
		} else if (field[fireCoordV][fireCoordH] == "X") {
			fogOfWar();
			hitShip();
			
		} else if (field[fireCoordV][fireCoordH] == "O") {
			field[fireCoordV][fireCoordH] = "X";
			fogOfWar();
			
			if (fireCoordV == 10 && fireCoordH == 10) {
				if (field[fireCoordV - 1][fireCoordH].matches("[^O]") && field[fireCoordV][fireCoordH - 1].matches("[^O]")) {
					sinkShip();
				} else {
					hitShip();
				}

			} else if (fireCoordV == 10) {
				if (field[fireCoordV-1][fireCoordH].matches("[^O]") && field[fireCoordV][fireCoordH-1].matches("[^O]") && field[fireCoordV][fireCoordH+1].matches("[^O]")) {
					sinkShip();
				} else {
					hitShip();
				}

			} else if (fireCoordH == 10) {
				if (field[fireCoordV-1][fireCoordH].matches("[^O]") && field[fireCoordV+1][fireCoordH].matches("[^O]") && field[fireCoordV][fireCoordH-1].matches("[^O]")) {
					sinkShip();
				} else {
					hitShip();
				}

			} else if (fireCoordV != 10 && fireCoordH != 10) {
				if (field[fireCoordV-1][fireCoordH].matches("[^O]") && field[fireCoordV+1][fireCoordH].matches("[^O]") && field[fireCoordV][fireCoordH-1].matches("[^O]") && field[fireCoordV][fireCoordH+1].matches("[^O]")) {
					sinkShip();
				} else {
					hitShip();
				}
			} 
			
			
		}

	}

	private static void hitShip() {
		System.out.println();
		System.out.println("You hit a ship! Try again:");

	}

	private static void sinkShip() {
		notSinkedShips--;

		if (notSinkedShips == 0) {
			System.out.println();
			System.out.println("You sank the last ship. You won. Congratulations!");
		} else {
			System.out.println();
			System.out.println("You sank a ship! Specify a new target:");
		}

	}

/////////////////////////////////////////////

	// BEGINNING OF GAME - new 10x10 field generation\
	private static void generateField() {

		// first row
		field[0][0] = " ";

		for (int i = 1; i <= 10; i++) {
			field[0][i] = Integer.toString(i);
		}

		// generate second and all others rows
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
	

/////////////////////////////////////////////

	// Aircraft Carrier placement
	static void placementOfAircraftCarrier() {
		enterCoordinates(aircraftCarrier);
	}

	// Battleship placement
	static void placementOfBattleship() {
		enterCoordinates(battleship);
	}

	// Submarine placement
	static void placementOfSubmarine() {
		enterCoordinates(submarine);
	}

	// Cruiser placement
	static void placementOfCruiser() {
		enterCoordinates(cruiser);
	}

	// Destroyer placement
	static void placementOfDestroyer() {
		enterCoordinates(destroyer);
	}

/////////////////////////////////////////////

	// coordinates input ("A1 A5")
	private static void enterCoordinates(Ship ship) {

		Scanner scanner = new Scanner(System.in);

		System.out.println();
		System.out.printf("Enter the coordinates of the %s (%d cells):", ship.getShipType(), ship.getShipSize());
		System.out.println();
		System.out.println();
		int shipSize = ship.getShipSize();

		// start check (enter something like "A1 A5"):
		boolean noErrors = false;

		// do while there are errors...
		do {
			boolean horizontal = false;
			boolean vertical = false;
			String beginShip = scanner.next(); // first coordinate (A1)...

			// beginShip contains wrong characters?
			if (beginShip.matches("[A-J][1-9][0]?")) {
				noErrors = true;
			} else {
				noErrors = false;
				System.out.println();
				System.out.println("Error! beginShip Contains wrong characters! Try again:\n");
				continue;
			}

			String endShip = scanner.next(); // ...last coordinate (A5)
			// endShip contains wrong characters?
			if (endShip.matches("[A-J][1-9][0]?")) {
				noErrors = true;
			} else {
				noErrors = false;
				System.out.println();
				System.out.println("Error! endShip Contains wrong characters! Try again:\n");
				continue;
			}

			int beginShip1 = (int) beginShip.charAt(0) - 64; // first char A-J, getting char number
			int beginShip2 = Integer.parseInt(beginShip.replaceAll("[^0-9]", "")); // replacing all non numbers and
																					// convert to int

			int endShip1 = (int) endShip.charAt(0) - 64; // first char A-J, getting char number
			int endShip2 = Integer.parseInt(endShip.replaceAll("[^0-9]", "")); // replacing all non numbers and convert
																				// to int

			// check if coordinates entered in reverse order - and change them
			if (beginShip1 > endShip1) {
				int tmp = beginShip1;
				beginShip1 = endShip1;
				endShip1 = tmp;

			}

			if (beginShip2 > endShip2) {
				int tmp = beginShip2;
				beginShip2 = endShip2;
				endShip2 = tmp;

			}

			// is orthogonal?
			if (beginShip1 != endShip1 && beginShip2 != endShip2) {
				noErrors = false;
				System.out.println();
				System.out.println("Error! Wrong ship location! Try again:\n");
				continue;
			} else {
				noErrors = true;

			}

			// is ship legal size?
			if (endShip1 - beginShip1 + 1 == shipSize || endShip2 - beginShip2 + 1 == shipSize) {
				noErrors = true;
			} else {
				System.out.println();
				System.out.printf("Error! Wrong length of the %s! Try again:\n", ship.getShipType());
				System.out.println();
				noErrors = false;
				continue;
			}

			// is within game board coordinates?
			if (beginShip1 < 1 || beginShip2 < 1 || endShip1 > 10 || endShip2 > 10) {
				System.out.println();
				System.out.println("Error! Ship location is out of bounds! Try again: \n");
				noErrors = false;
				continue;
			} else {
				noErrors = true;
			}

			// in horizontal or vertical position?
			if (beginShip1 == endShip1) {
				horizontal = true;

			} else if (beginShip2 == endShip2) {
				vertical = true;

			}

			// is place available?
			boolean isLocationAvailable = true;
			if (horizontal == true) {
				for (int i = beginShip2; i <= endShip2; i++) {
					if (field[beginShip1][i].equals("O")) {
						noErrors = false;
						isLocationAvailable = false;
					}

				}

			} else if (vertical == true) {
				for (int i = beginShip1; i <= endShip1; i++) {
					if (field[i][beginShip2].equals("O")) {
						noErrors = false;
						isLocationAvailable = false;
					}

				}

			}

			if (isLocationAvailable == false) {
				System.out.println();
				System.out.println("Error! Location is already occupied by other ship! Try again: \n");
				continue;
			}

			// is too close to another ships?
			boolean isTooClose = false;

			if (horizontal == true && endShip2 == 10 && beginShip1 != 10) {
				for (int i = beginShip2; i <= endShip2; i++) {
					if (field[beginShip1 - 1][i].equals("O") || field[beginShip1 + 1][i].equals("O")
							|| field[beginShip1][beginShip2 - 1].equals("O")
							|| field[beginShip1 - 1][beginShip2 - 1].equals("O")
							|| field[beginShip1 + 1][beginShip2 - 1].equals("O"))

					{
//						System.out.println("CASE 1");
						noErrors = false;
						isTooClose = true;
						continue;
					}

				}

			} else if (horizontal == true && beginShip1 == 10 && endShip2 != 10) {
				for (int i = beginShip2; i <= endShip2; i++) {
					if (field[beginShip1 - 1][i].equals("O") || field[beginShip1][beginShip2 - 1].equals("O")
							|| field[beginShip1][endShip2 + 1].equals("O")
							|| field[beginShip1 - 1][beginShip2 - 1].equals("O")
							|| field[beginShip1 - 1][endShip2 + 1].equals("O"))

					{
//						System.out.println("CASE 2");
						noErrors = false;
						isTooClose = true;
						continue;
					}

				}

			} else if (horizontal == true && beginShip1 == 10 && endShip2 == 10) {
				for (int i = beginShip2; i <= endShip2; i++) {
					if (field[beginShip1 - 1][i].equals("O") || field[beginShip1][beginShip2 - 1].equals("O")
							|| field[beginShip1 - 1][beginShip2 - 1].equals("O"))

					{
//						System.out.println("CASE 3");
						noErrors = false;
						isTooClose = true;
						continue;
					}

				}

			} else if (horizontal == true && beginShip1 != 10 && endShip2 != 10) {
				for (int i = beginShip2; i <= endShip2; i++) {
					if (field[beginShip1 - 1][i].equals("O") || field[beginShip1 + 1][i].equals("O")
							|| field[beginShip1][beginShip2 - 1].equals("O")
							|| field[beginShip1][endShip2 + 1].equals("O")
							|| field[beginShip1 - 1][beginShip2 - 1].equals("O")
							|| field[beginShip1 + 1][beginShip2 - 1].equals("O")
							|| field[beginShip1 - 1][endShip2 + 1].equals("O")
							|| field[beginShip1 + 1][endShip2 + 1].equals("O"))

					{
//						System.out.println("CASE 4");
						noErrors = false;
						isTooClose = true;
						continue;
					}

				}

			} else if (vertical == true && endShip1 != 10 && beginShip2 != 10) {

				if (field[beginShip1 - 1][beginShip2 - 1].equals("O") || field[beginShip1 - 1][beginShip2].equals("O")
						|| field[beginShip1 - 1][beginShip2 + 1].equals("O")

						|| field[endShip1 + 1][beginShip2 - 1].equals("O")
						|| field[endShip1 + 1][beginShip2].equals("O")
						|| field[endShip1 + 1][beginShip2 + 1].equals("O")

				) {
//					System.out.println("CASE 5");
					noErrors = false;
					isTooClose = true;

				}

				for (int i = beginShip1; i <= endShip1; i++) {
					if (field[i][beginShip2 - 1].equals("O") || field[i][beginShip2 + 1].equals("O"))

					{
//						System.out.println("CASE 6");
						noErrors = false;
						isTooClose = true;
						continue;
					}

				}

			} else if (vertical == true && endShip1 == 10 && beginShip2 == 10) {

				if (field[beginShip1 - 1][beginShip2 - 1].equals("O")
						|| field[beginShip1 - 1][beginShip2].equals("O")) {
//					System.out.println("CASE 7");
					noErrors = false;
					isTooClose = true;

				}

				for (int i = beginShip1; i <= endShip1; i++) {
					if (field[i][beginShip2 - 1].equals("O"))

					{
//						System.out.println("CASE 8");
						noErrors = false;
						isTooClose = true;
						continue;
					}

				}

			} else if (vertical == true && endShip1 == 10 && beginShip2 != 10) {

				if (field[beginShip1 - 1][beginShip2 - 1].equals("O") || field[beginShip1 - 1][beginShip2].equals("O")
						|| field[beginShip1 - 1][beginShip2 + 1].equals("O")

				) {
//					System.out.println("CASE 9");
					noErrors = false;
					isTooClose = true;

				}

				for (int i = beginShip1; i <= endShip1; i++) {
					if (field[i][beginShip2 - 1].equals("O"))

					{
//						System.out.println("CASE 10");
						noErrors = false;
						isTooClose = true;
						continue;
					}

				}

			} else if (vertical == true && endShip1 != 10 && beginShip2 == 10) {
				if (field[beginShip1 - 1][beginShip2 - 1].equals("O") || field[beginShip1 - 1][beginShip2].equals("O")
						|| field[endShip1 + 1][beginShip2 - 1].equals("O")
						|| field[endShip1 + 1][beginShip2].equals("O")

				) {
//					System.out.println("CASE 11");
					noErrors = false;
					isTooClose = true;

				}

				for (int i = beginShip1; i <= endShip1; i++) {
					if (field[i][beginShip2 - 1].equals("O"))

					{
//						System.out.println("CASE 12");
						noErrors = false;
						isTooClose = true;
						continue;
					}

				}

			}

			if (isTooClose == true) {
				System.out.println();
				System.out.println("Error! You placed it too close to another one. Try again: \n");

			}

			// write ship on game field
			if (noErrors == true) {
				if (horizontal == true) {
					for (int i = beginShip2; i <= endShip2; i++) {
						field[beginShip1][i] = "O";
					}

				} else if (vertical == true) {
					for (int i = beginShip1; i <= endShip1; i++) {
						field[i][beginShip2] = "O";
					}
				}
				printField();
			}

		} while (!noErrors);

	}

}

enum Ship {

	ACARRIER(5, "Aircraft Carrier"), BATTLESHIP(4, "Battleship"), SUBMARINE(3, "Submarine"), CRUISER(3, "Cruiser"),
	DESTROYER(2, "Destroyer");

	int shipSize;
	String shipType;

	Ship(int shipSize, String shipType) {
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
