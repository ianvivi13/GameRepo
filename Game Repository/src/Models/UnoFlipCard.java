package Models;

public class UnoFlipCard implements Comparable<UnoFlipCard>{
	private Color color;
	private Value values;
	
	public UnoFlipCard(Color color, Value values) {
		this.values = values;
		this.color = color;	
	}
	
	public UnoFlipCard(String color, String values) {
		this.values = Value.fromString(values);
		this.color =  Color.fromString(color);	
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
		UnoFlipCard other = (UnoFlipCard) obj;
		
		return this.compareTo(other) == 0;
	}
	
	@Override 
	public int compareTo(UnoFlipCard o) {
		int cmp = this.color.compareTo(o.color);
		if (cmp != 0) {
			return cmp;
		}
		return this.values.compareTo(o.values);
	}
	
}