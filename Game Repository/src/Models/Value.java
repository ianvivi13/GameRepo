package Models;

public enum Value {
	One("1"),
	Two("2"),
	Three("3"),
	Four("4"),
	Five("5"),
	Six("6"),
	Seven("7"),
	Eight("8"),
	Nine("9"),
	DrawTwo("DT"),
	Skip("S"),
	Reverse("R"),
	Zero("0"),
	Wild("W"),
	Wild_Four("W4");
	
private final String symbol;
	
	//Constructor
	private Value(String symbol) {
		this.symbol = symbol;
	}
	
	//getter
	public String getSymbol() {
		return symbol;
	}
	
	public String toString() {
		return symbol; 
	}
	
	public String getMemberName() {
		return super.toString();
	}
}
