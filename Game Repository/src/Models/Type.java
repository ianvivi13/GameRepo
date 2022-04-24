package Models;

public enum Type {
	
	AlterTheFuture("AF"),
	Attack("AT"),
	BeardCat("BC"),
	Cattermelon("CM"),
	Defuse("DF"),
	DrawFromBottom("DB"),
	ExplodingKitten("EK"),
	Favor("FA"),
	FeralCat("FC"),
	HairyPotatoCat("HP"),
	Nope("NO"),
	RainbowRalphingCat("RC"),
	SeeTheFuture("SF"),
	Shuffle("SH"),
	Skip("SK"),
	TacoCat("TC"),
	TargetedAttack("TA");

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
	
	public static Type fromString(String symbol) {
        for (Type t : Type.values()) {
            if (t.toString().equalsIgnoreCase(symbol)) {
                return t;
            }
        }
       return null;
    }
	
	public String getMemberName() {
		return super.toString();
	}
	
	
}