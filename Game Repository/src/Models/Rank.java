package Models;

public enum Rank {
	/* In order, the list of rankings from least to greatest*/
	ACE("A"),
	TWO("2"),
	THREE("3"),
	FOUR("4"),
	FIVE("5"),
	SIX("6"),
	SEVEN("7"),
	EIGHT("8"),
	NINE("9"),
	TEN("10"),
	JACK("J"),
	QUEEN("Q"),
	KING("K");
	
	private final String symbol;
	
	//Constructor
	private Rank(String symbol) {
		this.symbol = symbol;
	}
	
	//getter
	public String getSymbol() {
		return symbol;
	}
	
	public String toString() {
		return symbol; 
	}
	
	//To retrieve the name of the enumeration as a string
	
	public String getMemberName() {
		return super.toString();
	}
}
