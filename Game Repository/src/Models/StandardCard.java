package Models;

public class StandardCard implements Comparable<StandardCard>{
	private Rank rank;
	private Suit suit;
	private int value;
	
	
	public StandardCard(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
		
		if(rank.getSymbol() == "2") {
			value = 2;
		}
		else if(rank.getSymbol() == "3") {
			value = 3;
		}
		else if(rank.getSymbol() == "4") {
			value = 4;
		}
		
		else if(rank.getSymbol() == "5") {
			value = 5;
		}
		else if(rank.getSymbol() == "6") {
			value = 6;
		}
		else if(rank.getSymbol() == "7") {
			value = 7;
		}
		else if(rank.getSymbol() == "8") {
			value = 8;
		}
		else if(rank.getSymbol() == "9") {
			value = 9;
		}
		else if(rank.getSymbol() == "T" ||rank.getSymbol() == "J" ||rank.getSymbol() == "Q" ||rank.getSymbol() == "K" ) {
			value = 10;
		}
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
	
	public int getValues() {
		return value;
	}
	
	
//	public String images() {
//		return Thread.currentThread().getContextClassLoader().getResource("10_clubs.png");
//	}
	
	
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