package Models;

public enum AltColor {
	
	ORANGE("O"), 
	CYAN("C"), 
	PINK("P"), 
	PURPLE("V"), 
	WHITE("I");
	
private final String symbol;
	
	//Constructor
	private AltColor(String symbol) {
		this.symbol = symbol;
	}
	
	//getter
	public String getSymbol() {
		return symbol;
	}
	
	public String toString() {
		return symbol; 
	}
	
	public static AltColor fromString(String symbol) {
        for (AltColor alt : AltColor.values()) {
            if (alt.toString().equalsIgnoreCase(symbol)) {
                return alt;
            }
        }
       return null;
    }
	
	public String getMemberName() {
		return super.toString();
	}
}
