package Models;

public class BlackJackModel{
	
	private Pile deck;
	private Pile hand;
	private Pile altHand;
	
	
	public BlackJackModel() {
		deck = new Pile();
		hand = new Pile();
		altHand = new Pile();
	}
	
	public Pile getDeck() {
		return deck;
	}
	
	public Pile getHand() {
		return hand;
	}
	
	public Pile getAltHand() {
		return altHand;
	}
}