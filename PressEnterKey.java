package Battleship;

import java.io.IOException;

public class PressEnterKey {
	public static void main(String[] args) {
		System.out.println("AAAAAAAAAAA press enter key");
		promptEnterKey();
		System.out.println("Key pressed");
	}
	
	public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
