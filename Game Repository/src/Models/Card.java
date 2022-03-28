package Models;

public class Card implements Comparable<Card>{
	private Rank rank;
	private Suit suit;
	
	
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public Suit getSuit() {
		return suit;
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
		if (obj == null || !(obj instanceof Card)) {
			return false;
		}
		Card other = (Card) obj;
		
		return this.compareTo(other) == 0;
	}
	
	@Override 
	public int compareTo(Card o) {
		int cmp = this.suit.compareTo(o.suit);
		if (cmp != 0) {
			return cmp;
		}
		return this.rank.compareTo(o.rank);
	}
}