package Models;

public class UnoParentController extends Game {
	
	public void initialize(UnoModel model) {
		model.getDeck().populate();
		model.getDeck().shuffle();
		model.getHand().addCards(model.getDeck().removeCards(7));
		model.getHand().setVisibleIndex(7);
	}
	
	//If no cards in your hand does not equal the color or symbol of the top index card in the waste pile, draw one card
	public void draw(UnoModel model) {
		for (int i = 0; i < model.getHand().getNumCards(); i++) {
			if (model.getHand().isEmpty() || model.getHand() != model.getWastePile().getTopCard() || model.getHand().getCard(i) == Color.BLACK) {
				model.getDeck().drawCard(model.getDeck().getTopCard());
			}
		}
		
		nextTurn();
		
	}

	//If the current card attempting to be 
	public void wildFour(UnoModel model, Color color) {
		if (model.getHand().getCard() == Value.Wild_Four) {
			//change color
			nextTurn();
			for(int x = 0; x < 4; x++ ) {
				model.getDeck().drawCard(model.getHand());
			}
		}
	}
	
	public void skip(UnoModel model) {
		
		
		nextTurn();
	}
	
	public void reverse(UnoModel model) {
		
		
		nextTurn();
	}
	
	public void play(UnoModel model) {
		
		
		nextTurn();
	}
	
	public void checkWin(UnoModel model, String player) {
		if (model.getHand().getNumCards() == 0) { //How to get the number of cards in possession by the individual player
			
		}
	}
}
