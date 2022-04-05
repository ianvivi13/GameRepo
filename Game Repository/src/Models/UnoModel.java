package Models;

public class UnoModel {

	private Pile deck;
	private Pile hand;
	private Pile wastePile;
	
	public UnoModel() {
		deck = new Pile();
		hand = new Pile();
		wastePile = new Pile();
	}
	
	public Pile getDeck() {
		return deck;
	}
	
	public Pile getHand() {
		return hand;
	}
	
	public Pile getWastePile() {
		return wastePile;
	}
}
