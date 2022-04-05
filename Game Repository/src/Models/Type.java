package Models;

public enum Type {
	
	Atler("AF"),
	Attack("AT"),
	Beard("BC"),
	Cattermelon("CM"),
	Defuse("DF"),
	;

	private final String symbol;
	
	//Constructor
	private Type(String symbol) {
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