package  Models;

import java.util.List;
import java.util.Scanner;

import Database.elves.DatabaseProvider;
import Database.elves.DerbyDatabase;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;



public class BlackJackController extends Game{
	
	
	public void initialize(BlackJackModel model) {
//		InitDatabase.init();
//		IDatabase db = DatabaseProvider.getInstance();
//		
//		List<StandardCard> authorList = db.initializeBlackJackCards();
		
		
		model.getDeck().populate();
		model.getDeck().shuffle();
		model.getHand().addCards(model.getDeck().removeCards(2));
		model.getHand().setVisibleIndex(0);
		model.getDeck().setVisibleIndex(1000000000);
	}
	
	public void hit(BlackJackModel model) {
		model.getHand().addCards(model.getDeck().removeCards(1));
	}
	
	// for hold we want to basically skip a players turn
	public void hold(BlackJackModel model) {
		nextTurn();
	}
	
	// for stay/freeze we want to skip a players turn until the other player calls stay
	public void freeze(BlackJackModel model) {
		//removePlayer();
	}
	
	// split needs to compare cards by rank and if they are the same then move cards to alt hand
	public void split(BlackJackModel model) {
		Rank one = ((StandardCard) model.getHand().getTopCard()).getRank();
		Rank two = ((StandardCard) model.getHand().getCard(model.getHand().getIndexOfTopCard() + 1)).getRank();
		if((model.getHand().getNumCards() == 2) && (one.equals(two))){
			model.getAltHand().addCards(model.getHand().removeCards(1));
		}
	}
	
	public void checkWin(BlackJackModel model) {
		
	}
	
	public void checkBust(BlackJackModel model){
		
	}

}