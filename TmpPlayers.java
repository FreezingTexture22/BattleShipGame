package Battleship;

public class TmpPlayers {
	public static void main(String[] args) {
		Playerr p1 = new Playerr("Andrey");
		Playerr p2 = new Playerr("Lesha");
		
		System.out.println(p1.getName());
		p1.setName("Vova");
		System.out.println(p1.getName());
		
		p1.setSurname();
		p2.setSurname();
		System.out.println(p1.getSurname());
		System.out.println(p2.getSurname());
		
	}
}

class Playerr {
	String name;
	String surname;
	StringBuilder sb;
	
	Playerr (String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
		
	}
	
	void setSurname() {
		surname = "von" + name;
	}
	
	public String getSurname() {
		return surname;
	}
	
}
