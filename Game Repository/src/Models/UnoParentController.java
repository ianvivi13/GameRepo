package Models;

public class UnoParentController extends Game {
	
	Color[] allColors = Color.values();
	Value[] allValues = Value.values();
	
	public UnoParentController(String gameCode, String gameKey) {
		super(gameCode, gameKey);
	}

	public void initialize(UnoModel model) {
		model.getDeck().populate();
		model.getDeck().shuffle();
		model.getHand().addCards(model.getDeck().removeCards(7));
		model.getHand().setVisibleIndex(7);
	}
	
	//If no cards in your hand does not equal the color or symbol of the top index card in the waste pile, draw one card
	public void draw(UnoModel model) {
		if (model.getHand() != model.getWastePile().getTopCard()) {
		model.getHand().drawCard();
		nextTurn();
		}
	}

	public void wildPlusFour(UnoModel model, Color color) {
		nextTurn();
	}
	
	public void skip(UnoModel model) {
		nextTurn();
		nextTurn();
	}
	
	public void reverse(UnoModel model) {
		reverseTurnOrder();
		nextTurn();
	}
	
	public void play(UnoModel model) {
		model.getDeck().addCards(model.getHand().removeCards(1));
		nextTurn();
	}
	
	public void checkWin() {
		
	}
}
