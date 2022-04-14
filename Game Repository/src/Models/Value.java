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
	DrawTwo("T"),
	Skip("S"),
	Reverse("R"),
	Zero("0"),
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
