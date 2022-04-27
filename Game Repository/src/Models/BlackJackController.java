package  Models;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;



public class BlackJackController {
	

	private IDatabase db;
	private int gameID;
	
	
	public int initialize(Game model) throws Exception {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		
		model.getMainPile().populate();
		model.getMainPile().shuffle();
		model.getMainPile().setVisibleIndex(1000000000);
		
		for(Player p : model.getPlayers()) {
			p.getPile().addCards(model.getMainPile().removeCards(2));
			p.getPile().setVisibleIndex(p.getPile().getIndexOfTopCard());;
		}
		return db.createGame(model);
	}
	
	// for hold we want to basically skip a players turn
	public void hold(Game model){
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		model.nextTurn();
		db.updateGame(gameID, model);
	}
	
	
	public void hit(Game model) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		//Player p = model.getPlayers().get(model.getTurnOrder().CurrentPlayer());
		int boob = 0;
		for(Player p: model.getPlayers()) {
			if(db.getPlayerIdFromPlayer(p) == model.getTurnOrder().CurrentPlayer()) {
				boob = db.getPlayerIdFromPlayer(p);
				break;
			}
		}
		try {
		Player current = db.getPlayerFromPlayerId(boob);
		current.getPile().addCards(model.getMainPile().removeCards(1));
		model.nextTurn();
		db.updateGame(gameID, model);
		} catch (Exception e) {
			System.out.println("Couldnt find a player");
		}
	}
	
//	// for stay/freeze we want to skip a players turn until the other player calls stay
//	public void freeze(Player p) {
//		removePlayerFromTurn(db.getPlayerIdFromPlayer(p));
//		db.updateGame(i, this);
//	}
	
	
//	public boolean checkWin() {
//		for(Player p : getPlayers()) {
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