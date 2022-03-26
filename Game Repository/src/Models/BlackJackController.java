package  Models;

public class BlackJackController extends Game{
	
	public void initialize(BlackJackModel model) {
		model.getDeck().populate();
		model.getDeck().shuffle();
		model.getHand().addCards(model.getDeck().removeCards(2));
		model.getHand().setVisibleIndex(0);
		model.getDeck().setVisibleIndex(model.getDeck().getIndexOfTopCard());
	}
	
	public void hit(BlackJackModel model) {
		model.getHand().drawCard();
	}
	
	// for hold we want to basically skip a players turn
	
	// for stay/freeze we want to skip a players turn until the other player calls stay
	
	// split needs to compare cards by rank and if they are the smae then move cards to alt hand
}