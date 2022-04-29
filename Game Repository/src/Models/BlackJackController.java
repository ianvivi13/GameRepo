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
		model.nextTurn();
		//checkWin(model);
		db.updateGame(db.getGameIdFromGame(model), model);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
		} 
	
	public void freeze(Game model) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		model.removePlayerFromTurn(model.getTurnOrder().CurrentPlayer());
		db.updateGame(db.getGameIdFromGame(model), model);
	}
	
	public boolean checkWin(Game model) {
		
		for(Player p : model.getPlayers()) {
			if(p.getPile().getValueStandard() == 21) {
				return true;
			}
		}
		return false;
	}
}