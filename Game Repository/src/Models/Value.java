package Models;

public enum Value {
	Zero("0"),
	One("1"),
	Two("2"),
	Three("3"),
	Four("4"),
	Five("5"),
	Six("6"),
	Seven("7"),
	Eight("8"),
	Nine("9"),
	Skip("S"),
	Reverse("R"),
	DrawTwo("T"),
	Wild("W"),
	Wild_Four("F"),
	SwapHands("H");
	
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
	
	public static Value fromString(String symbol) {
        for (Value v : Value.values()) {
            if (v.toString().equalsIgnoreCase(symbol)) {
                return v;
            }
        }
       return null;
    }
	
	public String getMemberName() {
		return super.toString();
	}
}
