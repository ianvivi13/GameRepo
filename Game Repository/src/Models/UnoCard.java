package Models;

public class UnoCard implements Comparable<UnoCard>{
	private Color color;
	private Value values;
	
	public UnoCard(Color color, Value values) {
		this.values = values;
		this.color = color;	
	}
	
	public Color getColor() {
		return color;
	}
	
	public Value getValues() {
		return values;
	}
	
	@Override
	public String toString() {
		return values.toString() + color.toString(); 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Object)) {
			return false;
		}
		UnoCard other = (UnoCard) obj;
		
		return this.compareTo(other) == 0;
	}
	
	@Override 
	public int compareTo(UnoCard o) {
		int cmp = this.color.compareTo(o.color);
		if (cmp != 0) {
			return cmp;
		}
		return this.values.compareTo(o.values);
	}
	
}