package Models;

public enum Color {
	RED("R"), 
	BLUE("B"), 
	GREEN("G"), 
	YELLOW("Y"), 
	BLACK("K");
	
private final String symbol;
	
	//Constructor
	private Color(String symbol) {
		this.symbol = symbol;
	}
	
	//getter
	public String getSymbol() {
		return symbol;
	}
	
	public String toString() {
		return symbol; 
	}
	
	public static Color fromString(String symbol) {
        for (Color c : Color.values()) {
            if (c.toString().equalsIgnoreCase(symbol)) {
                return c;
            }
        }
       return null;
    }
	
	public String getMemberName() {
		return super.toString();
	}
}
