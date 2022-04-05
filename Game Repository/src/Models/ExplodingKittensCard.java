package Models;

public class ExplodingKittensCard implements Comparable<ExplodingKittensCard>{
	private Type type;
	private Value values;
	
	public ExplodingKittensCard(Type type, Value values) {
		this.values = values;
		this.type = type;	
	}
	
	public Type getColor() {
		return type;
	}
	
	public Value getValues() {
		return values;
	}
	
	@Override
	public String toString() {
		return values.toString() + type.toString(); 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Object)) {
			return false;
		}
		ExplodingKittensCard other = (ExplodingKittensCard) obj;
		
		return this.compareTo(other) == 0;
	}
	
	@Override 
	public int compareTo(ExplodingKittensCard o) {
		int cmp = this.type.compareTo(o.type);
		if (cmp != 0) {
			return cmp;
		}
		return this.values.compareTo(o.values);
	}
	
}
