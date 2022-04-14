package Models;

public class ExplodingKittensCard implements Comparable<ExplodingKittensCard>{
	private Type type;
	
	public ExplodingKittensCard(Type type) {
		this.type = type;	
	}
	
	public Type getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return type.toString(); 
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
		return this.type.compareTo(o.type);
	}
	
}
