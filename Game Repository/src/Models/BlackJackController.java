package  Models;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;



public class BlackJackController {
	

	private static IDatabase db;
	
	
	public static void initialize(int gameId) throws Exception {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		
		Game model = db.getGameFromGameId(gameId);
		
		model.getMainPile().populate();
		model.getMainPile().shuffle();
		model.getMainPile().shuffle();
		
		for(Player players : model.getPlayers()) {
			players.getPile().addCards(model.getMainPile().removeCards(2));
			players.getPile().setVisibleIndex(players.getPile().getIndexOfTopCard());
		}
		db.updateGame(gameId, model);
	}
	
	// for hold we want to basically skip a players turn
	public static void hold(int gameId){
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		model.nextTurn();
		db.updateGame(gameId, model);
	}
	
	
	public static void hit(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		current.getPile().addCards(model.getMainPile().removeCards(1));
		checkBust(gameId);
		model.nextTurn();
		db.updateGame(gameId, model);
		db.updatePlayer(db.getPlayerIdFromPlayer(current), current);
		} 
	
	// for stay/freeze we want to skip a players turn until the other player calls stay
	public static void freeze(int gameId) {
		InitDatabase.init();
		db = DatabaseProvider.getInstance();
		Game model = db.getGameFromGameId(gameId);
		model.removePlayerFromTurn(model.getTurnOrder().CurrentPlayer());
		db.updateGame(db.getGameIdFromGame(model), model);
	}
	
	
	public static boolean checkWin(int gameId) {
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getPlayerIds().get(0));
		Player next = db.getPlayerFromPlayerId(model.getPlayerIds().get(1));
		db.updateGame(gameId, model);
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
	
	public static boolean checkBust(int gameId) {
		Game model = db.getGameFromGameId(gameId);
		Player current = db.getPlayerFromPlayerId(model.getTurnOrder().CurrentPlayer());
		db.updateGame(gameId, model);
		if(current.getPile().getValueStandard() >  21) {
			model.removePlayerFromTurn(model.getTurnOrder().CurrentPlayer());
			return true;
		}
		
		return false;
	}

}