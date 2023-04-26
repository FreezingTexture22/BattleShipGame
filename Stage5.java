package Battleship;

import java.io.IOException;
import java.util.Scanner;

public class Stage5 {
	public static void main(String[] args) {
		boolean gameEnded = false;

		Player p1 = new Player("Player 1");
		Player p2 = new Player("Player 2");

		// Player 1 board generation
		System.out.println("Player 1, place your ships on the game field");
		System.out.println();
		p1.generateField();
		p1.printField();

		// Player 1 ship placement
		p1.placementOfAircraftCarrier();
		p1.placementOfBattleship();
		p1.placementOfSubmarine();
		p1.placementOfCruiser();
		p1.placementOfDestroyer();

		promptEnterKey(); // Press Enter and pass the move to another player

		// Player 2 board generation
		System.out.println("Player 2, place your ships on the game field");
		System.out.println();
		p2.generateField();
		p2.printField();

		// Player 2 ship placement
		p2.placementOfAircraftCarrier();
		p2.placementOfBattleship();
		p2.placementOfSubmarine();
		p2.placementOfCruiser();
		p2.placementOfDestroyer();

		//
		// GAME START

		promptEnterKey(); // Press Enter and pass the move to another player

		p1.gameStart();
		do {

			p1.takeShot(p2); // Player 1 shoots

			if (p2.notSinkedShips == 0) {
				System.out.println("Player 1 wins!");
				gameEnded = true;
				continue;
			}

			promptEnterKey(); // Press Enter and pass the move to another player

			p2.takeShot(p1); // Player 2 shoots

			if (p1.notSinkedShips == 0) {
				System.out.println("Player 2 wins!");
				gameEnded = true;
				continue;
			}

			promptEnterKey(); // Press Enter and pass the move to another player

		} while (gameEnded != true);

	}

//////////////////////////////////////////////////
//Press ENTER to pass turn to another player

	static void promptEnterKey() {
		System.out.println();
		System.out.println("Press Enter and pass the move to another player");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

// promptEnterKey END
/////////////////////////////////////////////

}

///
///
/// NEW CLASS 

class Player {
	String name;

	// some vars
	String[][] field = new String[11][11];
	int notSinkedShips = 5; // Quantity of ships on board, change as needed

	// creating ships from ENUM
	static Ship aircraftCarrier = Ship.ACARRIER;
	static Ship battleship = Ship.BATTLESHIP;
	static Ship submarine = Ship.SUBMARINE;
	static Ship cruiser = Ship.CRUISER;
	static Ship destroyer = Ship.DESTROYER;

	Player(String name) {
		this.name = name;
	}

//////////////////////////////////////////////////
/// BEGINNING OF GAME - new 10x10 field generation\
	void generateField() {

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
///
//////////////////////////////////////////////////

//////////////////////////////////////////////////
/// print out field 10 x 10
	void printField() {
		System.out.println();
		for (int i = 0; i <= 10; i++) {
			for (int j = 0; j <= 10; j++) {
				System.out.print(field[i][j] + " ");
			}
			System.out.println();
		}
	}
///
/////////////////////////////////////////////

//////////////////////////////////////////////////
/// print out field - ships hidden!
	void fogOfWar(Player target) {

		System.out.println();
		for (int i = 0; i <= 10; i++) {
			for (int j = 0; j <= 10; j++) {
				if (target.field[i][j].equals("O")) {
					System.out.print("~ ");
				} else {
					System.out.print(target.field[i][j] + " ");
				}
			}

			System.out.println();
		}

	}

///
/////////////////////////////////////////////

//////////////////////////////////////////////////
// Start the Game
	void gameStart() {
		System.out.println();
		System.out.println("The game starts!");

	}
///
/////////////////////////////////////////////

//////////////////////////////////////////////////
// Take a shot
	void takeShot(Player target) {

		fogOfWar(target);
		System.out.println("---------------------");
		printField();
		System.out.println();
		System.out.printf("%s, it's your turn:", name);
		System.out.println();

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

			// vertical coordinate, first char A-J, getting char number
			fireCoordV = (int) fireCoordinate.charAt(0) - 64;

			// horizontal coordinate, replacing all non numbers and convert to int
			fireCoordH = Integer.parseInt(fireCoordinate.replaceAll("[^0-9]", ""));

		} while (!noErrors);

		if (target.field[fireCoordV][fireCoordH] == "~" || target.field[fireCoordV][fireCoordH] == "M") {
			target.field[fireCoordV][fireCoordH] = "M";

			System.out.println();
			System.out.println("You missed!");

		} else if (target.field[fireCoordV][fireCoordH] == "X") {

			hitShip();

		} else if (target.field[fireCoordV][fireCoordH] == "O") {
			target.field[fireCoordV][fireCoordH] = "X";

			if (fireCoordV == 10 && fireCoordH == 10) {
				if (target.field[fireCoordV - 1][fireCoordH].matches("[^O]")
						&& target.field[fireCoordV][fireCoordH - 1].matches("[^O]")) {
					sinkShip(target);
				} else {
					hitShip();
				}

			} else if (fireCoordV == 10) {
				if (target.field[fireCoordV - 1][fireCoordH].matches("[^O]")
						&& target.field[fireCoordV][fireCoordH - 1].matches("[^O]")
						&& target.field[fireCoordV][fireCoordH + 1].matches("[^O]")) {
					sinkShip(target);
				} else {
					hitShip();
				}

			} else if (fireCoordH == 10) {
				if (target.field[fireCoordV - 1][fireCoordH].matches("[^O]")
						&& target.field[fireCoordV + 1][fireCoordH].matches("[^O]")
						&& target.field[fireCoordV][fireCoordH - 1].matches("[^O]")) {
					sinkShip(target);
				} else {
					hitShip();
				}

			} else if (fireCoordV != 10 && fireCoordH != 10) {
				if (target.field[fireCoordV - 1][fireCoordH].matches("[^O]")
						&& target.field[fireCoordV + 1][fireCoordH].matches("[^O]")
						&& target.field[fireCoordV][fireCoordH - 1].matches("[^O]")
						&& target.field[fireCoordV][fireCoordH + 1].matches("[^O]")) {
					sinkShip(target);
				} else {
					hitShip();
				}
			}

		}

	}

///
/////////////////////////////////////////////

//////////////////////////////////////////////////
//Print message - ship hit	
	static void hitShip() {
		System.out.println();
		System.out.println("You hit a ship!");

	}

///
/////////////////////////////////////////////	

//////////////////////////////////////////////////
//Sink a ship	
	void sinkShip(Player target) {
		target.notSinkedShips--;

		System.out.println("target.notSinkedShips " + target.notSinkedShips);

		if (target.notSinkedShips == 0) {
			System.out.println();
			System.out.println("You sank the last ship. You won. Congratulations!");

		} else {
			System.out.println();
			System.out.println("You sank a ship! Specify a new target:");
		}

	}
///
/////////////////////////////////////////////

/////////////////////////////////////////////

// Aircraft Carrier placement
	void placementOfAircraftCarrier() {
		enterCoordinates(aircraftCarrier);
	}

// Battleship placement
	void placementOfBattleship() {
		enterCoordinates(battleship);
	}

// Submarine placement
	void placementOfSubmarine() {
		enterCoordinates(submarine);
	}

// Cruiser placement
	void placementOfCruiser() {
		enterCoordinates(cruiser);
	}

// Destroyer placement
	void placementOfDestroyer() {
		enterCoordinates(destroyer);
	}

/////////////////////////////////////////////

//////////////////////////////////////////////////
//coordinates input ("A1 A5")
	void enterCoordinates(Ship ship) {

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

					noErrors = false;
					isTooClose = true;

				}

				for (int i = beginShip1; i <= endShip1; i++) {
					if (field[i][beginShip2 - 1].equals("O") || field[i][beginShip2 + 1].equals("O"))

					{

						noErrors = false;
						isTooClose = true;
						continue;
					}

				}

			} else if (vertical == true && endShip1 == 10 && beginShip2 == 10) {

				if (field[beginShip1 - 1][beginShip2 - 1].equals("O")
						|| field[beginShip1 - 1][beginShip2].equals("O")) {

					noErrors = false;
					isTooClose = true;

				}

				for (int i = beginShip1; i <= endShip1; i++) {
					if (field[i][beginShip2 - 1].equals("O"))

					{

						noErrors = false;
						isTooClose = true;
						continue;
					}

				}

			} else if (vertical == true && endShip1 == 10 && beginShip2 != 10) {

				if (field[beginShip1 - 1][beginShip2 - 1].equals("O") || field[beginShip1 - 1][beginShip2].equals("O")
						|| field[beginShip1 - 1][beginShip2 + 1].equals("O")

				) {

					noErrors = false;
					isTooClose = true;

				}

				for (int i = beginShip1; i <= endShip1; i++) {
					if (field[i][beginShip2 - 1].equals("O"))

					{

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

					noErrors = false;
					isTooClose = true;

				}

				for (int i = beginShip1; i <= endShip1; i++) {
					if (field[i][beginShip2 - 1].equals("O"))

					{

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
/// end of coordinates input
/////////////////////////////////////////////	

}
/// CLASS END

enum Ships {

	ACARRIER(5, "Aircraft Carrier"), BATTLESHIP(4, "Battleship"), SUBMARINE(3, "Submarine"), CRUISER(3, "Cruiser"),
	DESTROYER(2, "Destroyer");

	int shipSize;
	String shipType;

	Ships(int shipSize, String shipType) {
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