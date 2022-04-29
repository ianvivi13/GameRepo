package Models;

public class StandardCard implements Comparable<StandardCard>{
	private Rank rank;
	private Suit suit;
	
	public StandardCard(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
		
	}
	
	public StandardCard(String rank, String suit) {
		this.rank = Rank.fromString(rank);
		this.suit = Suit.fromString(suit);
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public String getImagePath() {
		String im = "_view/images/StandardCards/";
		String r = rank.toString();
		String s = suit.toString();
		switch (r) {
			case "T":
				im += "10";
				break;
			case "J":
				im += "jack";
				break;
			case "Q":
				im += "queen";
				break;
			case "K":
				im += "king";
				break;
			case "A":
				im += "ace";
				break;
			default:
				im += r;
		}
		
		im += "_";
		
		switch (s) {
			case "H":
				im += "hearts";
				break;
			case "D":
				im += "diamonds";
				break;
			case "S":
				im += "spades";
				break;
			case "C":
				im += "clubs";
				break;
			default:
				im += s;
		}
		
		return im + ".png";
	}
	
	public String getBackPath() {
		return "_view/images/StandardCards/back-sm.png";
	}
	
	@Override
	public String toString() {
		return rank.toString() + suit.toString(); 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Object)) {
			return false;
		}
		StandardCard other = (StandardCard) obj;
		
		return this.compareTo(other) == 0;
	}
	
	@Override 
	public int compareTo(StandardCard o) {
		int cmp = this.suit.compareTo(o.suit);
		if (cmp != 0) {
			return cmp;
		}
		return this.rank.compareTo(o.rank);
	}
}