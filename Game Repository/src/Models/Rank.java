package Models;

public enum Rank {
	/* In order, the list of rankings from least to greatest*/
	Ace('A'),
	TWO('2'),
	THREE('3'),
	FOUR('4'),
	FIVE('5'),
	SIX('6'),
	SEVEN('7'),
	EIGHT('8'),
	NINE('9'),
	TEN('t'),
	JACK('J'),
	QUEEN('Q'),
	KING('K');
	
	private final char symbol;
	
	//Constructor
	private Rank(char symbol) {
		this.symbol = symbol;
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
