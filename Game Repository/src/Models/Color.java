package Models;

public enum Color {
	RED("R"),
	YELLOW("Y"),
	GREEN("G"), 
	BLUE("B"),
	BLACK("W");
	
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
