package Models;

public enum Suit {
	CLUBS("C", Color.BLACK),
	DIAMONDS("D", Color.RED),
	HEARTS("H", Color.RED),
	SPADES("S", Color.BLACK);
	
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
	
	public static Suit fromString(String t) {
        for (Suit s : Suit.values()) {
            if (s.toString().equalsIgnoreCase(t)) {
                return s;
            }
        }
       return null;
    }
	
	//To retrieve the name of the enumeration as a string
	
	public String getMemberName() {
		return super.toString();
	}
}
