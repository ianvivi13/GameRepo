package  Models;

import java.util.List;
import java.util.Scanner;

import Database.elves.DatabaseProvider;
import Database.elves.DerbyDatabase;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;



public class BlackJackController extends Game {
	
	private IDatabase db;
	private int i;
	
	public BlackJackController(String gameKey) {
		super(gameKey);
	}
	
	public void initialize() throws Exception {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		
		getMainPile().populate();
		getMainPile().shuffle();
		getMainPile().setVisibleIndex(1000000000);
		
		for(Player p : getPlayers()) {
			p.getPile().addCards(getMainPile().removeCards(2));
		}
		i = db.createGame(this);
	}
	
//	public void hit(Game game) {
//		Player p = game.getPlayers().get(game.getTurnOrder().CurrentPlayer());
//		p.getPile().drawCard();
//		nextTurn();
//		db.updateGame(db.getGameIdFromGame(blj), blj);
//	}
//	
//	// for hold we want to basically skip a players turn
//	public void hold() {
//		nextTurn();
//		db.updateGame(i, blj);
//	}
//	
//	// for stay/freeze we want to skip a players turn until the other player calls stay
//	public void freeze(Player p) {
//		tko.RemoveAllTurns(p.getUserBotID());
//		db.updateGame(db.getGameIdFromGame(blj), blj);
//	}
//	
//	// split needs to compare cards by rank and if they are the same then move cards to alt hand
//	public void split(Player p) {
//		p = players.get(tko.CurrentPlayer());
//		Rank one = ((StandardCard) p.getPile().getCard(p.getPile().getIndexOfTopCard())).getRank();
//		Rank two = ((StandardCard) p.getPile().getCard(p.getPile().getIndexOfTopCard()+1)).getRank();
//		if((p.getPile().getNumCards() == 2) && (one.equals(two))){
//			p.getAltPile().addCard(p.getPile().getTopCard());
//		}
//		db.updateGame(db.getGameIdFromGame(blj), blj);
//	}
//	
//	public boolean checkWin() {
//		for(Player p : players) {
//			if(p.getPile().getValueStandard() == 21) {
//				return true;
//			}
//		}
//		return false;
////		db.getBlackjackStats(UserID)
////		db.updateBlackjackStats(, user_id);
////		db.updateGlobalStats(stat, user_id);
//	}

}