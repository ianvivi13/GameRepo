package  Models;

import java.util.List;
import java.util.Scanner;

import Database.elves.DatabaseProvider;
import Database.elves.DerbyDatabase;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;



public class BlackJackController extends Game {
	
	Game blj = new Game(gameCode,gameKey);
	
	public BlackJackController(String gameCode, String gameKey) {
		super(gameCode, gameKey);
	}
	public void initialize() {
		mainPile.populate();
		mainPile.shuffle();
		
		for(Player p : players) {
			p.getPile().addCards(mainPile.removeCards(2));
			p.getPile().setVisibleIndex(0);
		}
		db.createGame(blj);
	}
	
	public void hit() {
		Player p = players.get(tko.CurrentPlayer());
		p.getPile().drawCard();
		nextTurn();
		db.updateGame(db.getGameIdFromGame(blj), blj);
	}
	
	// for hold we want to basically skip a players turn
	public void hold(BlackJackModel model) {
		nextTurn();
		db.updateGame(db.getGameIdFromGame(blj), blj);
	}
	
	// for stay/freeze we want to skip a players turn until the other player calls stay
	public void freeze() {
		
	}
	
	// split needs to compare cards by rank and if they are the same then move cards to alt hand
	public void split(BlackJackModel model) {
		Player p = players.get(tko.CurrentPlayer());
		Rank one = ((StandardCard) p.getPile().getCard(p.getPile().getIndexOfTopCard())).getRank();
		Rank two = ((StandardCard) p.getPile().getCard(p.getPile().getIndexOfTopCard()+1)).getRank();
		if((p.getPile().getNumCards() == 2) && (one.equals(two))){
			p.getAltPile().addCard(p.getPile().getTopCard());
		}
		db.updateGame(db.getGameIdFromGame(blj), blj);
	}
	
	public boolean checkWin() {
		for(Player p : players) {
			if(p.getPile().getValueStandard() == 21) {
				return true;
			}
		}
		return false;
//		db.updateBlackjackStats(stat, user_id);
//		db.updateGlobalStats(stat, user_id);
	}

}