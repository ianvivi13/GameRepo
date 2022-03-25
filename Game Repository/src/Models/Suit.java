package Models;

public enum Suit {
	CLUBS('c', Color.BLACK),
	DIAMONDS('d', Color.RED),
	HEARTS('h', Color.RED),
	SPADES('s', Color.BLACK);
	
	private final char symbol;
	private final Color color;
	
	//Constructor
	private Suit(char symbol, Color color) {
		this.symbol = symbol;
		this.color = color;
	}
	
	//getter
	public char getSymbol() {
		return symbol;
	}
	
	public char toChar() {
		return symbol;
	}
	
	//To retrieve the name of the enumeration as a string
	
	/*public char getMemberName() {
		return super.toChar();
	}*/
}
