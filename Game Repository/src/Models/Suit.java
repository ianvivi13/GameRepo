package Models;

public enum Suit {
	CLUBS("♣", Color.BLACK),
	DIAMONDS("♦", Color.RED),
	HEARTS("♥", Color.RED),
	SPADES("♠", Color.BLACK);
	
	private final String symbol;
	private final Color color;
	
	//Constructor
	private Suit(String symbol, Color color) {
		this.symbol = symbol;
		this.color = color;
	}
	
	//getter
	public Color getColor() {
		return color;
	}
	
	public String toString() {
		return symbol;
	}
	
	//To retrieve the name of the enumeration as a string
	
	public String getMemberName() {
		return super.toString();
	}
}
