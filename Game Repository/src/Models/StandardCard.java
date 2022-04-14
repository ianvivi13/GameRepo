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