package  Models;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;



public class BlackJackController {
	

	private IDatabase db;
	
	
	public int initialize(Game model) throws Exception {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		
		model.getMainPile().populate();
		model.getMainPile().shuffle();
		
		for(Player players : model.getPlayers()) {
			players.getPile().addCards(model.getMainPile().removeCards(2));
			players.getPile().setVisibleIndex(players.getPile().getIndexOfTopCard());
		}
		int gameID = db.createGame(model);
		db.updateGame(gameID, model);
		return gameID;
	}
	
	// for hold we want to basically skip a players turn
	public void hold(Game model){
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		model.nextTurn();
		db.updateGame(db.getGameIdFromGame(model), model);
	}
	
	
	public void hit(Game model) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		current.getPile().addCards(model.getMainPile().removeCards(1));
		checkBust(model);
		model.nextTurn();
		db.updateGame(db.getGameIdFromGame(model), model);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
		} 
	
	// for stay/freeze we want to skip a players turn until the other player calls stay
	public void freeze(Game model) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		model.removePlayerFromTurn(model.getTurnOrder().CurrentPlayer());
		db.updateGame(db.getGameIdFromGame(model), model);
	}
	
	
	public boolean checkWin(Game model) {
		Player current = db.getPlayerFromPlayerId(model.getPlayerIds().get(0));
		Player next = db.getPlayerFromPlayerId(model.getPlayerIds().get(1));
		//need to find a way to get next player
		db.updateGame(db.getGameIdFromGame(model), model);
		if(current.getPile().getValueStandard() == next.getPile().getValueStandard() && current.getPile().getNumCards() < next.getPile().getNumCards()) {
			return true;
		}
		else if(current.getPile().getValueStandard() < 21 && next.getPile().getValueStandard() < current.getPile().getValueStandard()) {
			return true;
		}
		else if(current.getPile().getValueStandard() == 21) {
			return true;
		}
		return false;
	}
	
	public boolean checkBust(Game model) {
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		db.updateGame(db.getGameIdFromGame(model), model);
		if(current.getPile().getValueStandard() >  21) {
			model.removePlayerFromTurn(model.getTurnOrder().CurrentPlayer());
			return true;
		}
		
		return false;
	}

}